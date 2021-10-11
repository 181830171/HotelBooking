package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class HolidayCouponStrategyImpl implements CouponMatchStrategy {
    /**
     * 判断某个订单是否满足节日优惠策略
     * @param orderVO
     * @param coupon
     */

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        LocalDateTime creatTime = LocalDateTime.parse(curdate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(coupon.getCouponType()==5 && coupon.getStartTime().isBefore(creatTime)&& coupon.getEndTime().isAfter(creatTime)&& orderVO.getPrice() >= coupon.getTargetMoney()){
            return true;
        }
        return false;
    }
}
