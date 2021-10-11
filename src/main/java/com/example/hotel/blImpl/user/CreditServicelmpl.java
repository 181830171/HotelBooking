package com.example.hotel.blImpl.user;

        import com.example.hotel.bl.user.AccountService;
        import com.example.hotel.bl.user.CreditService;
        import com.example.hotel.bl.user.VIPService;
        import com.example.hotel.data.user.CreditMapper;
        import com.example.hotel.po.UserCredit;
        import com.example.hotel.vo.ResponseVO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;

@Service
public class CreditServicelmpl implements CreditService {

    private static final String ADD_ERROR = "添加失败";
    private static final String CHARGE_ERROR = "充值失败";

    @Autowired
    private AccountService accountService;
    @Autowired
    private CreditMapper creditMapper;
    @Autowired
    private VIPService vipService;


    /*
     * 1. -撤销订单：如果距离入住时间<6小时，扣除信用值大小为(money/2)
     * 2. -异常订单：扣除信用值大小为money
     * 3. +延迟入住：恢复因异常订单扣除的信用值+执行订单增加的信用值
     * 4. +执行订单：增加信用值大小为money
     * 5. +充值:增加信用值大小为money*100
     */
    @Override
    public double getCreditChangeByAction(String action, double price) {
        double creditChange = 0;
        switch (action) {
            case "已撤销":
                creditChange = -price / 2;
                break;
            case "异常订单":
                creditChange = -price;
                break;
            case "延迟入住":
                creditChange = 2 * price;
                break;
            case "已入住":
                creditChange = price;
                break;
            case "充值":
                creditChange = price*100;
                break;
        }
        return creditChange;
    }


    @Override
    public ResponseVO addCreditRecord(int userid, int orderid, String action, double price) {
        try {

            double creditChange = getCreditChangeByAction(action, price);
            System.out.println("开始添加信用记录");
            System.out.println("change: " + creditChange);
            // 更新用户信用记录（user数据库中的数据)
            accountService.updateCredit(userid, creditChange);

            // 获取剩余的信用值
            double res = accountService.getCurrentUserCredit(userid);
            System.out.println("res: " + res);

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            UserCredit userCredit = new UserCredit();
            // 为该条信用记录设置时间，订单号，用户编号,动作，信用度变化，信用度结果
            userCredit.setCreateDate(curdate);
            userCredit.setOrderId(orderid);
            userCredit.setUserId(userid);
            userCredit.setActionType(action);
            userCredit.setChange(creditChange);
            userCredit.setRes(res);

            // 将该条信用记录加入数据库中
            creditMapper.addCreditRecord(userCredit);

            //信用值产生变化，会员等级和会员等级折扣发生变更
            vipService.updateVIPLevel(userid);
            vipService.updateVIPDiscount(userid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<UserCredit> getAllCredits() {
        return creditMapper.getAllCredits();
    }

    @Override
    public List<UserCredit> getUserCredits(int id) {

        return creditMapper.getUserCredits(id);
    }


    // 信用充值
    @Override
    public ResponseVO chargeCredit(int id, double money) {
        try {
            if (money > 0) {
                // 注意这里的订单编号为0，不属于由订单状态产生的信用变化
                addCreditRecord(id, 0, "充值", money);
            }
            else {
                return ResponseVO.buildFailure(CHARGE_ERROR);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(CHARGE_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }
}
