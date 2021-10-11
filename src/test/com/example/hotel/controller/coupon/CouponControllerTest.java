package com.example.hotel.controller.coupon;

import com.example.hotel.HotelApplication;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.VipVO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CouponControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private CouponVO coupon1=new CouponVO(){{
        setHotelId(1);
        setName("多间优惠");
        setDescription("满三间房打9折");
        setDiscount(0.9);
        setDiscountMoney(0);
        setTargetMoney(-1);
        setType(2);
        setStatus(1);
    }};

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
        setRoomType("Family");
        setPrice((double) 900);
        setUserId(10);
    }};

    private static VipVO commonVIP=new VipVO(){{
        setVIPType("普通会员");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        setBirthday(curdate.substring(0,10));//假定该会员当天生日
        setUserId(10);
    }};

    private static VipVO enterpriseVIP=new VipVO(){{
        setVIPType("企业会员");
        setEnterpriseName("nju");
        setUserId(11);
    }};

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    public void addHotelTargetMoneyCoupon() throws Exception{
        String responseString = mockMvc.perform(post("/api/coupon/addCoupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(coupon1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));
        String content = new JSONObject(responseString).getString("content");
        JSONObject jsonObject = new JSONObject(content);
        Assert.assertEquals(coupon1.getHotelId(), jsonObject.get("hotelId"));
        Assert.assertEquals(coupon1.getType(), jsonObject.get("type"));
        Assert.assertEquals(coupon1.getDescription(), jsonObject.get("description"));
        Assert.assertEquals(coupon1.getDiscount(), jsonObject.getDouble("discount"), 0);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}