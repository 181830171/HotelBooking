package com.example.hotel.blImpl.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.user.VIPService;
import com.example.hotel.data.user.VIPMapper;
import com.example.hotel.po.VIP;
import com.example.hotel.vo.VipVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)

public class VIPServiceImplTest {

    @Autowired
    private VIPService vipService;
    @Autowired
    private VIPMapper vipMapper;

    private static VipVO testVIP1 = new VipVO(){{
        setUserId(7);
        setVipName("测试一号");
        setVIPType("普通会员");
        setBirthday("1990-01-01");
    }};

    @Test
    @Transactional
    public void registerVIPTest1() {
        vipService.registerVIP(testVIP1);
        VIP vip = vipService.getVIPInfoByUserId(testVIP1.getUserId());//从Mapper改为Service是为了使覆盖率更大
        Assert.assertEquals(testVIP1.getVipName(), vip.getVipName());
        Assert.assertEquals(testVIP1.getBirthday(), vip.getBirthday());
        Assert.assertEquals(testVIP1.getVIPType(), vip.getVIPType());
    }


}