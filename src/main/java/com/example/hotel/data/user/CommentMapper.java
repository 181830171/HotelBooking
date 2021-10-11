package com.example.hotel.data.user;


import com.example.hotel.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    int addComment(Comment comment);

    List<Comment> getCommentsByHotelId(@Param("hotelId") int hotelId);

    List<Comment> getCommentsByUserId(@Param("userId") int userId);

}
