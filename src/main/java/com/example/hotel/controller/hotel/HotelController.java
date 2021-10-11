package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelPictureService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.PictureVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelPictureService hotelPictureService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {//,@RequestParam String strBizRegion

        return hotelService.addHotel(hotelVO);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess();
    }

    @PostMapping("/{hotelId}/hotelInfo/update")
    public ResponseVO updateHotel(@RequestBody HotelVO hotelVO, @PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.updateHotelInfo(hotelId, hotelVO.getName(), hotelVO.getPhoneNum(), hotelVO.getDescription(), hotelVO.getHotelStar()));
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }


    // 删除酒店
    @DeleteMapping("/{hotelId}/deleteHotel")
    public ResponseVO deleteHotel(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.deleteHotel(hotelId));
    }

    // 添加酒店图片
    @PostMapping("/addHotelPicture")
    public ResponseVO addHotelPicture(@RequestBody PictureVO pictureVO) {
        return ResponseVO.buildSuccess(hotelPictureService.addHotelPicture(pictureVO));
    }

    //获取酒店相关图片
    @GetMapping("/{hotelId}/getHotelPictures")
    public ResponseVO getHotelPictures(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelPictureService.getHotelPictures(hotelId));
    }



}
