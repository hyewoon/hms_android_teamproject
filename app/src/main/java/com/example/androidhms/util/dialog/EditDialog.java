package com.example.androidhms.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.androidhms.databinding.DialogEditBinding;

public class EditDialog {

    private final DialogEditBinding b;
    private final Dialog dialog;

    public EditDialog(Context context, LayoutInflater inflater, String title, String memo, OnSaveClickListener onSaveClick) {
        dialog = new Dialog(context);
        b = DialogEditBinding.inflate(inflater);
        dialog.setContentView(b.getRoot());
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 100;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0.3f);

        b.imgvExit.setOnClickListener(v -> dialog.dismiss());
        b.btnSave.setOnClickListener(v -> onSaveClick.onSaveClick(this, b.etMemo.getText().toString()));
        b.tvTitle.setText(title);
        b.etMemo.setText(memo);
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

    public interface OnSaveClickListener {
        void onSaveClick(EditDialog dialog, String content);
    }

}
