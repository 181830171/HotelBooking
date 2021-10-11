package com.example.hotel.blImpl.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.CommentService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.user.CommentMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)

public class CommentServicelmplTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderService orderService;

    private static OrderVO testOrder=new OrderVO(){{
        setClientName("测试一号");
        setHaveChild(false);
        setHotelId(2);
        setHotelName("儒家酒店");
        setPeopleNum(2);
        setPhoneNumber("12345678911");
        setRoomNum(1);
        setCheckInDate("2020-07-10");
        setCheckOutDate("2020-07-12");
        setCreateDate("2020-06-21 18:58:49");
        setRoomType("家庭房");
        setPrice((double) 900);
        setUserId(8);
    }};

    private static CommentVO testComment1 = new CommentVO(){{
        setUserId(testOrder.getUserId());
        setHotelId(testOrder.getHotelId());
        setCreateDate(testOrder.getCreateDate());
        setDescriptionScore(4.5);
        setServiceScore(4.5);
        setEnvironmentScore(4.5);
        setUserEvaluation("挺不错的");
    }};

    @Test
    @Transactional
    public void addCommentTest1() {
        orderService.addOrder(testOrder);
        Order order = orderService.getOrderById(testOrder.getId());
        orderService.endOrder(order.getId());
        testComment1.setOrderId(order.getId());
        commentService.addComment(testComment1);
        Assert.assertEquals(4.5, testComment1.getFinalScore(), 0.0);
    }

}