package com.example.hotel.data.user;

import com.example.hotel.po.VIP;
import com.example.hotel.vo.ResponseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VIPMapper {

    /**
     * 注册会员
     *
     * @return
     */
    int registerVIP(VIP vip);

    /**
     * 根据userid查找会员信息
     * @param userId
     * @return
     */
    VIP getVIPInfoByUserId(@Param("userId") Integer userId);

    /**
     * 根据会员id查找相应的会员信息
     * @param id
     * @return
     */
    VIP getVIPInfoByVIPId(@Param("id") Integer id);

    /**
     * 根据会员信用值更新会员等级
     * @param userId
     */
    void updateVIPLevel(@Param("userId") int userId,@Param("vipLevel") int vipLevel);

    /**
     * 根据会员等级更新会员折扣
     * @Param userId
     */
    void updateVIPDiscount(@Param("userId") int userId,@Param("vipDiscount") double vipDiscount);


}
