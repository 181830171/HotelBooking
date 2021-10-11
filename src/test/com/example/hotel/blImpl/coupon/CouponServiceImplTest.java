package com.example.hotel.blImpl.coupon;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.VIPService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.VipVO;
import org.hibernate.validator.constraints.Currency;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = HotelApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class CouponServiceImplTest {

    @Autowired
    CouponService couponService;

    @Autowired
    AccountService accountService;

    @Autowired
    VIPService vipService;

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

    private CouponVO coupon2=new CouponVO(){{
        setHotelId(2);
        setName("限时优惠");
        setDescription("6月21日满400减50");
        setDiscount(-1);
        setDiscountMoney(50);
        setTargetMoney(400);
        setStartTime("2020-06-21");
        setEndTime("2020-06-21");
        setType(4);
        setStatus(1);
    }};

    private CouponVO coupon3=new CouponVO(){{
        setHotelId(3);
        setName("满减优惠");
        setDescription("满1000减50");
        setDiscount(-1);
        setDiscountMoney(50);
        setTargetMoney(1000);
        setType(3);
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

    @Test
    @Transactional
    public void getMatchOrderCoupon() {
        //在获取匹配优惠券时，订单并未创建，所以ordervo的createDate是空字段，每次调用ismatch方法都是以系统时间为判断标准
        //因此不做限时优惠的测试
        List<Coupon> coupons=couponService.getMatchOrderCoupon(testOrder);
        Assert.assertEquals(1,coupons.size());  //满减

        testOrder.setRoomNum(3);
        coupons=couponService.getMatchOrderCoupon(testOrder);
        Assert.assertEquals(2,coupons.size());  //满减 多间

        vipService.registerVIP(commonVIP);
        coupons=couponService.getMatchOrderCoupon(testOrder);
        Assert.assertEquals(3,coupons.size());  //生日 满减 多间

        testOrder.setUserId(11);
        vipService.registerVIP(enterpriseVIP);
        coupons=couponService.getMatchOrderCoupon(testOrder);
        Assert.assertEquals(3,coupons.size()); //5号顾客 满减 多间 企业

        testOrder.setPrice(300.0);
        coupons=couponService.getMatchOrderCoupon(testOrder);
        Assert.assertEquals(2,coupons.size()); // 多间 企业

        testOrder.setHotelId(1);
        coupons=couponService.getMatchOrderCoupon(testOrder);
        Assert.assertEquals(1,coupons.size()); //企业

    }

    @Test
    @Transactional
    public void addCouponTest1() {
        List<Coupon> pre= couponService.getHotelAllCoupon(coupon1.getHotelId());
        couponService.addCoupon(coupon1);
        List<Coupon> post=couponService.getHotelAllCoupon(coupon1.getHotelId());
        Assert.assertEquals(1,post.size()-pre.size());
        Coupon curCoupon=post.get(post.size()-1);//假定添加到最后
        Assert.assertEquals(coupon1.getHotelId(),curCoupon.getHotelId());
        Assert.assertEquals(coupon1.getName(),curCoupon.getCouponName());
        Assert.assertEquals(coupon1.getDescription(),curCoupon.getDescription());
        Assert.assertEquals(coupon1.getDiscount(),curCoupon.getDiscount(),0.001);
        Assert.assertEquals(coupon1.getType(),curCoupon.getCouponType());
    }

    @Test
    @Transactional
    public void addCouponTest2() {
        List<Coupon> pre= couponService.getHotelAllCoupon(coupon2.getHotelId());
        couponService.addCoupon(coupon2);
        List<Coupon> post=couponService.getHotelAllCoupon(coupon2.getHotelId());
        Assert.assertEquals(1,post.size()-pre.size());
        Coupon curCoupon=post.get(post.size()-1);//假定添加到最后
        Assert.assertEquals(coupon2.getHotelId(),curCoupon.getHotelId());
        Assert.assertEquals(coupon2.getName(),curCoupon.getCouponName());
        Assert.assertEquals(coupon2.getDescription(),curCoupon.getDescription());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime st = curCoupon.getStartTime();
        LocalDateTime et = curCoupon.getEndTime();
        String startTime = df.format(st);
        String endTime=df.format(et);

        Assert.assertEquals(coupon2.getStartTime()+" 00:00:00",startTime);
        Assert.assertEquals(coupon2.getEndTime()+" 23:59:59",endTime);
        Assert.assertEquals((double)coupon2.getTargetMoney(),curCoupon.getTargetMoney(),0.001);
        Assert.assertEquals((double)coupon2.getDiscountMoney(),curCoupon.getDiscountMoney(),0.001);
        Assert.assertEquals(coupon2.getType(),curCoupon.getCouponType());
    }

    @Test
    @Transactional
    public void addCouponTest3() {
        List<Coupon> pre= couponService.getHotelAllCoupon(coupon3.getHotelId());
        couponService.addCoupon(coupon3);
        List<Coupon> post=couponService.getHotelAllCoupon(coupon3.getHotelId());
        Assert.assertEquals(1,post.size()-pre.size());
        Coupon curCoupon=post.get(post.size()-1);//假定添加到最后
        Assert.assertEquals(coupon3.getHotelId(),curCoupon.getHotelId());
        Assert.assertEquals(coupon3.getName(),curCoupon.getCouponName());
        Assert.assertEquals(coupon3.getDescription(),curCoupon.getDescription());
        Assert.assertEquals((double)coupon3.getTargetMoney(),curCoupon.getTargetMoney(),0.001);
        Assert.assertEquals((double)coupon3.getDiscountMoney(),curCoupon.getDiscountMoney(),0.001);
        Assert.assertEquals(coupon3.getType(),curCoupon.getCouponType());
    }
}