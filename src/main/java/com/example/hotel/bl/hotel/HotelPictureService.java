package com.example.hotel.bl.hotel;

import com.example.hotel.po.Picture;
import com.example.hotel.vo.PictureVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface HotelPictureService {

    /**
     * 添加酒店相关图片
     * @author zzy
     * @param pictureVO
     */

    ResponseVO addHotelPicture(PictureVO pictureVO);

    /**
     * 获取图片
     * @author zzy
     * @param hotelId
     */

    List<Picture> getHotelPictures(Integer hotelId);
}
