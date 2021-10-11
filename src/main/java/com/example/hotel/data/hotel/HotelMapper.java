package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    //Hotel getHotelById(@Param("id") Integer id);

    int deleteHotel(@Param("id") Integer id);

    int updateHotelRate(@Param("id") Integer id, @Param("rate") double rate);

    /**
     * 更新酒店信息
     * @author zzy
     * @param id
     * @param name
     * @param phoneNum
     * @param description
     * @param hotelStar
     */

    int updateHotelInfo(@Param("id") Integer id, @Param("name") String name, @Param("phoneNum") String phoneNum, @Param("description") String description, @Param("hotelStar") String hotelStar);

}
