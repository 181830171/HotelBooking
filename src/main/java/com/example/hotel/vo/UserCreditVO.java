package com.example.hotel.vo;

public class UserCreditVO {
    private Integer id;
    private String createDate;
    private Integer orderId;
    private Integer userId;
    private String action;
    private double creditChange;
    private double res;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getUseId() { return userId; }

    public void setUserId(Integer userId){this.userId=userId;}

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getOrderId() { return orderId; }

    public void setOrderId(Integer orderId){this.orderId=orderId;}

    public String getType(){return action;}

    public void setType(String type){this.action=type;}

    public double getChange(){return creditChange;}

    public void setChange(double change){this.creditChange=change;}

    public double getRes(){return res;}

    public void setRes(double res){this.res=res;}
}
