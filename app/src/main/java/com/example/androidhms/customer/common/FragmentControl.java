package com.example.androidhms.customer.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FragmentControl {

    private final AppCompatActivity activity;

    public FragmentControl(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setFragment(int id, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    public void addFragment(int id, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    public void showFragment(Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    public void hideFragment(Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    public void removeFragment(Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().remove(fragment);
    }


}
