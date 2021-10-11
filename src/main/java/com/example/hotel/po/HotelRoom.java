package com.example.hotel.po;

import com.example.hotel.enums.RoomType;

public class HotelRoom {
    private Integer id;
    private String roomType;
    private Integer hotelId;
    private double price;
    private int peopleNum;
    private int breakfast;
    private String description;
    private String picture;

    /**
     * 当前剩余可预定房间数
     */
    private int curNum;
    /**
     * 某类型房间总数
     */
    private int total;

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

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCurNum() {
        return curNum;
    }

    public void setCurNum(int curNum) {
        this.curNum = curNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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
