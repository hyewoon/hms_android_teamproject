package com.example.androidhms.customer.common;

public class CommonMethod {

    //숫자만 추출
    public static String extractDate(String date) {
        String match = "[^0-9]";
        date = date.replaceAll(match, "");
        return date;
    }
    
}
