package com.example.hotel.data.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @return
     */
     int createNewAccount(User user);

    /**
     * 根据邮箱查找账号
     * @param email
     * @return
     */
     User getAccountByEmail(@Param("email") String email);

     User getAccountById(@Param("id") int id);

     User getAccountByName(@Param("username") String username);

    /**
     * 更新用户信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
     int updateAccount(@Param("id") int id, @Param("password") String password,@Param("userName") String username, @Param("phoneNumber") String phonenumber);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(@Param("id") int id);

    /**
     * 获取用户当前的信用值
     */
    double getCurrentUserCredit(@Param("id") int id);

    /**
     * 更新用户信用值
     */
    int updateCredit(@Param("id") int id, @Param("change") double change);


}
