package com.example.hotel.enums;

public enum BizRegion {
    RegionA("A区",0.9),
    RegionB("B区",0.91),
    RegionC("C区",0.92),
    RegionD("D区",0.93),
    RegionE("E区",0.94);


    private String value;
    private double vipRegionDiscount;

    BizRegion(String value, double vipRegionDiscount) {
        this.value = value;
        this.vipRegionDiscount = vipRegionDiscount;
    }

    public String getValue() {
        return value;
    }

    public double getVipRegionDiscount() {
        return vipRegionDiscount;
    }



}
