package com.moi.cquptcard.model.bean;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class Card {
    private String name;
    private String cardId;
    private String money;
    private String time;

    public Card(String name, String cardId) {
        this.name = name;
        this.cardId = cardId;
    }

    public String getMoney() {
        return money;
    }

    public Card setMoney(String money) {
        this.money = money;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Card setTime(String time) {
        this.time = time;
        return this;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
