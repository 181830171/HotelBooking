package com.example.hotel.bl.user;

import com.example.hotel.po.UserCredit;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author zzy
 */

public interface CreditService {


    /**
     * 根据行为获取信用改变情况
     * 1. -撤销订单：如果距离入住时间<6小时，扣除信用值大小为(money/2)
     * 2. -异常订单：扣除信用值大小为money
     * 3. +延迟入住：恢复因异常订单扣除的信用值
     * 4. +执行订单：增加信用值大小为money
     * 5. +充值:增加信用值大小为money
     * @param action
     * @param price 订单价格
     * @return
     */

    double getCreditChangeByAction(String action, double price);


    /**
     * 向数据库中添加信用记录
     */

    ResponseVO addCreditRecord(int userid, int orderid, String action, double price);

    /**
     * 获取全部信用记录
     */

    List<UserCredit> getAllCredits();


    /**
     * 获得指定用户的所有信用记录
     * @param id
     * @return
     */
    List<UserCredit> getUserCredits(int id);

    /**
     * 信用充值（需要前端内容）
     * @param id
     * @param money 充值
     * @return
     */

    ResponseVO chargeCredit(int id, double money);


}
