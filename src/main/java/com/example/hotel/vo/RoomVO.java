package com.example.hotel.vo;

import com.example.hotel.enums.RoomType;

public class RoomVO {
    private Integer id;
    private String roomType;
    private Double price;
    private Integer curNum;
    private Integer total;
    private int peopleNum;
    private int breakfast;
    private String description;
    private String picture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }


    public int getBreakfast() { return breakfast; }

    public void setBreakfast(int breakfast) { this.breakfast = breakfast; }

    public int getPeopleNum() { return peopleNum; }

    public void setPeopleNum(int peopleNum) { this.peopleNum = peopleNum; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
