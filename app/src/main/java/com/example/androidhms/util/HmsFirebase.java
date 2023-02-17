package com.example.androidhms.util;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.androidhms.staff.vo.ChatRoomVO;
import com.example.androidhms.staff.vo.ChatVO;
import com.example.androidhms.staff.vo.StaffChatDTO;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HmsFirebase {

    public static final int GET_CHATROOM_SUCCESS = 1;
    public static final int GET_CHAT_SUCCESS = 2;
    public static final int GET_CHATROOM_LIST_SUCCESS = 3;
    public static final int GET_CHAT_MEMBER_SUCCESS = 4;
    public static final int GET_NOT_CHECKED_CHAT_COUNT_SUCCESS = 5;
    public static final int GET_NOTIFICATION_SUCCESS = 6;
    public static final int GET_NOTIFICATION_CHATROOM_SUCCESS = 7;
    public static final int CREATE_GROUP_CHATROOM_SUCCESS = 8;
    public static final int OUT_GROUP_CHATROOM_SUCCESS = 9;
    public static final int GET_CHATROOM_NOTICE_SUCCESS = 10;

    private static final String RB_URL = "https://hmsmessenger-3a156-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private static final FirebaseDatabase fbDb = FirebaseDatabase.getInstance(RB_URL);
    private static final DatabaseReference dbRef = fbDb.getReference();

    private final DatabaseReference chatRoom;
    private final DatabaseReference member;
    private final String myId;

    private Handler handler;
    private ValueEventListener notCheckedChatCountListener, getChatListener, getChatRoomListener, getNotificationListener;

    public HmsFirebase(Context context) {
        FirebaseApp.initializeApp(context);
        chatRoom = dbRef.child("chatRoom");
        member = dbRef.child("member");
        if (Util.staff == null) Util.getStaff(context);
        myId = String.valueOf(Util.staff.getStaff_id());
    }

    public HmsFirebase(Context context, Handler handler) {
        this(context);
        this.handler = handler;
    }

    /**
     * 자신의 토큰값 저장
     */
    public void sendToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            member.child(myId).child("token").setValue(task.getResult());
        });
    }

    public void sendToken(String token) {
        member.child(myId).child("token").setValue(token);
    }

    public void deleteToken() {
        member.child(myId).child("token").setValue("null");
    }

    /**
     * 아직 읽지않은 채팅 수 불러오기
     */
    public void getNotCheckedChatCount() {
        notCheckedChatCountListener = getNotCheckedChatCountListener();
        member.child(myId).child("count")
                .addValueEventListener(getNotCheckedChatCountListener());
    }

    private ValueEventListener getNotCheckedChatCountListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    count += child.getValue(Integer.class);
                }
                handler.sendMessage(handler.obtainMessage(
                        GET_NOT_CHECKED_CHAT_COUNT_SUCCESS, count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void removeNotCheckedChatCountListener() {
        member.child(myId).child("count").removeEventListener(notCheckedChatCountListener);
    }

    /**
     * MessengerStaffFragment 에서 1:1 채팅방 개설 (이미 존재할 경우 입장)
     */
    public void makeChatRoom(List<StaffChatDTO> staffList) {
        // 1:1 채팅방의 키값 (staff_id)--(staff_id)
        String key;
        if (staffList.get(0).getStaff_id() < staffList.get(1).getStaff_id()) {
            key = staffList.get(0).getStaff_id() + "--" + staffList.get(1).getStaff_id();
        } else key = staffList.get(1).getStaff_id() + "--" + staffList.get(0).getStaff_id();
        chatRoom.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 채팅방이 존재하지 않을경우 채팅방을 만들고 함수 재호출
                if (!snapshot.exists()) {
                    HashMap<String, Object> map = new HashMap<>();
                    HashMap<String, StaffChatDTO> memberMap = new HashMap<>();
                    for (StaffChatDTO dto : staffList) {
                        memberMap.put(String.valueOf(dto.getStaff_id()), dto);
                    }
                    map.put("member", memberMap);
                    // 1:1 채팅방의 제목 #(staff_name0)(staff_name1)
                    map.put("chatRoomTitle", "#" + staffList.get(0).getName() + staffList.get(1).getName());
                    chatRoom.child(key).setValue(map)
                            .addOnSuccessListener(unused -> handler.sendMessage(handler.obtainMessage(GET_CHATROOM_SUCCESS, null)));
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("key", key);
                    map.put("title", snapshot.child("chatRoomTitle").getValue(String.class));
                    handler.sendMessage(handler.obtainMessage(GET_CHATROOM_SUCCESS, map));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * MessengerStaffFragment 에서 그룹 채팅방 개설
     */
    public void makeGroupChatRoom(String title, List<StaffChatDTO> staffList) {
        String key = UUID.randomUUID().toString();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, StaffChatDTO> memberMap = new HashMap<>();
        for (StaffChatDTO dto : staffList) {
            memberMap.put(String.valueOf(dto.getStaff_id()), dto);
        }
        map.put("chatRoomTitle", title);
        map.put("member", memberMap);
        chatRoom.child(key).setValue(map).addOnSuccessListener(unused -> {
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("key", key);
            map1.put("title", title);
            handler.sendMessage(handler.obtainMessage(CREATE_GROUP_CHATROOM_SUCCESS, map1));
        });
    }

    /**
     * MessengerFragment 채팅방 목록 불러오기
     */
    public void getChatRoom() {
        getChatRoomListener = getChatRoomListener();
        member.child(myId).child("lastChat").addValueEventListener(getChatRoomListener);
    }

    private ValueEventListener getChatRoomListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                member.child(myId).child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ChatRoomVO> chatRoomList = new ArrayList<>();
                        if (!snapshot.exists()) {
                            handler.sendMessage(handler.obtainMessage(GET_CHATROOM_LIST_SUCCESS, null));
                        } else {
                            final int[] childrenCount = {1};
                            for (DataSnapshot count : snapshot.getChildren()) {
                                chatRoom.child(count.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot chatRoom) {
                                        // 채팅방이 개설만 되고 채팅이 하나도 없을 경우
                                        if (!chatRoom.child("lastChat").exists()) {
                                            chatRoomList.add(new ChatRoomVO(
                                                    chatRoom.getKey(),
                                                    chatRoom.child("chatRoomTitle").getValue(String.class),
                                                    "", "2000-01-01 00:00:00.000",
                                                    String.valueOf(count.getValue(Integer.class))
                                            ));
                                        } else {
                                            chatRoomList.add(new ChatRoomVO(
                                                    chatRoom.getKey(),
                                                    chatRoom.child("chatRoomTitle").getValue(String.class),
                                                    chatRoom.child("lastChat").child("content").getValue(String.class),
                                                    chatRoom.child("lastChat").child("time").getValue(String.class),
                                                    String.valueOf(count.getValue(Integer.class))
                                            ));
                                        }
                                        if (childrenCount[0]++ == snapshot.getChildrenCount()) {
                                            handler.sendMessage(handler.obtainMessage(GET_CHATROOM_LIST_SUCCESS, chatRoomList));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void removeGetChatRoomListener() {
        member.child(myId).child("lastChat").removeEventListener(getChatRoomListener);
    }

    /**
     * 채팅방 입장/퇴장시 접속 상태를 변경
     */
    public void setOnChat(String key, boolean onChat) {
        chatRoom.child(key).child("member")
                .child(myId).child("onChat").setValue(onChat);
    }

    /**
     * 채팅방 멤버 불러오기
     */
    public void getChatMember(String key) {
        chatRoom.child(key).child("member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<StaffChatDTO> staffList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    staffList.add(data.getValue(StaffChatDTO.class));
                }
                handler.sendMessage(handler.obtainMessage(GET_CHAT_MEMBER_SUCCESS, staffList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * 채팅 내역을 불러옴
     */
    public void getChat(String key) {
        getChatListener = GetChatListener(key);
        chatRoom.child(key).child("chat").addValueEventListener(getChatListener);
    }

    private ValueEventListener GetChatListener(String key) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ChatVO> chatList = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    chatList.add(child.getValue(ChatVO.class));
                }
                member.child(myId).child("count").child(key).setValue(0);
                chatRoom.child(key).child("member")
                        .child(myId)
                        .child("lastChatCheckTime").setValue(Util.getChatTimeStamp());
                handler.sendMessage(handler.obtainMessage(GET_CHAT_SUCCESS, chatList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
    }

    public void removeChatListener(String key) {
        chatRoom.child(key).child("chat").removeEventListener(getChatListener);
        chatRoom.child(key).child("member")
                .child(myId)
                .child("lastChatCheckTime").setValue(Util.getChatTimeStamp());
    }

    /**
     * 하루가 지나갈 경우 시스템 메시지에 날짜 출력
     */
    public void sendDateBeforeSendChat(String key, String title, ChatVO vo, Timestamp timestamp) {
        chatRoom.child(key).child("chat").push().setValue(
                new ChatVO("0", "SYSTEM", Util.dateFormat(timestamp, "yyyy년 M월 d일")))
                .addOnSuccessListener(unused -> sendChat(key, title, vo));
    }

    /**
     * 채팅 전송
     */
    public void sendChat(String key, String title, ChatVO vo) {
        chatRoom.child(key).child("chat").push().setValue(vo);
        chatRoom.child(key).child("lastChat").setValue(vo);
        chatRoom.child(key).child("member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (!child.getKey().equals(myId) && !child.child("onChat").getValue(Boolean.class)) {
                        member.child(String.valueOf(child.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("count").child(key).exists()) {
                                    member.child(String.valueOf(child.getKey()))
                                            .child("count").child(key)
                                            .setValue(snapshot.child("count").child(key).getValue(Integer.class) + 1);
                                } else
                                    member.child(String.valueOf(child.getKey()))
                                            .child("count").child(key).setValue(1);
                                if (snapshot.child("token").exists() && !snapshot.child("token").getValue(String.class).equals("null")) {
                                    SendPush.sendPushNotification(snapshot.child("token").getValue(String.class), vo, key, title);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        member.child(String.valueOf(child.getKey())).child("lastChatRoom")
                                .setValue(key + "##" + title).addOnSuccessListener(unused -> {
                                    member.child(String.valueOf(child.getKey())).child("lastChat").setValue(vo);
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * 채팅 알림
     */
    public void getNotification() {
        getNotificationListener = getNotificationListener();
        member.child(myId).child("lastChat").addValueEventListener(getNotificationListener);
    }

    private ValueEventListener getNotificationListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    handler.sendMessage(handler.obtainMessage(GET_NOTIFICATION_SUCCESS, snapshot.getValue(ChatVO.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void getNotificationChatroom() {
        member.child(myId).child("lastChatRoom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    handler.sendMessage(handler.obtainMessage(GET_NOTIFICATION_CHATROOM_SUCCESS, snapshot.getValue(String.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removeNotificationListener() {
        member.child(myId).child("lastChat").removeEventListener(getNotificationListener);
    }


    /**
     * 그룹 채팅 멤버 추가
     */
    public void addMemberGroupChat(String key, List<StaffChatDTO> addStaffList) {
        chatRoom.child(key).child("member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, StaffChatDTO> staffMap = new HashMap<>();
                ArrayList<StaffChatDTO> staffList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    StaffChatDTO dto = data.getValue(StaffChatDTO.class);
                    staffMap.put(String.valueOf(dto.getStaff_id()), dto);
                    staffList.add(dto);
                }
                for (StaffChatDTO dto : addStaffList) {
                    staffMap.put(String.valueOf(dto.getStaff_id()), dto);
                    staffList.add(dto);
                }
                chatRoom.child(key).child("member").setValue(staffMap)
                        .addOnSuccessListener(unused -> {
                            StringBuilder addMember = new StringBuilder();
                            for (int i = 0; i < addStaffList.size(); i++) {
                                addMember.append(addStaffList.get(i).getName()).append("님");
                                if (i != addStaffList.size() - 1) {
                                    addMember.append(", ");
                                }
                            }
                            addMember.append("이 입장하셨습니다.");
                            chatRoom.child(key).child("chat").push().setValue(
                                            new ChatVO("0", "SYSTEM_ADD", addMember.toString()));
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * 그룹 채팅방 나가기
     */
    public void outGroupChatRoom(String key, String myName) {
        chatRoom.child(key).child("chat").removeEventListener(getChatListener);
        member.child(myId).child("count").child(key).removeValue().addOnSuccessListener(unused -> {
            handler.sendMessage(handler.obtainMessage(OUT_GROUP_CHATROOM_SUCCESS, 0));
            chatRoom.child(key).child("member").child(myId).removeValue();
            chatRoom.child(key).child("chat").push().setValue(
                    new ChatVO("0", "SYSTEM_OUT", myName + "님이 나갔습니다."));
        });
    }

    /**
     * 채팅방 공지사항 불러오기
     */
    public void getNoticeChat(String key) {
        chatRoom.child(key).child("noticeChat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                handler.sendMessage(handler.obtainMessage(GET_CHATROOM_NOTICE_SUCCESS, snapshot.getValue(ChatVO.class)));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * 채팅방 공지사항 지정
     */
    public void setNoticeChat(String key, ChatVO vo) {
        chatRoom.child(key).child("noticeChat").setValue(vo)
                .addOnSuccessListener(unused -> chatRoom.child(key).child("chat").push().setValue(
                new ChatVO("0", "SYSTEM_NOTICE", "채팅방의 공지사항이 설정되었습니다.")));
    }

}
