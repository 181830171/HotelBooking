package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.CommentService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private static final String DELETEHOTEL_ERROR = "删除失败";
    private static final String UPDATE_ERROR = "更新失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;


    @Override
    public ResponseVO addHotel(HotelVO hotelVO){//String strBizRegion
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null){
            return ResponseVO.buildFailure("管理员不存在！创建酒店失败！");
        }
        else if(!manager.getUserType().equals(UserType.HotelManager)){//应该由网站管理员创建酒店，而非某个酒店管理员
            System.out.println(manager.getUserType());
            return ResponseVO.buildFailure("管理员无权限！创建酒店失败！");
        }
        List<HotelVO> hotels = retrieveHotels();
        for(HotelVO hotel:hotels){
            if(hotel.getName().equals(hotelVO.getName())&&hotel.getPhoneNum().equals(hotelVO.getPhoneNum()))
                return ResponseVO.buildFailure("酒店已存在！");
        }
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(hotelVO.getRate());
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setPicture(hotelVO.getPicture());
//        BizRegion bizRegion = BizRegion.RegionA;
//        switch(strBizRegion){
//            case "RegionB":
//                bizRegion = BizRegion.RegionB;
//                break;
//            case "RegionC":
//                bizRegion = BizRegion.RegionC;
//                break;
//            case "RegionD":
//                bizRegion = BizRegion.RegionD;
//                break;
//            case "RegionE":
//                bizRegion = BizRegion.RegionE;
//        }
//        hotel.setBizRegion(bizRegion);
        hotelMapper.insertHotel(hotel);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public ResponseVO updateHotelInfo(Integer hotelId, String name, String phoneNum, String description, String hotelStar) {
        try {
            hotelMapper.updateHotelInfo(hotelId, name, phoneNum, description, hotelStar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }


    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        if(hotelVO==null)
            return null;
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            roomVO.setDescription(r.getDescription());
            roomVO.setPicture(r.getPicture());
            roomVO.setPeopleNum(r.getPeopleNum());
            roomVO.setBreakfast(r.getBreakfast());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }



    // 删除酒店
    @Override
    public ResponseVO deleteHotel(Integer hotelId) {
        try {
            HotelVO hotel = retrieveHotelDetails(hotelId);
            if(hotel==null)
                return ResponseVO.buildFailure("酒店不存在");
            hotelMapper.deleteHotel(hotelId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETEHOTEL_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

//    @Override
//    public Hotel getHotelById(Integer hotelId) {
//        return hotelMapper.getHotelById(hotelId);
//    }

    @Override
    public void updateHotelRate(Integer hotelId, double rate) {
        hotelMapper.updateHotelRate(hotelId, rate);
    }

//    /**
//     * @param hotelId
//     * @return
//     */
//    @Override
//    public List<Order> getHotelOrders(Integer hotelId) {
//        List<Order> orders = orderService.getAllOrders();
//        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
//    }
    //修改循环依赖，将该方法移至orderservice中
}
