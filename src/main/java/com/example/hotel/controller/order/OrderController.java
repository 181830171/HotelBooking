package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    // 加入执行订单的操作 controller->bl->bllmpl
    @GetMapping("/{orderid}/execOrder")
    public ResponseVO execOrder(@PathVariable int orderid) {return orderService.execOrder(orderid); }

    // 异常订单
    @GetMapping("/{orderid}/abnormalOrder")
    public ResponseVO abnormalOrder(@PathVariable int orderid) {return orderService.abnormalOrder(orderid); }

    // 延时入住
    @GetMapping("/{orderid}/delayCheckIn")
    public ResponseVO delayCheckIn(@PathVariable int orderid) {return
            orderService.delayCheckIn(orderid);
    }

    // 删除订单
    @DeleteMapping("/{orderid}/deleteOrder")
    public ResponseVO deleteOrder(@PathVariable int orderid) {return orderService.deleteOrder(orderid); }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    @GetMapping("/{orderid}/endOrder")
    public ResponseVO endOrder(@PathVariable int orderid) { return orderService.endOrder(orderid); }

    @GetMapping("/{orderid}/getOrderById")//5.8
    public ResponseVO getOrderById(@PathVariable int orderid){return ResponseVO.buildSuccess(orderService.getOrderById(orderid));}

    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

}
