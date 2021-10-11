package com.example.hotel.blImpl.user;


import com.example.hotel.bl.user.VIPService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.data.user.VIPMapper;
import com.example.hotel.po.User;
import com.example.hotel.po.VIP;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import com.example.hotel.vo.VipVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VIPServiceImpl implements VIPService {

    private final static String VIP_REGISTER_ERROR = "会员注册失败";

    @Autowired
    private VIPMapper vipMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerVIP(VipVO vipVO){
        VIP vip = new VIP();
        User user=accountMapper.getAccountById(vipVO.getUserId());
        BeanUtils.copyProperties(vipVO,vip);
        try {
            int vipLevel=(int)(user.getCredit()/500)+1;
            vip.setVipLevel(vipLevel);
            if(vipLevel>=6) {vip.setVipDiscount(0.7);}
            else{vip.setVipDiscount(1-0.05*vipLevel);}
            vipMapper.registerVIP(vip);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(VIP_REGISTER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public VIP getVIPInfoByUserId(Integer userId){
        return vipMapper.getVIPInfoByUserId(userId);
    }

    @Override
    public VIP getVIPInfoByVIPId(Integer id){
        return vipMapper.getVIPInfoByUserId(id);
    }

    @Override
    public void updateVIPLevel(int userId) {
        VIP vip = getVIPInfoByUserId(userId);
        if (vip!=null){
            double credit = accountMapper.getCurrentUserCredit(userId);
            //设置会员信用值每500升一等级
            int plus = (int)credit/500;
            int vipLevel = plus + 1;
            vipMapper.updateVIPLevel(userId,vipLevel);
        }
    }

    @Override
    public void updateVIPDiscount(int userId){
        VIP vip = getVIPInfoByUserId(userId);
        if (vip!=null){
           int level = vip.getVipLevel();
           //设置会员折扣为level1打95折扣，level2打9折。以此类推，每提高一级折扣提高0.05,但折扣上限为7折
           double init = 1;
           double vipDiscount = init - level*0.05;
           if(vipDiscount<=0.7)
               vipDiscount = 0.7;
           vipMapper.updateVIPDiscount(userId,vipDiscount);
        }
    }
}
