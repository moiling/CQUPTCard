package com.moi.cquptcard.model.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 *
 * Created by moi on 15/12/9.
 */
@Table(name = "CARD")
public class CardTable extends Model {
    @Column(name = "name")
    public String name;
    @Column(name = "card_id")
    public String cardId;
}
