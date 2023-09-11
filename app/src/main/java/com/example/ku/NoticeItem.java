package com.example.ku;

public class NoticeItem {

    private String tv_title;
    private String tv_date;

    public NoticeItem(String tv_title, String tv_date){
        this.tv_title = tv_title;
        this.tv_date = tv_date;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String title){
        this.tv_title = title;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String date){
        this.tv_date = date;
    }

}
