package com.example.hotel.controller.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

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
        setOrderId(1);
        setCreateDate(testOrder.getCreateDate());
        setDescriptionScore(4.5);
        setServiceScore(4.5);
        setEnvironmentScore(4.5);
        setUserEvaluation("挺不错的");
    }};

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @Transactional
    public void commentTest() throws Exception {
        // 添加订单
        String responseString = mockMvc.perform(post("/api/order/addOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testOrder)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(asJsonString(testOrder));
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));


        // 对订单评论
        String responseString1 = mockMvc.perform(post("/api/user/addComment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testComment1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));

        // 根据用户id获取评论
        String responseString2 = mockMvc.perform(get("/api/user/8/getCommentsByUserId")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}