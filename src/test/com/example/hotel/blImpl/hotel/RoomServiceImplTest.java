package com.example.hotel.blImpl.hotel;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = HotelApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomServiceImplTest {

    @Autowired
    private RoomService roomService;

    private HotelRoom testRoom=new HotelRoom(){{
       setHotelId(2);
       setRoomType("DoubleBed");
       setCurNum(20);
       setTotal(20);
       setPrice(199);
       setDescription("多床 40-45㎡ 8层 有窗 WIFI免费 有线带宽免费");
       setPicture("http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-DoubleBed.jpg");
       setBreakfast(1);
       setPeopleNum(2);
    }};

    @Test
    @Transactional
    public void insertRoomInfo() {
        List<HotelRoom> pre=roomService.retrieveHotelRoomInfo(testRoom.getHotelId());
        roomService.insertRoomInfo(testRoom);
        List<HotelRoom> post=roomService.retrieveHotelRoomInfo(testRoom.getHotelId());
        Assert.assertEquals(1,post.size()-pre.size());//录入后该酒店房间种类增多
        HotelRoom curRoom=post.get(post.size()-1);//假定添加房间会添加到房间列表的最后
        Assert.assertEquals(testRoom.getCurNum(),curRoom.getCurNum());
        Assert.assertEquals(testRoom.getTotal(),curRoom.getTotal());
        Assert.assertEquals(testRoom.getPrice(),curRoom.getPrice(),0.001);
        Assert.assertEquals(testRoom.getRoomType(),curRoom.getRoomType());
        Assert.assertEquals(testRoom.getHotelId(),curRoom.getHotelId());
        int curNum=roomService.getRoomCurNum(testRoom.getHotelId(),"DoubleBed");
        Assert.assertEquals(testRoom.getCurNum(),curNum);

        Assert.assertEquals(testRoom.getDescription(),curRoom.getDescription());
        Assert.assertEquals(testRoom.getPeopleNum(),curRoom.getPeopleNum());
        Assert.assertEquals(testRoom.getBreakfast(),curRoom.getBreakfast());
        Assert.assertEquals(testRoom.getPicture(),curRoom.getPicture());

    }
}