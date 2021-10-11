package com.example.hotel.data.user;

import com.example.hotel.po.User;
import com.example.hotel.po.UserCredit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zzy
 */

@Mapper
@Repository
public interface CreditMapper {

    /**
     * 增加一条信用记录
     */

    int addCreditRecord(UserCredit userCredit);

    /**
     * 获取全部信用记录
     */

    List<UserCredit> getAllCredits();

    /**
     * 用户查看自己的信用变更记录
     */

    List<UserCredit> getUserCredits(@Param("id") int id);
}
