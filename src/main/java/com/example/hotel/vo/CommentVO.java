package com.example.hotel.vo;

public class CommentVO {

    private int id;//主键id
    private Integer hotelId;
    private String hotelName;
    private Integer userId;
    private Integer orderId;
    private String createDate;
    private double descriptionScore;
    private double serviceScore;
    private double environmentScore;
    private double finalScore;
    private String userEvaluation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public double getDescriptionScore() {
        return descriptionScore;
    }

    public void setDescriptionScore(double descriptionScore) {
        this.descriptionScore = descriptionScore;
    }

    public double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public double getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(double environmentScore) {
        this.environmentScore = environmentScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public String getUserEvaluation() {
        return userEvaluation;
    }

    public void setUserEvaluation(String userEvaluation) {
        this.userEvaluation = userEvaluation;
    }






}

