package com.moi.cquptcard.model.bean;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class Card {
    private String name;
    private String cardID;

    public Card(String name, String cardID) {
        this.name = name;
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
