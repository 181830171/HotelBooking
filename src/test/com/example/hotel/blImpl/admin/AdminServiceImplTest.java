package com.example.hotel.blImpl.admin;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest(classes = HotelApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AccountMapper accountMapper;

    private static UserForm testManger1 = new UserForm(){{
        setEmail("testManger1@qq.com");
        setPassword("123456");
    }};

    private static UserForm testManger2 = new UserForm(){{
        setEmail("001@qq.com");
        setPassword("123456");
    }};

    @Test
    @Transactional
    public void addManagerTest1() {
        adminService.addManager(testManger1);
        User user = accountMapper.getAccountByEmail(testManger1.getEmail());
        Assert.assertEquals(testManger1.getEmail(), user.getEmail());
        Assert.assertEquals(testManger1.getPassword(), user.getPassword());
    }

    // 重复注册 邮箱重复
    @Test
    @Transactional
    public void addManagerTest2() {
        ResponseVO responseVO = adminService.addManager(testManger2);
        Assert.assertEquals("邮箱已存在", responseVO.getMessage());
    }


}