package com.example.hotel.controller.user;

import com.example.hotel.HotelApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.Session;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CreditControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void chargeCreditTest() throws Exception{
        // 信用充值
        String responseString = mockMvc.perform(post("/api/user/9/100/chargeCredit")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));

        // 根据id获取当前信用值
        String responseString1 = mockMvc.perform(get("/api/user/9/getUserCredits")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));
        String content = new JSONObject(responseString1).getString("content");
        // 由于不会清理缓存，所以取数组最后一个信用值结果，即最新信用值
        JSONArray jsonArray = new JSONArray(content);
        int len  = jsonArray.length();
        String c = new JSONArray(content).get(len-1).toString();
        System.out.println(new JSONObject(c).get("res"));
        Assert.assertEquals(100 + 100 * 100 * len, new JSONObject(c).get("res"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}