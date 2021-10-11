package com.example.hotel.controller.user;


import com.example.hotel.bl.user.CommentService;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/user")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/addComment")
    public ResponseVO addComment(@RequestBody CommentVO commentVO) {
        return commentService.addComment(commentVO);
    }

    @GetMapping("/{hotelId}/getCommentsByHotelId")
    public ResponseVO getCommentsByHotelId(@PathVariable int hotelId) {
        return ResponseVO.buildSuccess(commentService.getCommentsByHotelId(hotelId));
    }

    @GetMapping("/{userId}/getCommentsByUserId")
    public ResponseVO getCommentByUserId(@PathVariable int userId) {
        return ResponseVO.buildSuccess(commentService.getCommentsByUserId(userId));
    }

    @GetMapping("/{hotelId}/countRate")
    public ResponseVO countRate(@PathVariable int hotelId) {
        return ResponseVO.buildSuccess(commentService.countRate(hotelId));
    }
}
