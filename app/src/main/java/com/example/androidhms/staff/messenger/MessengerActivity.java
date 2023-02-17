package com.example.androidhms.staff.messenger;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityMessengerBinding;
import com.example.androidhms.staff.StaffBaseActivity;

public class MessengerActivity extends StaffBaseActivity {

    private ActivityMessengerBinding bind;
    private MessengerFragment messengerFragment;
    private MessengerStaffFragment messengerStaffFragment;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getSharedPreferences("messengerInfo", MODE_PRIVATE);
        editor = preferences.edit();

        messengerStaffFragment = new MessengerStaffFragment();
        messengerFragment = new MessengerFragment();
        addFragment(bind.flContainer.getId(), messengerStaffFragment);
        addFragment(bind.flContainer.getId(), messengerFragment);

        bind.ivMessenger.setOnClickListener(onBnvClick());
        bind.ivMessengerStaff.setOnClickListener(onBnvClick());

        if (preferences.getInt("selected", 0) == 0) {
            bind.ivMessengerStaff.performClick();
        } else bind.ivMessenger.performClick();

        if (getIntent().getBooleanExtra("toolbar", false)) {
            bind.ivMessenger.performClick();
        }
    }

    @Override
    protected View getLayoutResource() {
        bind = ActivityMessengerBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    private View.OnClickListener onBnvClick() {
        return v -> {
            if (v.getId() == R.id.iv_messenger_staff) {
                hideFragment(messengerFragment);
                showFragment(messengerStaffFragment);
                bind.ivMessengerStaff.setColorFilter(
                        ContextCompat.getColor(MessengerActivity.this, R.color.text_color));
                bind.ivMessenger.setColorFilter(
                        ContextCompat.getColor(MessengerActivity.this, R.color.gray));
                editor.putInt("selected", 0);
                editor.commit();
            } else if (v.getId() == R.id.iv_messenger) {
                hideFragment(messengerStaffFragment);
                showFragment(messengerFragment);
                bind.ivMessenger.setColorFilter(
                        ContextCompat.getColor(MessengerActivity.this, R.color.text_color));
                bind.ivMessengerStaff.setColorFilter(
                        ContextCompat.getColor(MessengerActivity.this, R.color.gray));
                editor.putInt("selected", 1);
                editor.commit();
            }
        };
    }
}