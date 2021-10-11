package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.data.user.VIPMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.VIP;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {

    private final static String DELETECOUPON_ERROR = "删除失败";

    private final  TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    private final  TimeCouponStrategyImpl timeCouponStrategy;

    private final HolidayCouponStrategyImpl holidayCouponStrategy;

    private final MultipleRoomsCouponStrategyImpl multipleRoomsCouponStrategy;

    private final CouponMapper couponMapper;

    private final VIPMapper vipMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             HolidayCouponStrategyImpl holidayCouponStrategy,
                             MultipleRoomsCouponStrategyImpl multipleRoomsCouponStrategy,
                             CouponMapper couponMapper, VIPMapper vipMapper) {
        this.couponMapper = couponMapper;
        this.vipMapper = vipMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        this.holidayCouponStrategy=holidayCouponStrategy;
        this.multipleRoomsCouponStrategy=multipleRoomsCouponStrategy;

        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(holidayCouponStrategy);
        strategyList.add(multipleRoomsCouponStrategy);
    }

    @Autowired
    private HotelService hotelService;



    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());

        List<Coupon> websiteCoupons=getHotelAllCoupon(-1);//网站优惠券

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }
        for(int i=0;i<websiteCoupons.size();i++){//网站优惠券
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, websiteCoupons.get(i))) {
                    availAbleCoupons.add(websiteCoupons.get(i));
                }
            }
        }

        //普通VIP生日特惠和企业VIP企业客户特惠
        VIP vip = vipMapper.getVIPInfoByUserId(orderVO.getUserId());
        if (vip!=null) {
            double vipLvlDiscount = vip.getVipDiscount();
            int hotelId = orderVO.getHotelId();
            double vipRegionDiscount = BizRegion.valueOf(hotelService.retrieveHotelDetails(hotelId).getBizRegion()).getVipRegionDiscount();

            Coupon vipCoupon = new Coupon();
            vipCoupon.setHotelId(-1);
            vipCoupon.setStatus(1);
            if (vip.getVIPType().equals("普通会员")){
                //如果生日月份与预订月份相同，则打8折
                int birthMonth=Integer.valueOf(vip.getBirthday().substring(5,7));
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String curdate = sf.format(date);
                int curmonth=Integer.valueOf(curdate.substring(5,7));
                double discount = 0.8;
                if(birthMonth==curmonth){
                    vipCoupon.setCouponType(1);
                    vipCoupon.setCouponName("普通VIP生日特惠");
                    vipCoupon.setDescription("普通VIP生日特惠8折");
                    vipCoupon.setTargetMoney(-1);
                    vipCoupon.setDiscount(discount);
                    availAbleCoupons.add(vipCoupon);
                }
            }
            else{
                double discount = 0.88;//企业会员为88折
                vipCoupon.setCouponType(6);
                vipCoupon.setCouponName("企业VIP特惠");
                vipCoupon.setDescription("合作企业VIP特惠88折");
                vipCoupon.setTargetMoney(-1);
                vipCoupon.setDiscount(discount);
                availAbleCoupons.add(vipCoupon);
            }
        }

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }

    @Override
    public CouponVO addCoupon(CouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        if(couponVO.getType()==4) {
            coupon.setStartTime(LocalDateTime.parse(couponVO.getStartTime()+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            coupon.setEndTime(LocalDateTime.parse(couponVO.getEndTime()+" 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        coupon.setDiscount(couponVO.getDiscount());
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public ResponseVO deleteCoupon(int couponId){
        try {
            couponMapper.deleteCoupon(couponId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETECOUPON_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }
}
