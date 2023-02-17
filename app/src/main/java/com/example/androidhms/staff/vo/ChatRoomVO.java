package com.example.androidhms.staff.vo;

public class ChatRoomVO {

    String key, roomTitle, lastChat, lastChatTime, count;

    public ChatRoomVO(String key, String roomTitle, String lastChat, String lastChatTime, String count) {
        this.roomTitle = roomTitle;
        this.lastChat = lastChat;
        this.lastChatTime = lastChatTime;
        this.count = count;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public String getLastChat() {
        return lastChat;
    }

    public String getLastChatTime() {
        return lastChatTime;
    }

    public String getCount() {
        return count;
    }
}
