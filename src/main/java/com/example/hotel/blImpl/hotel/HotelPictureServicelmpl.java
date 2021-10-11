package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelPictureService;
import com.example.hotel.data.hotel.HotelPictureMapper;
import com.example.hotel.po.Picture;
import com.example.hotel.vo.PictureVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HotelPictureServicelmpl implements HotelPictureService {

    private static final String ADD_ERROR = "添加图片失败";

    @Autowired
    private HotelPictureMapper hotelPictureMapper;

    @Override
    public ResponseVO addHotelPicture(PictureVO pictureVO) {
        try {
            Picture picture = new Picture();
            BeanUtils.copyProperties(pictureVO, picture);
            hotelPictureMapper.addPicture(picture);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Picture> getHotelPictures(Integer hotelId) {

        return hotelPictureMapper.getPictures(hotelId);
    }
}
