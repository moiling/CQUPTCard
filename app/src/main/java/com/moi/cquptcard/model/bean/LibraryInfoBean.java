package com.moi.cquptcard.model.bean;

import com.moi.cquptcard.model.ParamName;

/**
 *
 * Created by moi on 11/23/2015.
 */
public class LibraryInfoBean {

    @ParamName("tsmc")
    private String bookName;
    @ParamName("jsrq")
    private String lendTime;
    @ParamName("yhrq")
    private String deadLine;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getLendTime() {
        return lendTime;
    }

    public void setLendTime(String lendTime) {
        this.lendTime = lendTime;
    }

}
