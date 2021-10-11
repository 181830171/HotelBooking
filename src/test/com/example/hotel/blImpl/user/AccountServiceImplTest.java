package com.example.hotel.blImpl.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = HotelApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AdminService adminService;

    private static UserVO testUser1 = new UserVO(){{
        setUserName("testUser1");
        setPassword("123456789");
        setEmail("testUser1@qq.com");
        setPhoneNumber("11111111111");
    }};

    private static UserVO testUser2 = new UserVO(){{
        setUserName("testUser2");
        setPassword("123456789");
        setEmail("123@qq.com");
        setPhoneNumber("22222222222");
    }};

    private static UserVO testUser3 = new UserVO(){{
        setUserName("测试一号");
        setPassword("123456789");
        setEmail("testUser3@qq.com");
        setPhoneNumber("22222222222");
    }};

    // 正常注册
    @Test
    @Transactional
    public void registerAccountTest1() {
        accountService.registerAccount(testUser1);
        User user = accountMapper.getAccountByEmail(testUser1.getEmail());
        Assert.assertEquals(testUser1.getUserName(), user.getUserName());
        Assert.assertEquals(testUser1.getPassword(), user.getPassword());
        Assert.assertEquals(testUser1.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(UserType.Client, user.getUserType());
        Assert.assertEquals(100, user.getCredit(), 0.0);
    }

    // 重复注册 邮箱重复
    @Test
    @Transactional
    public void registerAccountTest2() {
        ResponseVO responseVO = accountService.registerAccount(testUser2);
        System.out.println("response: " + responseVO.getMessage());
        Assert.assertEquals("邮箱已存在", responseVO.getMessage());
    }

    // 重复注册 用户名重复
    @Test
    @Transactional
    public void registerAccountTest3() {
        ResponseVO responseVO = accountService.registerAccount(testUser3);
        Assert.assertEquals("用户名已存在", responseVO.getMessage());
    }

    //账号密码填写正确登录
    @Test
    @Transactional
    public void loginTest1() {
        List<User> users = adminService.getAllUsers();
        User user = users.get(users.size()-1);
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(user,userForm);
        User logger = accountService.login(userForm);
        assertEquals(logger.getId(),user.getId());
        assertEquals(logger.getUserName(),user.getUserName());
        assertEquals(logger.getPassword(),user.getPassword());
        assertEquals(logger.getEmail(),user.getEmail());
    }

    //未注册用户登录
    @Test
    @Transactional
    public void loginTest2() {
        UserForm userForm = new UserForm(){{
            setEmail("000@11.com");
            setPassword("000000");
        }};
        User logger = accountService.login(userForm);
        Assert.assertNull(logger);
    }

    //已注册用户填写的登录密码错误
    @Test
    @Transactional
    public void loginTest3() {
        List<User> users = adminService.getAllUsers();
        User user = users.get(users.size()-1);
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(user,userForm);
        userForm.setPassword(userForm.getPassword()+"000");
        User logger = accountService.login(userForm);
        Assert.assertNull(logger);
    }


    @Test
    @Transactional
    public void updateUserInfoTest1() {
        accountService.updateUserInfo(4,"newPassword","user4","2333");
        User user=accountService.getUserInfo(4);//获取数据库中4号用户 ，两次皆修改成功
        Assert.assertEquals((Integer) 4,user.getId());
        Assert.assertEquals("newPassword",user.getPassword());
        Assert.assertEquals("user4",user.getUserName());
        Assert.assertEquals("2333",user.getPhoneNumber());
        // 大小写不同视为用户名不同(数据库读取时加binary来区分大小写)
        accountService.updateUserInfo(4,"newPASSWORD","USER4","180512");
        user=accountService.getUserInfo(4);
        Assert.assertEquals((Integer) 4,user.getId());
        Assert.assertEquals("newPASSWORD",user.getPassword());
        Assert.assertEquals("USER4",user.getUserName());
        Assert.assertEquals("180512",user.getPhoneNumber());
    }

    @Test
    @Transactional
    public void updateUserInfoTest2() {
        accountService.updateUserInfo(5,"password","user5","2333");
        User user=accountService.getUserInfo(5);
        Assert.assertEquals((Integer) 5,user.getId());
        Assert.assertEquals("password",user.getPassword());
        Assert.assertEquals("user5",user.getUserName());
        Assert.assertEquals("2333",user.getPhoneNumber());//第一次修改成功
        accountService.updateUserInfo(5,"anewPassword","USER5","123456");
        user=accountService.getUserInfo(5);
        Assert.assertEquals((Integer) 5,user.getId());
        Assert.assertEquals("password",user.getPassword());
        Assert.assertEquals("user5",user.getUserName());
        Assert.assertEquals("2333",user.getPhoneNumber());//修改失败，因为新密码超出了11位
    }


    @Test
    @Transactional
    public void deleteUserTest1() {
        List<User> users = adminService.getAllUsers();
        User last = users.get(users.size()-1);
        accountMapper.deleteUser(last.getId());
        Assert.assertNull(accountService.getUserInfo(last.getId()));
    }

    @Test
    @Transactional
    public void deleteUserTest2() {
        List<User> users = adminService.getAllUsers();
        Assert.assertNull(accountService.getUserInfo(10086));//id不存在的用户
        accountMapper.deleteUser(users.size()+1);
        Assert.assertNull(accountService.getUserInfo(users.size()+1));
    }

}