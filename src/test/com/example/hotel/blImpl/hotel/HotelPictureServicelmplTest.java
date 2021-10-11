package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelPictureService;
import com.example.hotel.po.Picture;
import com.example.hotel.vo.PictureVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.hotel.HotelApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = HotelApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class HotelPictureServicelmplTest {

    @Autowired
    private HotelPictureService hotelPictureService;

    private final PictureVO pictureVO1 = new PictureVO(){{
       setHotelId(3);
       setLinkStr("http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/three.jpg");
    }};

    private final PictureVO pictureVO2 = new PictureVO(){{
        setHotelId(3);
        setLinkStr("http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/four.jpg");
    }};

    @Test
    @Transactional
    public void addHotelPictureTest() {
        List<Picture> pre = hotelPictureService.getHotelPictures(3);
        hotelPictureService.addHotelPicture(pictureVO1);
        hotelPictureService.addHotelPicture(pictureVO2);
        List<Picture> post = hotelPictureService.getHotelPictures(3);
        Assert.assertEquals(2,post.size()-pre.size());
        Picture picture1 = post.get(post.size()-2);
        Assert.assertEquals(pictureVO1.getLinkStr(),picture1.getLinkStr());
        Picture picture2 = post.get(post.size()-1);
        Assert.assertEquals(pictureVO2.getLinkStr(),picture2.getLinkStr());
    }
}