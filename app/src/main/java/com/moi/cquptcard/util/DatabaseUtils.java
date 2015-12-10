package com.moi.cquptcard.util;

import com.activeandroid.query.Select;
import com.moi.cquptcard.model.bean.Card;
import com.moi.cquptcard.model.database.CardTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by moi on 15/12/9.
 */
public class DatabaseUtils {

    public static void saveCard(Card card) {
        CardTable cardTable = new CardTable();
        cardTable.name = card.getName();
        cardTable.cardId = card.getCardId();
        cardTable.save();
    }

    public static void deleteCard(Card card) {
        new Select()
                .from(CardTable.class)
                .where("name=? and card_id=?", card.getName(), card.getCardId())
                .executeSingle()
                .delete();
    }

    public static List<Card> loadCards() {
        if (new Select().from(CardTable.class).execute().size() > 0) {
            List<CardTable> cardTables = new Select().from(CardTable.class).execute();
            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < cardTables.size(); i++) {
                cards.add(new Card(cardTables.get(i).name, cardTables.get(i).cardId));
            }
            return cards;
        }
        return new ArrayList<>();
    }
}
