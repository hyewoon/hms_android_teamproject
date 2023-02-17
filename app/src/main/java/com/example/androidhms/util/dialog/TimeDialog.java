package com.example.androidhms.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;

import com.example.androidhms.databinding.DialogEditBinding;
import com.example.androidhms.databinding.DialogTimeBinding;
import com.example.androidhms.util.Util;

import java.sql.Timestamp;

public class TimeDialog {

    private final DialogTimeBinding b;
    private final Dialog dialog;
    private String time;

    public TimeDialog(Context context, LayoutInflater inflater, TimeDialog.OnSelectClickListener listener) {
        dialog = new Dialog(context);
        b = DialogTimeBinding.inflate(inflater);
        dialog.setContentView(b.getRoot());
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 100;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0.3f);
        time = Util.getTime(new Timestamp(System.currentTimeMillis()).toString());

        b.imgvExit.setOnClickListener(v -> dialog.dismiss());
        b.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            String hh = hourOfDay >= 10 ? "" + hourOfDay : "0" + hourOfDay;
            String mm = minute >= 10 ? "" + minute : "0" + minute;
            time = hh + ":" + mm;
        });
        b.btnSave.setOnClickListener(v -> listener.onSelectClick(this, time));

    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface OnSelectClickListener {
        void onSelectClick(TimeDialog dialog, String time);
    }
}
