package com.example.hotel.controller.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.vo.VipVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// 前端避免了重复注册 测试前重新运行数据库

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class VIPControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static VipVO testVIP1 = new VipVO(){{
        setUserId(10);
        setVipName("test");
        setVIPType("普通会员");
        setBirthday("1990-01-01");
    }};

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @Transactional
    public void registerVIP() throws Exception{
        // 注册会员
        String responseString = mockMvc.perform(post("/api/user/registerVIP")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testVIP1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));

        // 获取会员信息
        String responseString1 = mockMvc.perform(get("/api/user/10/getVIPInfoByUserId")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));
        String content = new JSONObject(responseString1).getString("content");
        JSONObject jsonObject = new JSONObject(content);
        System.out.println("用户id: " + jsonObject.get("userId"));
        Assert.assertEquals(10, jsonObject.get("userId"));
        Assert.assertEquals("test", jsonObject.get("vipName"));
        Assert.assertEquals("1990-01-01", jsonObject.get("birthday"));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}