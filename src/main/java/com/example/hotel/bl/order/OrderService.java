package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 执行订单
     * @author zzy
     * @param orderid
     * @return
     */

    ResponseVO execOrder(int orderid);

    /**
     * 异常订单
     * @author zzy
     * @param orderid
     * @return
     */

    ResponseVO abnormalOrder(int orderid);

    /**
     * 延时入住
     * @author zzy
     * @param orderid
     * @return
     */

    ResponseVO delayCheckIn(int orderid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 删除订单
     * @author zzy
     * @param orderid
     * @return
     */

    ResponseVO deleteOrder(int orderid);

    /**
     * 结束订单：退房
     * @author zzy
     * @param orderid
     * @return
     */

    ResponseVO endOrder(int orderid);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 按id查看某一订单
     * @param orderid
     * @return
     */
    Order getOrderById(int orderid);//5.8

    /**
     * 评价订单
     * @param orderid
     * @return
     */

    void commentOrder(int orderid);
}
