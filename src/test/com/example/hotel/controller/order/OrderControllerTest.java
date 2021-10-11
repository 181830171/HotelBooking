package com.example.hotel.controller.order;

import com.example.hotel.HotelApplication;
import com.example.hotel.vo.OrderVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.After;
import org.junit.Assert;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// 测试之前重新执行数据库文件，清空缓存

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static OrderVO testOrder=new OrderVO(){{
        setHotelId(1);
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
        setUserId(12);
    }};


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Transactional
    public void orderTest1() throws Exception {
        // 添加订单
        String responseString = mockMvc.perform(post("/api/order/addOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testOrder)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(asJsonString(testOrder));
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));

        // 获取用户订单
        String responseString1 = mockMvc.perform(get("/api/order/12/getUserOrders")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        String content = new JSONObject(responseString1).getString("content");
        // 由于不会清理缓存，所以取数组最后一个为最新订单
        JSONArray jsonArray = new JSONArray(content);
        int len  = jsonArray.length();
        String c = new JSONArray(content).get(len-1).toString();
        int id = new JSONObject(c).getInt("id");

        // 执行订单失败，未到入住时间
        String responseString2 = mockMvc.perform(get("/api/order/" + id + "/execOrder")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString2);
        assertFalse(new JSONObject(responseString2).getBoolean("success"));
        Assert.assertEquals("未到入住时间", new JSONObject(responseString2).getString("message"));

        // 取消订单
        String responseString3 = mockMvc.perform(get("/api/order/1/annulOrder")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    // 正常入住
    @Test
    @Transactional
    public void orderTest2() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String checkIn = sf.format(date);
        testOrder.setCheckInDate(checkIn);
        // 添加订单
        String responseString = mockMvc.perform(post("/api/order/addOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testOrder)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(asJsonString(testOrder));
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));
        // 获取用户订单
        String responseString1 = mockMvc.perform(get("/api/order/12/getUserOrders")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        String content = new JSONObject(responseString1).getString("content");
        JSONArray jsonArray = new JSONArray(content);
        int len  = jsonArray.length();
        String c = new JSONArray(content).get(len-1).toString();
        int id = new JSONObject(c).getInt("id");
        System.out.println(id);
        // 正常执行
        String responseString2 = mockMvc.perform(get("/api/order/"+ id +"/execOrder")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString2);
        assertTrue(new JSONObject(responseString2).getBoolean("success"));

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}