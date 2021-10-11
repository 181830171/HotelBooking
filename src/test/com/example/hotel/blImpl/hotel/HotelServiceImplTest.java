package com.example.hotel.blImpl.hotel;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ImageBanner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = HotelApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class HotelServiceImplTest {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AccountService accountService;

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

    //正确添加新的酒店
    @Test
    @Transactional
    public void addHotelTest1() throws ServiceException {
        adminService.addManager(testManager);
        List<User> managers = adminService.getAllManagers();
        User hotelManager = managers.get(managers.size()-1);
        int managerId = hotelManager.getId();
        testHotel.setManagerId(managerId);
        List<HotelVO> pre = hotelService.retrieveHotels();
        hotelService.addHotel(testHotel);
        List<HotelVO> post = hotelService.retrieveHotels();
        Assert.assertEquals(1,post.size()-pre.size());
        HotelVO curHotel = post.get(post.size()-1);
        Assert.assertEquals(testHotel.getName(),curHotel.getName());
        Assert.assertEquals(testHotel.getAddress(),curHotel.getAddress());
        Assert.assertEquals(testHotel.getBizRegion(),curHotel.getBizRegion());
        Assert.assertEquals(testHotel.getHotelStar(),curHotel.getHotelStar());
        Assert.assertEquals(testHotel.getPhoneNum(),curHotel.getPhoneNum());
        Assert.assertEquals(testHotel.getManagerId(),curHotel.getManagerId());
    }

    //管理员不存在
    @Test
    @Transactional
    public void addHotelTest2() throws ServiceException {
        testHotel.setManagerId(12345);
        ResponseVO response = hotelService.addHotel(testHotel);
        Assert.assertEquals("管理员不存在！创建酒店失败！",response.getMessage());
    }

    //除网站管理员外的酒店管理员无权限
    @Test
    @Transactional
    public void addHotelTest3() throws ServiceException {
        //id为9的管理员为酒店管理员，非网站管理员，因此添加酒店时选择的酒店管理员身份无效
        testHotel.setManagerId(9);
        ResponseVO response = hotelService.addHotel(testHotel);
        Assert.assertEquals("管理员无权限！创建酒店失败！",response.getMessage());
    }

    //酒店已创建
    @Test
    @Transactional
    public void addHotelTest4() throws ServiceException {
        List<HotelVO> hotels = hotelService.retrieveHotels();
        HotelVO existedHotel = hotels.get(1);//选取儒家酒店，因为该酒店管理员type为网站管理员
        ResponseVO response = hotelService.addHotel(existedHotel);
        Assert.assertEquals("酒店已存在！",response.getMessage());
    }

    @Test
    @Transactional
    public void updateHotelInfoTest1() {
        hotelService.updateHotelInfo(1,"testHotel","131805","物美价廉，欢迎入住", "Five");
        HotelVO hotel=hotelService.retrieveHotelDetails(1);
        Assert.assertEquals((Integer) 1,hotel.getId());
        Assert.assertEquals("testHotel",hotel.getName());
        Assert.assertEquals("131805",hotel.getPhoneNum());
        Assert.assertEquals("物美价廉，欢迎入住",hotel.getDescription());
        Assert.assertEquals("Five",hotel.getHotelStar());
    }

    @Test
    @Transactional
    public void updateHotelInfoTest2() {
        hotelService.updateHotelInfo(2,"testHotel2","1001201120","欢迎入住", "Four");
        HotelVO hotel=hotelService.retrieveHotelDetails(2);
        Assert.assertEquals((Integer) 2,hotel.getId());
        Assert.assertEquals("testHotel2",hotel.getName());
        Assert.assertEquals("1001201120",hotel.getPhoneNum());
        Assert.assertEquals("欢迎入住",hotel.getDescription());
        Assert.assertEquals("Four",hotel.getHotelStar());
    }


    @Test
    @Transactional
    public void deleteHotelTest1() {
        hotelService.deleteHotel(1);
        assertNull(hotelService.retrieveHotelDetails(1));
    }

    @Test
    @Transactional
    public void deleteHotelTest2() {
        ResponseVO response = hotelService.deleteHotel(2000);
        Assert.assertEquals("酒店不存在",response.getMessage());
    }

}