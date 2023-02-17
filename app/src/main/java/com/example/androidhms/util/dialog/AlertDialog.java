package com.example.androidhms.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.example.androidhms.databinding.DialogAlertBinding;

public class AlertDialog {

    private final DialogAlertBinding b;
    private final Dialog dialog;

    public AlertDialog(Context context, LayoutInflater inflater, String title, String content,
                       OnAlertDialogClickListener callback) {
        dialog = new Dialog(context);
        b = DialogAlertBinding.inflate(inflater);
        dialog.setContentView(b.getRoot());
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 100;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0.3f);
        dialog.setCancelable(false);

        b.tvTitle.setText(title);
        b.tvContent.setText(content);
        b.tvNo.setOnClickListener(v -> callback.setOnClickNo(this));
        b.tvYes.setOnClickListener(v -> callback.setOnClickYes(this));
    }

    public AlertDialog setYesText(String str) {
        b.tvYes.setText(str);
        return this;
    }

    public AlertDialog setNoText(String str) {
        b.tvNo.setText(str);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface OnAlertDialogClickListener {
        void setOnClickYes(AlertDialog dialog);
        void setOnClickNo(AlertDialog dialog);
    }

}