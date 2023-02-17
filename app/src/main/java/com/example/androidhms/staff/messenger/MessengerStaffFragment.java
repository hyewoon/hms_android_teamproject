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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.databinding.FragmentMessengerStaffBinding;
import com.example.androidhms.staff.messenger.adapter.MessengerStaffAdapter;
import com.example.androidhms.staff.messenger.dialog.GroupDialog;
import com.example.androidhms.staff.vo.StaffChatDTO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MessengerStaffFragment extends Fragment {

    private FragmentMessengerStaffBinding bind;
    private ArrayList<StaffChatDTO> staffList;
    private ArrayList<StaffChatDTO> chatMemberList;
    private HmsFirebase fb;
    private StaffChatDTO staff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = FragmentMessengerStaffBinding.inflate(inflater, container, false);
        fb = new HmsFirebase(this.getContext(), firebaseHandler());
        staff = Util.getStaffChatDTO(getContext());

        bind.tvName.setText(staff.getName());
        new RetrofitMethod().sendGet("getStaff.ap", (isResult, data) -> {
            if (isResult) {
                staffList = new Gson().fromJson(data, new TypeToken<ArrayList<StaffChatDTO>>() {
                }.getType());
                // 자기 자신은 채팅 상대방에서 제외
                for (int i = 0; i < staffList.size(); i++) {
                    if (staffList.get(i).getStaff_id() == staff.getStaff_id()) {
                        staffList.remove(i);
                        break;
                    }
                }
                setSpinner();
            }
        });

        // 그룹 채팅방 버튼 클릭
        bind.llCreateGroup.setOnClickListener(onCreateGroupChatroomClick(inflater));

        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    public View.OnClickListener onGetChatClick(int position) {
        return v -> {
            chatMemberList = new ArrayList<>();
            staff.setLastChatCheckTime();
            chatMemberList.add(staff);
            chatMemberList.add(staffList.get(position));
            fb.makeChatRoom(chatMemberList);
        };
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.department, android.R.layout.simple_spinner_dropdown_item);
        bind.spDepartment.setAdapter(adapter);
        bind.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bind.rlProgress.view.setVisibility(View.VISIBLE);
                String selected = (String) bind.spDepartment.getSelectedItem();
                if (position != 0) {
                    ArrayList<StaffChatDTO> tempList = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tempList = (ArrayList<StaffChatDTO>) staffList.stream().filter(d -> {
                            if (selected.equals("의사")) return d.getStaff_level() == 1;
                            else if (selected.equals("간호사")) return d.getStaff_level() == 2;
                            else return d.getDepartment_name().equals(selected);
                        }).collect(Collectors.toList());
                    }
                    Util.setRecyclerView(getContext(), bind.rvMessengerStaff, new MessengerStaffAdapter(MessengerStaffFragment.this, tempList), true);
                } else {
                    Util.setRecyclerView(getContext(), bind.rvMessengerStaff, new MessengerStaffAdapter(MessengerStaffFragment.this, staffList), true);
                }
                bind.rlProgress.view.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private View.OnClickListener onCreateGroupChatroomClick(LayoutInflater inflater) {
        return v -> new GroupDialog(getContext(), staffList, inflater, (dialog, title, memberStaffList) -> {
            if (title.contains("#")) {
                Toast.makeText(getContext(), "제목에 '#'은 들어갈 수 없습니다.", Toast.LENGTH_SHORT).show();
            } else if (title.trim().equals("")) {
                Toast.makeText(getContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (memberStaffList.size() == 1) {
                Toast.makeText(getContext(), "자신을 제외한 채팅방 참여자를 추가해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                dialog.showProgress();
                fb.makeGroupChatRoom(title, memberStaffList);
                dialog.dismiss();
            }
        }).show();
    }

    private Handler firebaseHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == HmsFirebase.GET_CHATROOM_SUCCESS) {
                    if (msg.obj != null) {
                        HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("title", map.get("title"));
                        intent.putExtra("key", map.get("key"));
                        startActivity(intent);
                    } else fb.makeChatRoom(chatMemberList);
                } else if (msg.what == HmsFirebase.CREATE_GROUP_CHATROOM_SUCCESS) {
                    HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("title", map.get("title"));
                    intent.putExtra("key", map.get("key"));
                    startActivity(intent);
                }
            }
        };
    }
}