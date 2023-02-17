package com.example.androidhms.staff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.databinding.SnackbarChatBinding;
import com.example.androidhms.databinding.ToolbarStaffBinding;
import com.example.androidhms.staff.messenger.ChatActivity;
import com.example.androidhms.staff.messenger.MessengerActivity;
import com.example.androidhms.staff.vo.ChatVO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;

public abstract class StaffBaseActivity extends AppCompatActivity {

    private HmsFirebase fb;
    private ToolbarStaffBinding bind;
    private ChatVO chatVO;
    // 채팅 알림 중복 방지
    protected static Timestamp getNotificationTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        bind = ToolbarStaffBinding.bind(findViewById(R.id.toolbar));
        fb = new HmsFirebase(this, getChatNotificationHandler());

        bind.imgvBefore.setOnClickListener(v -> finish());
        if (!(getActivity() instanceof ChatActivity) && !(getActivity() instanceof MessengerActivity)) {
            bind.imgvMessenger.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MessengerActivity.class);
                intent.putExtra("toolbar", true);
                startActivity(intent);
            });
        }
        if (getNotificationTime == null) setGetNotificationTime();
    }

    private Handler getChatNotificationHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == HmsFirebase.GET_NOT_CHECKED_CHAT_COUNT_SUCCESS) {
                    int count = (int) msg.obj;
                    if (count == 0) bind.tvNotCheckedChat.setVisibility(View.INVISIBLE);
                    else {
                        bind.tvNotCheckedChat.setVisibility(View.VISIBLE);
                        bind.tvNotCheckedChat.setText(String.valueOf(count));
                    }
                } else if (msg.what == HmsFirebase.GET_NOTIFICATION_SUCCESS) {
                    chatVO = (ChatVO) msg.obj;
                    fb.getNotificationChatroom();
                } else if (msg.what == HmsFirebase.GET_NOTIFICATION_CHATROOM_SUCCESS) {
                    chatSnackbar((String) msg.obj, chatVO);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        fb.getNotCheckedChatCount();
        fb.getNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fb.removeNotCheckedChatCountListener();
        fb.removeNotificationListener();
    }

    protected void addFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    protected void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    protected void hideFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    protected void chatSnackbar(String keyAndTitle, ChatVO vo) {
        if (!(getActivity() instanceof ChatActivity)
                && getNotificationTime != null
                && Timestamp.valueOf(vo.getTime()).compareTo(getNotificationTime) > 0) {
            // 스낵바 커스텀 설정
            SnackbarChatBinding sbBind = SnackbarChatBinding.inflate(getActivity().getLayoutInflater());
            Snackbar snackbar = Snackbar.make(bind.toolbar, "", BaseTransientBottomBar.LENGTH_SHORT);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            layout.addView(sbBind.getRoot());
            // 스낵바 위치 설정
            View view = snackbar.getView();
            view.setBackgroundColor(Color.TRANSPARENT);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            params.topMargin = Util.getPxFromDp(getActivity(), 50);
            params.leftMargin = Util.getPxFromDp(getActivity(), 20);
            params.rightMargin = Util.getPxFromDp(getActivity(), 20);
            view.setLayoutParams(params);
            // 스낵바 바인딩
            String[] strArr = keyAndTitle.split("##");
            String titleView;
            if (strArr[1].contains("#")) titleView = vo.getName();
            else titleView = strArr[1] + " / " + vo.getName();
            sbBind.tvName.setText(titleView);
            if (vo.getContent().contains("##")) {
                sbBind.tvContent.setText(vo.getName() + "님이 링크를 공유했습니다.");
            } else sbBind.tvContent.setText(vo.getContent());
            sbBind.imgvExit.setOnClickListener(v -> snackbar.dismiss());
            sbBind.view.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("title", strArr[1]);
                intent.putExtra("key", strArr[0]);
                startActivity(intent);
            });
            snackbar.show();
            setGetNotificationTime();
            // 진동
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
       //     vibrator.vibrate(800); // 0.8초간 진동
        }
    }

    private static void setGetNotificationTime() {
        getNotificationTime = new Timestamp(System.currentTimeMillis());
    }

    protected abstract View getLayoutResource();

    protected abstract Activity getActivity();

}
