package com.example.hotel.vo;

public class VipVO {

    private Integer id;
    private Integer userId;
    private String vipName;
    private String vipType;
    private String birthday;
    private String enterpriseName;
    private int vipLevel;
    private double vipDiscount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVIPType() {
        return vipType;
    }

    public void setVIPType(String type) {
        this.vipType = type;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getVipName() { return vipName; }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public int getVipLevel() { return vipLevel; }

    public void setVipLevel(int vipLevel) { this.vipLevel = vipLevel; }

    public double getVipDiscount() { return vipDiscount; }

    public void setVipDiscount(double vipDiscount) { this.vipDiscount = vipDiscount; }
}
