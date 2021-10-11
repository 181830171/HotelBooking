package com.example.hotel.data.hotel;


import com.example.hotel.po.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelPictureMapper {

    int addPicture(Picture picture);

    List<Picture> getPictures(@Param("hotelId") Integer hotelId);
}
