package com.example.hotel.controller.hotel;

import com.example.hotel.HotelApplication;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.UserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HotelControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final UserForm testManager = new UserForm(){{
        setEmail("888@qq.com");
        setPassword("123456");
    }};

    private final HotelVO testHotel = new HotelVO(){{
        setName("测试酒店1");
        setDescription("欢迎您入住");
        setAddress("南京市栖霞区仙林大道163号");
        setBizRegion("RegionA");
        setHotelStar("Five");
        setPhoneNum("1871234567");
    }};

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @Transactional
    public void hotelTest1() throws Exception {
        testHotel.setManagerId(9);
        String responseString = mockMvc.perform(post("/api/hotel/addHotel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testHotel)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        Assert.assertEquals("管理员无权限！创建酒店失败！", new JSONObject(responseString).getString("message"));

    }

    @Test
    @Transactional
    public void hotelTest2() throws Exception {
        testHotel.setManagerId(12345);
        String responseString = mockMvc.perform(post("/api/hotel/addHotel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testHotel)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        Assert.assertEquals("管理员不存在！创建酒店失败！", new JSONObject(responseString).getString("message"));

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}