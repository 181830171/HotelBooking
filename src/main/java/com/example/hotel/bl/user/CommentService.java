package com.example.hotel.bl.user;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author zzy
 */

public interface CommentService {

    /**
     * 添加评论
     * @param commentVO
     * @return
     */

    ResponseVO addComment(CommentVO commentVO);

    /**
     * 获取酒店评论
     * @param hotelId
     * @return
     */

    List<Comment> getCommentsByHotelId(int hotelId);

    /**
     * 获取用户评论
     * @param userId
     * @return
     */

    List<Comment> getCommentsByUserId(int userId);

    /**
     * 计算酒店评分
     * @param hotelId
     * @return
     */

    double countRate(int hotelId);

}
