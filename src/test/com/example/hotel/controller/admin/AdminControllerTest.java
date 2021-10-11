package com.example.hotel.controller.admin;

import com.example.hotel.HotelApplication;
import com.example.hotel.controller.user.AccountController;
import com.example.hotel.vo.UserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static UserForm userForm1 = new UserForm(){{
        setEmail("001001@qq.com");
        setPassword("123456");
    }};

    private static UserForm userForm2 = new UserForm(){{
        setEmail("001@qq.com");
        setPassword("123456");
    }};

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    // 正常添加酒店管理员
    @Test
    @Transactional
    public void addManagerTest1() throws Exception{
        String responseString = mockMvc.perform(post("/api/admin/addManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userForm1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(asJsonString(userForm1));
        System.out.println("-----返回的json = " + responseString);
        assertTrue(new JSONObject(responseString).getBoolean("success"));
    }

    // 重复注册
    @Test
    @Transactional
    public void addManagerTest2() throws Exception{
        String responseString = mockMvc.perform(post("/api/admin/addManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userForm2)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(asJsonString(userForm2));
        System.out.println("-----返回的json = " + responseString);
        Assert.assertEquals("邮箱已存在", new JSONObject(responseString).getString("message"));
        assertFalse(new JSONObject(responseString).getBoolean("success"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}