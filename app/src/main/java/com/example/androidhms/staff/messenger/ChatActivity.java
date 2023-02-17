package com.example.androidhms.staff.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityChatBinding;
import com.example.androidhms.databinding.NavChatHeaderBinding;
import com.example.androidhms.staff.StaffBaseActivity;
import com.example.androidhms.staff.lookup.LookupActivity;
import com.example.androidhms.staff.messenger.adapter.ChatAdapter;
import com.example.androidhms.staff.messenger.dialog.AddMemberDialog;
import com.example.androidhms.staff.outpatient.PrescriptionActivity;
import com.example.androidhms.staff.vo.ChatVO;
import com.example.androidhms.staff.vo.PrescriptionVO;
import com.example.androidhms.staff.vo.StaffChatDTO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;
import com.example.androidhms.util.dialog.AlertDialog;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatActivity extends StaffBaseActivity {

    private ActivityChatBinding bind;
    private NavChatHeaderBinding nBind;
    private HmsFirebase fb;
    private String key;
    private String title;
    private StaffChatDTO staff;
    private ArrayList<ChatVO> chatList;
    private ArrayList<StaffChatDTO> staffList;
    private ArrayList<StaffChatDTO> allStaffList;
    private boolean isGroup;
    private boolean isOutChatRoom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fb = new HmsFirebase(this, firebaseHandler());
        staff = Util.getStaffChatDTO(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        key = intent.getStringExtra("key");
        isGroup = !title.contains("#");

        bind.toolbar.imgvMessenger.setImageResource(R.drawable.icon_menu);
        bind.toolbar.imgvMessenger.setOnClickListener(v -> bind.view.openDrawer(GravityCompat.END));

        if (!isGroup) {
            String titleView = title.replace("#", "");
            titleView = titleView.replaceAll(staff.getName(), "");
            bind.tvChatroom.setText(titleView);
        } else bind.tvChatroom.setText(title);

        bind.btSend.setOnClickListener(onSendClick());

        bind.imgvShare.setOnClickListener(onShareClick());
        // 채팅 입력 칸을 눌렀을때 맨 아래가 보이게
//        bind.etContent.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) new Handler().postDelayed(
//                    () -> bind.rvChat.scrollToPosition(chatList.size() - 1), 200);
//        });
//        bind.etContent.setOnClickListener(v ->
//                new Handler().postDelayed(() ->
//                        bind.rvChat.scrollToPosition(chatList.size() - 1), 200));
        fb.getChat(key);
        fb.getChatMember(key);
        fb.getNoticeChat(key);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fb.setOnChat(key, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isOutChatRoom) fb.setOnChat(key, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isOutChatRoom) {
            fb.removeChatListener(key);
            fb.setOnChat(key, false);
        }
    }

    @Override
    protected View getLayoutResource() {
        bind = ActivityChatBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    private void setNavigationView() {
        nBind = NavChatHeaderBinding.bind(bind.navMember.getHeaderView(0));
        nBind.tvName.setText(staff.getName());
        nBind.tvDepartment.setText(getDepartment(staff));

        Menu menu = bind.navMember.getMenu();
        menu.clear();
        Menu subMenu = menu.addSubMenu("참여자 (" + staffList.size() + ")");
        for (StaffChatDTO dto : staffList) {
            subMenu.add(dto.getName() + " / " + getDepartment(dto));
        }
        if (!isGroup) {
            nBind.imgvAdd.setVisibility(View.GONE);
            nBind.imgvOut.setVisibility(View.GONE);
        } else {
            nBind.imgvAdd.setOnClickListener(v -> {
                if (allStaffList == null) {
                    new RetrofitMethod().sendGet("getStaff.ap", (isResult, data) -> {
                        if (isResult) {
                            allStaffList = new Gson().fromJson(data, new TypeToken<ArrayList<StaffChatDTO>>() {
                            }.getType());
                            onShowAddMemberDialog();
                        }
                    });
                } else onShowAddMemberDialog();
            });
            nBind.imgvOut.setOnClickListener(v -> onShowOutDialog());
        }
    }

    private String getDepartment(StaffChatDTO dto) {
        String department = dto.getDepartment_name();
        if (dto.getStaff_level() == 1) department += " 의사";
        else department += " 간호사";
        return department;
    }

    private Handler firebaseHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == HmsFirebase.GET_CHAT_SUCCESS) {
                    chatList = (ArrayList<ChatVO>) msg.obj;
                    Util.setRecyclerView(ChatActivity.this, bind.rvChat,
                            new ChatAdapter(ChatActivity.this, chatList, String.valueOf(staff.getStaff_id())), true);
                    bind.rvChat.scrollToPosition(chatList.size() - 1);
                    // 멤버 변동시 추가 / 삭제
                    if (!chatList.isEmpty() && (chatList.get(chatList.size() - 1).getName().equals("SYSTEM_ADD") ||
                            chatList.get(chatList.size() - 1).getName().equals("SYSTEM_OUT"))) {
                        fb.getChatMember(key);
                    }
                    // 공지사항 변경시 새 공지사항을 불러옴
                    if (!chatList.isEmpty() && (chatList.get(chatList.size() - 1).getName().equals("SYSTEM_NOTICE"))) {
                        fb.getNoticeChat(key);
                    }
                } else if (msg.what == HmsFirebase.GET_CHAT_MEMBER_SUCCESS) {
                    staffList = (ArrayList<StaffChatDTO>) msg.obj;
                    setNavigationView();
                } else if (msg.what == HmsFirebase.OUT_GROUP_CHATROOM_SUCCESS) {
                    Toast.makeText(ChatActivity.this, "채팅방에서 나갔습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (msg.what == HmsFirebase.GET_CHATROOM_NOTICE_SUCCESS) {
                    if (msg.obj != null) {
                        ChatVO vo = (ChatVO) msg.obj;
                        bind.tvNotice.setText(vo.getContent());
                        bind.llNotice.setVisibility(View.VISIBLE);
                    }
                    if (chatList != null) {
                        bind.rvChat.scrollToPosition(chatList.size() - 1);
                    }
                    bind.rvChat.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private View.OnClickListener onSendClick() {
        return v -> {
            if (bind.etContent.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "채팅을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            } else if (bind.etContent.getText().toString().contains("##")){
                Toast.makeText(this, "'##'은 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
            } else if (chatList.isEmpty() || !Util.getDate(Timestamp.valueOf(chatList.get(chatList.size() - 1).getTime()))
                    .equals(Util.getDate(new Timestamp(System.currentTimeMillis())))) {
                fb.sendDateBeforeSendChat(key, title,
                        new ChatVO(String.valueOf(staff.getStaff_id()), staff.getName(), bind.etContent.getText().toString())
                        , new Timestamp(System.currentTimeMillis()));
                bind.etContent.setText("");
            } else {
                fb.sendChat(key, title, new ChatVO(String.valueOf(staff.getStaff_id()), staff.getName(), bind.etContent.getText().toString()));
                bind.etContent.setText("");
            }
        };
    }

    private void onShowAddMemberDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allStaffList = (ArrayList<StaffChatDTO>) allStaffList.stream().filter(d -> {
                for (StaffChatDTO dto : staffList) if (dto.equals(d)) return false;
                return true;
            }).collect(Collectors.toList());
        }
        new AddMemberDialog(ChatActivity.this, allStaffList, getLayoutInflater(), new AddMemberDialog.OnDialogBtnClickListener() {
            @Override
            public void onCreateClick(AddMemberDialog dialog, List<StaffChatDTO> memberStaffList) {
                fb.addMemberGroupChat(key, memberStaffList);
                dialog.dismiss();
                bind.view.closeDrawer(GravityCompat.END);
            }
        }).show();
    }

    private void onShowOutDialog() {
        nBind.imgvOut.setOnClickListener(v -> {
            new AlertDialog(ChatActivity.this, getLayoutInflater(), "채팅방 나가기", "정말 채팅방을 나가시겠습니까?",
                    new AlertDialog.OnAlertDialogClickListener() {
                        @Override
                        public void setOnClickYes(AlertDialog dialog) {
                            isOutChatRoom = true;
                            fb.outGroupChatRoom(key, staff.getName());
                            dialog.dismiss();
                        }
                        @Override
                        public void setOnClickNo(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    }).setYesText("나가기").show();
        });
    }

    public void setNoticeChat(int position) {
        new AlertDialog(ChatActivity.this, getLayoutInflater(),
                "공지사항 등록",
                "'" + chatList.get(position).getContent() + "'\n를 채팅방의 공지사항으로 등록하시겠습니까?",
                new AlertDialog.OnAlertDialogClickListener() {
                    @Override
                    public void setOnClickYes(AlertDialog dialog) {
                        fb.setNoticeChat(key, chatList.get(position));
                        dialog.dismiss();
                    }
                    @Override
                    public void setOnClickNo(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                }).setYesText("등록").show();
    }

    public void getSharedChat(String content) {
        String[] shared = content.split("##");
        if (shared[1].equals("patient")) {
            Intent intent = new Intent(this, LookupActivity.class);
            intent.putExtra("patient_id", Integer.parseInt(shared[2]));
            startActivity(intent);
        } else if (shared[1].equals("prescription")) {
            Intent intent = new Intent(this, PrescriptionActivity.class);
            intent.putExtra("medical_record_id", Integer.parseInt(shared[2]));
            startActivity(intent);
        }
    }

    private View.OnClickListener onShareClick() {
        return v -> {
            if (Util.sharedContent == null) {
                Toast.makeText(this, "저장된 공유 내용이 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                if (chatList.isEmpty() || !Util.getDate(Timestamp.valueOf(chatList.get(chatList.size() - 1).getTime()))
                        .equals(Util.getDate(new Timestamp(System.currentTimeMillis())))) {
                    fb.sendDateBeforeSendChat(key, title,
                            new ChatVO(String.valueOf(staff.getStaff_id()), staff.getName(), Util.sharedContent)
                            , new Timestamp(System.currentTimeMillis()));
                } else fb.sendChat(key, title, new ChatVO(String.valueOf(staff.getStaff_id()), staff.getName(), Util.sharedContent));
            }
        };
    }

}