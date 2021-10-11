package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponMapper {
    int insertCoupon(Coupon coupon);

    List<Coupon> selectByHotelId(@Param("hotelId") Integer hotelId);

    /**
     * 删除优惠券
     * @param couponId
     * @return
     */
    int deleteCoupon(@Param("couponId") int couponId);
}
