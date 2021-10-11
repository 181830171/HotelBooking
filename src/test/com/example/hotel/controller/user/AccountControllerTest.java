package com.example.hotel.controller.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static UserForm userForm = new UserForm(){{
        setEmail("001@qq.com");
        setPassword("123456");
    }};

    private static UserVO testUser1 = new UserVO(){{
        setUserName("testUser1");
        setPassword("123456789");
        setEmail("testUser1@qq.com");
        setPhoneNumber("11111111111");
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
    public void registerAccountTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));

    }

    @Test
    public void loginTest() throws Exception {
        String responseString = mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userForm)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));

    }

    @Test
    @Transactional
    public void getUserInfoTest() throws Exception {
        mockMvc.perform(get("/api/user/2/getUserInfo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("content.email").value("002@qq.com"))
                .andExpect(jsonPath("content.userName").value("酒店管理员二号"))
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}