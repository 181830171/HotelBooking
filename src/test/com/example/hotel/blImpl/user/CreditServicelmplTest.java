package com.example.hotel.blImpl.user;

import com.example.hotel.HotelApplication;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CreditService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest(classes= HotelApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CreditServicelmplTest {

    @Autowired
    private CreditService creditService;
    @Autowired
    private AccountService accountService;

    @Test
    @Transactional
    public void chargeCreditTest1() {
        double preCredit = accountService.getCurrentUserCredit(4);
        creditService.chargeCredit(4, 100);
        double afterCredit = accountService.getCurrentUserCredit(4);
        Assert.assertEquals(preCredit + 100 * 100, afterCredit, 0.0);
    }

    // 充值金额为负 信用值不变
    @Test
    @Transactional
    public void chargeCreditTest2() {
        double preCredit = accountService.getCurrentUserCredit(4);
        creditService.chargeCredit(4, -100);
        double afterCredit = accountService.getCurrentUserCredit(4);
        Assert.assertEquals(preCredit, afterCredit, 0.0);
    }


}