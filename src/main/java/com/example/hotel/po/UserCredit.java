package com.example.hotel.po;

public class UserCredit {

    private Integer id;
    private String createDate;//时间
    private Integer orderId;
    private Integer userId;
    private String actionType;//动作（订单执行、订单异常、订单撤销）
    private double creditChange;//信用度变化
    private double res;//信用度结果

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId){this.userId=userId;}

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getOrderId() { return orderId; }

    public void setOrderId(Integer orderId){this.orderId=orderId;}

    public String getActionType(){return actionType;}

    public void setActionType(String type){this.actionType=type;}

    public double getChange(){return creditChange;}

    public void setChange(double change){this.creditChange=change;}

    public double getRes(){return res;}

    public void setRes(double res){this.res=res;}
}
