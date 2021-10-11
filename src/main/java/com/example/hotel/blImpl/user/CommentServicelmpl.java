package com.example.hotel.blImpl.user;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.CommentService;
import com.example.hotel.data.user.CommentMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class CommentServicelmpl implements CommentService {

    private static final String ADD_ERROR = "评论失败";

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HotelService hotelService;

    // 添加评论
    @Override
    public ResponseVO addComment(CommentVO commentVO) {
        try {
            double descriptionScore = commentVO.getDescriptionScore();
            double serviceScore = commentVO.getServiceScore();
            double environmentScore = commentVO.getEnvironmentScore();
            double finalScore = (descriptionScore + serviceScore + environmentScore) / 3.0;
            commentVO.setFinalScore(finalScore);

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            commentVO.setCreateDate(curdate);

            Comment comment = new Comment();
            BeanUtils.copyProperties(commentVO, comment);
            commentMapper.addComment(comment);
            // 更细酒店评分
            countRate(comment.getHotelId());
            // 将订单状态设置为已评价
            orderService.commentOrder(comment.getOrderId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Comment> getCommentsByHotelId(int hotelId) {
        return commentMapper.getCommentsByHotelId(hotelId);
    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return commentMapper.getCommentsByUserId(userId);
    }

    @Override
    public double countRate(int hotelId) {
        List<Comment> hotelComments = commentMapper.getCommentsByHotelId(hotelId);
        int numOfComments = hotelComments.size();
        double sumOfScores = 0.0;
        for (int i = 0; i < numOfComments; i++) {
            System.out.println("每个评论的分数" + hotelComments.get(i).getFinalScore());
            sumOfScores += hotelComments.get(i).getFinalScore();
        }
        double rate = sumOfScores/numOfComments;
        System.out.println("总分: " + sumOfScores + " 评论数: " + numOfComments + " 评分: " + rate);
        // 更新酒店评分
        hotelService.updateHotelRate(hotelId, rate);
        return rate;
    }
}
