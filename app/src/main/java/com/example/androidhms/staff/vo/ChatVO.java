package com.example.androidhms.staff.vo;

import com.example.androidhms.util.Util;

public class ChatVO {

    private String id, name, content, time;

    public ChatVO() {

    }

    public ChatVO(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
        time = Util.getChatTimeStamp();
    }

    public ChatVO(String id, String name, String content, String time) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
