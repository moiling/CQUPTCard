package com.moi.cquptcard.model.bean;

import com.moi.cquptcard.model.ParamName;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class ConsumptionBean {
    @ParamName("jyls")
    private String number;
    @ParamName("xm")
    private String name;
    @ParamName("sj")
    private String time;
    @ParamName("lx")
    private String type;
    @ParamName("je")
    private String money;
    @ParamName("ye")
    private String balance;
    @ParamName("sh")
    private String place;
    @ParamName("sb")
    private String placeDetail;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(String placeDetail) {
        this.placeDetail = placeDetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
