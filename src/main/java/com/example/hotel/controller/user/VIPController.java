package com.example.hotel.controller.user;


import com.example.hotel.bl.user.VIPService;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.VipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/api/user")
public class VIPController {

    @Autowired
    private VIPService vipService;

    @PostMapping("/registerVIP")
    public ResponseVO registerVIP(@RequestBody VipVO vipVO){
        return vipService.registerVIP(vipVO);
    }

    @GetMapping("/{userId}/getVIPInfoByUserId")
    public ResponseVO getVIPInfoByUserId(@PathVariable Integer userId){
        return ResponseVO.buildSuccess(vipService.getVIPInfoByUserId(userId));
    }
    @CrossOrigin
    @GetMapping("/{id}/getVIPInfoByVIPId")
    public ResponseVO getVIPInfoByVIPId(@PathVariable int id){
        return ResponseVO.buildSuccess(vipService.getVIPInfoByVIPId(id));
    }

    @PostMapping("/updateVIPLevel")
    public void updateVIPLevel(@PathVariable int userId){
        vipService.updateVIPLevel(userId);
    }

    @PostMapping("/updateVIPDiscount")
    public void updateVIPDiscount(@PathVariable int userId){
        vipService.updateVIPDiscount(userId);
    }

}
