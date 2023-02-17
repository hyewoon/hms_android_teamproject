package com.example.androidhms.staff.messenger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidhms.databinding.FragmentMessengerBinding;
import com.example.androidhms.staff.messenger.adapter.ChatRoomAdapter;
import com.example.androidhms.staff.vo.ChatRoomVO;
import com.example.androidhms.staff.vo.StaffChatDTO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MessengerFragment extends Fragment {

    private FragmentMessengerBinding bind;
    private HmsFirebase fb;
    private StaffChatDTO staff;
    private ArrayList<ChatRoomVO> chatRoomList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentMessengerBinding.inflate(inflater, container, false);
        fb = new HmsFirebase(this.getContext(), firebaseHandler());
        staff = Util.getStaffChatDTO(getContext());
        bind.tvName.setText(staff.getName());
        bind.clNotfound.tvNotfound.setText("채팅방이 없습니다.");
        return bind.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fb.getChatRoom();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fb.removeGetChatRoomListener();
        bind = null;
    }

    private Handler firebaseHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == HmsFirebase.GET_CHATROOM_LIST_SUCCESS && bind != null) {
                    bind.clNotfound.view.setVisibility(View.GONE);
                    if (msg.obj != null) {
                        chatRoomList = (ArrayList<ChatRoomVO>) msg.obj;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            chatRoomList = (ArrayList<ChatRoomVO>)
                                    chatRoomList.stream()
                                            .sorted(Comparator.comparing(ChatRoomVO::getLastChatTime).reversed())
                                            .collect(Collectors.toList());
                        }
                        Util.setRecyclerView(getContext(), bind.rvChatroom,
                                new ChatRoomAdapter(MessengerFragment.this, chatRoomList, staff.getName()), true);
                        if (chatRoomList.isEmpty()) bind.clNotfound.view.setVisibility(View.VISIBLE);
                    } else bind.clNotfound.view.setVisibility(View.VISIBLE);
                    bind.rlProgress.view.setVisibility(View.GONE);
                }
            }
        };
    }

    public void getChatRoomClick(String key, String title) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}