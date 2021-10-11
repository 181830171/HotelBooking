package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CreditService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String ANNUL_ERROR = "撤销失败";
    private final static String EXEC_ERROR = "执行失败";
    private final static String Abnormal_ERROR = "设置异常订单失败";
    private final static String DELAYCHECKIN_ERROR = "延时入住失败";
    private final static String DELETEROOM_ERROR = "删除失败";
    private final static String ENDORDER_ERROR = "退房失败";
    private final static String CREDIT_ERROR = "信用不足";

    private final static String INTIME = " 14:00:00"; // 最早入住时间
    private final static String ENDTIME = " 23:59:59"; // 最晚入住时间


    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    CreditService creditService;




    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            // 因为要计算预定时间到入住时间的差值，所以这里的时间格式加上时分秒
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);

            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            // 如果信用值<0 则不能预定酒店
            if (user.getCredit() < 0) {
                return ResponseVO.buildFailure(CREDIT_ERROR);
            }
            //orderVO.setClientName(user.getUserName());
            //orderVO.setPhoneNumber(user.getPhoneNumber());
            // 这两句话将某个订单的客户名及电话设为登录用户的信息，但实际上我感觉应该由客户预定时填入的信息决定,因此删去了。
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);



            // 加入新订单后启动计时
            abnormalOrder(order.getId());
            //方便测试
            orderVO.setId(order.getId());
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    // 执行订单的操作
    @Override
    public ResponseVO execOrder(int orderid) {
        try{
            // 执行订单需要同时设置持久化对象和数据库内容!!!
            Order order = orderMapper.getOrderById(orderid);

            // 需要判断是否为当天执行
            String checkInDateStr = order.getCheckInDate() + INTIME;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date checkInDate = sf.parse(checkInDateStr);
            System.out.println("允许执行订单的时间: " + checkInDateStr);
            // 获取当前时间
            Date date = new Date();
            System.out.println("当前时间: " + sf.format(date));
            if (date.before(checkInDate)) {
                return ResponseVO.buildFailure("未到入住时间");
            }
            order.setOrderState("已入住");
            System.out.println("订单号: " + orderid + " " + order.getOrderState());
            orderMapper.execOrder(orderid);
            // 信用记录变更
            creditService.addCreditRecord(order.getUserId(), orderid, "已入住", order.getPrice());
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(EXEC_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    // 异常订单
    /*
    实现思路：订单创建时，使用 入住时间-预定时间 得到一个差值，使用计时器实现
     */
    @Override
    public ResponseVO abnormalOrder(int orderid) {
        try{
            // 创建order的持久化对象
            Order order = orderMapper.getOrderById(orderid);
            String createDateStr = order.getCreateDate();
            // 为入住时间加入时分秒
            String checkInDateStr = order.getCheckInDate() + ENDTIME;

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createDate = sf.parse(createDateStr);
            Date checkInDate = sf.parse(checkInDateStr);
            long millisecond = checkInDate.getTime()-createDate.getTime();
            System.out.println("延时: " + millisecond);

            // 使用Timer设定一个延时装置
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 如果在这段时间中执行了订单，那么数据库中orderState会被改变，
                    // 但之前获得的order是不变的，所以需要获取最新的持久化对象currentOrder,查看订单状态
                    Order currentOrder = orderMapper.getOrderById(orderid);

                    System.out.println("订单号: " + orderid);
                    System.out.println("最晚入住时间: " + checkInDateStr);
                    System.out.println("订单状态: " + currentOrder.getOrderState());
                    // 超时未入住，设定为异常订单 (订单状态为已入住和已撤销时不做改变)
                    if (!currentOrder.getOrderState().equals("已入住") && !currentOrder.getOrderState().equals("已撤销")) {
                        System.out.println("超时未入住设置为异常订单");
                        currentOrder.setOrderState("异常订单");
                        orderMapper.abnormalOrder(orderid);
                        // 信用记录变更
                        creditService.addCreditRecord(currentOrder.getUserId(), orderid, "异常订单", currentOrder.getPrice());
                    }
                    System.out.println("end");
                    timer.cancel();
                }
            }, millisecond);


        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(Abnormal_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    // 延时入住
    @Override
    public ResponseVO delayCheckIn(int orderid) {

        try {
            Order order = orderMapper.getOrderById(orderid);

            // 获取当前时间
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curdate = new Date();
            // 获取退房时间
            String checkOutDateStr = order.getCheckOutDate() + " 12:00:00";
            Date checkOutDate = sf.parse(checkOutDateStr);
            if (checkOutDate.before(curdate)) {
                return ResponseVO.buildFailure("超过最晚入住时间");
            }

            order.setOrderState("延迟入住");
            orderMapper.delayCheckIn(orderid);
            // 信用记录变更
            creditService.addCreditRecord(order.getUserId(), orderid, "延迟入住", order.getPrice());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELAYCHECKIN_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    //结束订单 退房
    @Override
    public ResponseVO endOrder(int orderid) {
        try {
            Order order = orderMapper.getOrderById(orderid);
            // 增加房间数量(负负得正)
            int curNum = -order.getRoomNum();
            hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), curNum);

            order.setOrderState("已退房");
            orderMapper.endOrder(orderid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ENDORDER_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    // 删除订单
    // 是否要更新房间信息
    @Override
    public ResponseVO deleteOrder(int orderid) {
        try {
            orderMapper.deleteOrder(orderid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETEROOM_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        try{
            Order order =orderMapper.getOrderById(orderid);
            int roomNums=-order.getRoomNum();
            hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),roomNums);
            orderMapper.annulOrder(orderid);

            // 如果撤销时间距离最晚入住时间<6小时，信用记录变更
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String checkInDateStr = order.getCheckInDate() + ENDTIME;
            Date checkInDate = sf.parse(checkInDateStr);
            double millisecond = checkInDate.getTime()-curDate.getTime();
            double hour = millisecond/(60*60*1000);
            if (hour < 6) {
                System.out.println(order.getUserId());
                creditService.addCreditRecord(order.getUserId(), orderid, "已撤销", order.getPrice());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void commentOrder(int orderid) {
        Order order = orderMapper.getOrderById(orderid);
        order.setOrderState("已评价");
        orderMapper.commentOrder(orderid);
    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders=orderMapper.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }




    @Override
    public Order getOrderById(int orderId){
        return orderMapper.getOrderById(orderId);
    }
}
