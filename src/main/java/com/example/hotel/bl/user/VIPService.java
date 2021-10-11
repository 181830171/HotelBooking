package com.example.hotel.bl.user;

import com.example.hotel.po.VIP;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.VipVO;


public interface VIPService {

    /**
     * 注册会员
     * @param vipVO
     * @return
     */
    ResponseVO registerVIP(VipVO vipVO);

    /**
     * 根据userid查找会员信息
     * @param userId
     * @return
     */
    VIP getVIPInfoByUserId(Integer userId);

    /**
     * 根据会员id查找相应的会员信息
     * @param id
     * @return
     */
    VIP getVIPInfoByVIPId(Integer id);

    /**
     * 根据会员的信用值更新会员等级
     * @param userId
     */
    void updateVIPLevel(int userId);

    /**
     * 根据会员等级更新相应会员折扣
     * @param userId
     */
    void updateVIPDiscount(int userId);


}
