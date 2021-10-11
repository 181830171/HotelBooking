package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class MultipleRoomsCouponStrategyImpl implements CouponMatchStrategy {
    /**
     * 判断某个订单是否满足多间优惠
     * @param orderVO
     * @param coupon
     * @return
     */

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon){
        if(coupon.getCouponType()==2 && orderVO.getRoomNum()>=3){
            return true;
        }

        return false;
    }
}
