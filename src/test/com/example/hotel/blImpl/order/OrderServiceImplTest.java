package com.example.hotel.blImpl.order;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.po.UserCredit;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.RoomVO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private CreditMapper creditMapper;
    private final static String ENDTIME = " 23:59:59";
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
        setCreateDate("2020-06-21 18:58:49");
        setRoomType("家庭房");
        setPrice((double) 900);
        setUserId(8);
    }};

    @Test
    @Transactional
    public void addOrderTest1() {//成功添加订单
        List<Order> pre=orderService.getHotelOrders(testOrder.getHotelId());
        int preRoomNum=roomMapper.getRoomCurNum(testOrder.getHotelId(),testOrder.getRoomType());
        orderService.addOrder(testOrder);
        List<Order> post=orderService.getHotelOrders(testOrder.getHotelId());
        int postRoomNum=roomMapper.getRoomCurNum(testOrder.getHotelId(),testOrder.getRoomType());
        Assert.assertEquals(1,post.size()-pre.size());//订单数加一
        Assert.assertEquals(testOrder.getRoomNum(),(Integer)(preRoomNum-postRoomNum));//房间数相应减少
        Order order=orderService.getOrderById(testOrder.getId());
        Assert.assertEquals("已预订",order.getOrderState());
    }

    @Test
    @Transactional
    public void addOrderTest2() {//信用值不足，添加订单失败
        List<Order> pre=orderService.getHotelOrders(testOrder.getHotelId());
        int preRoomNum=roomMapper.getRoomCurNum(testOrder.getHotelId(),testOrder.getRoomType());
        //设置用户信用值为-100
        User user=accountMapper.getAccountById(testOrder.getUserId());
        accountMapper.updateCredit(testOrder.getUserId(),-(user.getCredit()+100));

        orderService.addOrder(testOrder);
        List<Order> post=orderService.getHotelOrders(testOrder.getHotelId());
        int postRoomNum=roomMapper.getRoomCurNum(testOrder.getHotelId(),testOrder.getRoomType());
        Assert.assertEquals(0,post.size()-pre.size());
        Assert.assertEquals(0,preRoomNum-postRoomNum);//订单数，房间数都不变
    }

    @Test
    @Transactional
    public void addOrderTest3() {//房间剩余数量不足，添加订单失败
        List<Order> pre=orderService.getHotelOrders(testOrder.getHotelId());
        //将订单对应的酒店的该房间类型剩余数量设为0；
        int curNum=roomMapper.getRoomCurNum(testOrder.getHotelId(),testOrder.getRoomType());
        roomMapper.updateRoomInfo(testOrder.getHotelId(),testOrder.getRoomType(),curNum);

        orderService.addOrder(testOrder);
        List<Order> post=orderService.getHotelOrders(testOrder.getHotelId());
        int postRoomNum=roomMapper.getRoomCurNum(testOrder.getHotelId(),testOrder.getRoomType());
        Assert.assertEquals(0,post.size()-pre.size());
        Assert.assertEquals(0,postRoomNum);//订单数不变，房间数为0
    }

    // 超时
    @Test
    @Transactional
    public void execOrderTest1() {
        List<UserCredit> pre=creditMapper.getUserCredits(testOrder.getUserId());
        orderService.addOrder(testOrder);
        orderService.execOrder(testOrder.getId());

        Order order = orderMapper.getOrderById(testOrder.getId());
        //测试订单状态变化
        Assert.assertEquals("已预订",order.getOrderState());

    }



    @Test
    @Transactional
    public void delayCheckInTest() {
        List<UserCredit> pre=creditMapper.getUserCredits(testOrder.getUserId());
        orderService.addOrder(testOrder);
        orderService.delayCheckIn(testOrder.getId());
        User user=accountMapper.getAccountById(8);
        Assert.assertEquals(1900,user.getCredit(),0.5);
        Order order=orderMapper.getOrderById(testOrder.getId());
        Assert.assertEquals("延迟入住",order.getOrderState());
        List<UserCredit> post=creditMapper.getUserCredits(testOrder.getUserId());
        //测试信用记录条目变化
        Assert.assertEquals(1,post.size()-pre.size());
    }

    @Test
    @Transactional
    public void endOrderTest() {
        orderService.addOrder(testOrder);
        int num=roomMapper.getRoomCurNum(2,"家庭房");
        orderService.endOrder(testOrder.getId());
        Order order=orderMapper.getOrderById(testOrder.getId());
        Assert.assertEquals(num+testOrder.getRoomNum(),roomMapper.getRoomCurNum(2,"家庭房"));
        Assert.assertEquals("已退房",order.getOrderState());
    }

    @Test
    @Transactional
    public void annualOrderTest() throws ParseException {
        List<UserCredit> pre=creditMapper.getUserCredits(testOrder.getUserId());
        orderService.addOrder(testOrder);
        User user=accountMapper.getAccountById(4);
        double credit=user.getCredit();
        orderService.annulOrder(testOrder.getId());
        user=accountMapper.getAccountById(4);
        Order order=orderMapper.getOrderById(testOrder.getId());
        Assert.assertEquals("已撤销",order.getOrderState());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String checkInDateStr = order.getCheckInDate() + ENDTIME;
        Date checkInDate = sf.parse(checkInDateStr);
        double millisecond = checkInDate.getTime()-curDate.getTime();
        double hour = millisecond/(60*60*1000);
        if(hour<6){
            //撤销时间离入住不足6小时信用值减房价一半
            Assert.assertEquals(-350,user.getCredit(),0.5);
            List<UserCredit> post=creditMapper.getUserCredits(testOrder.getUserId());
            //测试信用记录条目变化
            Assert.assertEquals(1,post.size()-pre.size());

        }else{
            //大于6小时则不改变信用
            Assert.assertEquals(100,100,0.5);
        }
    }


}