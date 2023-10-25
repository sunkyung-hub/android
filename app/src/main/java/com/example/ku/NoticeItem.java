package com.example.ku;

import java.util.Date;

public class NoticeItem {

    private String title;
    private String date; // Timestamp 대신 String으로 변경
    private String homepageUrl; // 홈페이지 URL 필드 추가
    private int order;
    private String documentId; // Firestore 문서 ID


    // 기본 생성자 (인자 없음)
    public NoticeItem() {
        // 기본 값 또는 초기화 코드를 여기에 추가
        // 멤버 변수 초기화 또는 기본값 설정
//        this.title = "2차 수강바구니";
//        this.date = ""; // 빈 문자열로 초기화
    }

    // 인자를 받는 생성자
    public NoticeItem(String title, String date, String homepageUrl, int order) { // Timestamp 대신 String으로 변경
        this.title = title;
        this.date = date;
        this.homepageUrl = homepageUrl;
        this.order = order;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() { // Timestamp 대신 String으로 변경
        return date;
    }

    public void setDate(String date) { // Timestamp 대신 String으로 변경
        this.date = date;
    }

    // 홈페이지 URL 필드에 대한 Getter 메서드
    public String getHomepageUrl() {
        return homepageUrl;
    }

    // 홈페이지 URL 필드에 대한 Setter 메서드
    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}
