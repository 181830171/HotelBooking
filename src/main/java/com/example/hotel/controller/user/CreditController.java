package com.example.hotel.controller.user;


import com.example.hotel.bl.user.CreditService;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/user")
public class CreditController {

    @Autowired
    CreditService creditService;

    @GetMapping("/{id}/getUserCredits")
    public ResponseVO retrieveUserCredits(@PathVariable int id) {
        return ResponseVO.buildSuccess(creditService.getUserCredits(id));
    }

    @GetMapping("/{id}/getAllCredits")
    public ResponseVO retrieveAllCredits(@PathVariable int id) {
        return ResponseVO.buildSuccess(creditService.getAllCredits());
    }

    @PostMapping("/{id}/{money}/chargeCredit")
    public ResponseVO chargeCredit(@PathVariable int id, @PathVariable double money) {
        return creditService.chargeCredit(id, money);
    }
}
