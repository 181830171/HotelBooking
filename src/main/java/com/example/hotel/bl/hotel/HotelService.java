package com.example.hotel.bl.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    ResponseVO addHotel(HotelVO hotelVO) throws ServiceException;


    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 修改酒店相关信息
     * @author zzy
     * @param hotelId
     * @param name
     * @param phoneNum
     * @param description
     * @param hotelStar
     */

    ResponseVO updateHotelInfo(Integer hotelId, String name, String phoneNum, String description, String hotelStar);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    /**
     * 按Id查询某一酒店
     * @author zzy
     * @param hotelId
     * @return
     */

    //Hotel getHotelById(Integer hotelId);

    /**
     * 删除酒店
     * @author zzy
     * @param hotelId
     * @return
     */

    ResponseVO deleteHotel(Integer hotelId);

    /**
     * 更新酒店评分
     * @author zzy
     * @param hotelId
     * @return
     */

    void updateHotelRate(Integer hotelId, double rate);






}
