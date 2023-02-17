package com.example.androidhms.staff.messenger.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.androidhms.databinding.DialogCreateGroupBinding;
import com.example.androidhms.staff.messenger.adapter.DialogStaffAdapter;
import com.example.androidhms.staff.vo.StaffChatDTO;
import com.example.androidhms.util.Util;

import java.util.ArrayList;
import java.util.List;

public class GroupDialog {

    private final DialogCreateGroupBinding b;
    private final Dialog dialog;
    private final List<StaffChatDTO> allStaffList;
    private final List<StaffChatDTO> memberStaffList;

    public GroupDialog(Context context, List<StaffChatDTO> allStaffList, LayoutInflater inflater, OnDialogBtnClickListener listener) {
        dialog = new Dialog(context);
        b = DialogCreateGroupBinding.inflate(inflater);
        dialog.setContentView(b.getRoot());
        this.allStaffList = allStaffList;
        memberStaffList = new ArrayList<>();
        memberStaffList.add(Util.getStaffChatDTO(context));
        setTextMemberList();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 100;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0.3f);

        b.imgvExit.setOnClickListener(v -> dialog.dismiss());
        b.btnCreate.setOnClickListener(v -> listener.onCreateClick(this, b.etTitle.getText().toString(), memberStaffList));

        Util.setRecyclerView(context, b.rvStaffList, new DialogStaffAdapter(allStaffList, this, inflater), true);

    }

    public void onSelectStaff(int position, boolean checked) {
        if (checked) memberStaffList.add(allStaffList.get(position));
        else memberStaffList.remove(allStaffList.get(position));
        setTextMemberList();
    }

    private void setTextMemberList() {
        StringBuilder member = new StringBuilder();
        for (int i = 0; i < memberStaffList.size(); i++) {
            member.append(memberStaffList.get(i).getName());
            if (i != memberStaffList.size() - 1) member.append(" / ");
        }
        b.tvMemberList.setText(member);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void showProgress() {
        b.rlProgress.view.setVisibility(View.VISIBLE);
    }

    public interface OnDialogBtnClickListener {
        void onCreateClick(GroupDialog dialog, String title, List<StaffChatDTO> memberStaffList);
    }
}
