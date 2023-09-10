package com.example.ku;

public class NoticeData {

    private String tv_title;
    private String tv_Date;

    public NoticeData(String tv_num, String tv_title, String tv_Date){
        this.tv_title = tv_title;
        this.tv_Date = tv_Date;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title){
        this.tv_title = tv_title;
    }

    public String getTv_Date() {
        return tv_Date;
    }

    public void setTv_Date(String tv_Date){
        this.tv_Date = tv_Date;
    }

}
