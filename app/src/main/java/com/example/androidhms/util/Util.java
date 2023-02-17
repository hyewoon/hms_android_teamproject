package com.example.androidhms.util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.staff.vo.StaffChatDTO;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.util.dialog.CalendarDialog;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util {

    public static StaffVO staff;
    public static boolean isStaffActivityForeground = false;
    public static String sharedContent;

    private Util() {}

    /**
     * 자동로그인시 SharedPreferences 에 저장된 staff 데이터를 불러옴
     */
    public static StaffVO getStaff(Context context) {
        if (staff == null) {
            SharedPreferences preferences = context.getSharedPreferences("staffLoginInfo", MODE_PRIVATE);
            String staffJson = preferences.getString("staffData", null);
            staff = new Gson().fromJson(staffJson, StaffVO.class);
        }
        return staff;
    }

    /**
     * 메신저 Activity 에서 사용되는 StaffChatDTO 로 변환
     */
    public static StaffChatDTO getStaffChatDTO(Context context) {
        if (staff == null) getStaff(context);
        return new StaffChatDTO(staff.getStaff_id(),
                staff.getStaff_level(), staff.getDepartment_id(), staff.getName(),
                staff.getDepartment_name());
    }

    /**
     * RecyclerView 설정
     */
    public static void setRecyclerView(Context context, RecyclerView rv, RecyclerView.Adapter<?> adapter, boolean vertical) {
        RecyclerView.LayoutManager lm;
        if (vertical) lm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        else lm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(null);
    }

    /**
     * dp -> px 변환
     */
    public static int getPxFromDp(Activity activity, int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        return (int) (dp * density);
    }

    /**
     * MaterialCalendar Dialog 설정
     */
    public static void setEditTextDate(Context context, LayoutInflater inflater, EditText edit, CalendarDialog.SetDateClickListener listener) {
        edit.setOnClickListener(v -> {
            if (edit.getText().toString().equals(""))
                new CalendarDialog(context, inflater, listener).show();
            else
                new CalendarDialog(context, inflater, listener).setDate(edit.getText().toString()).show();
        });
    }

    public static void setEditTextDate(Context context, LayoutInflater inflater, EditText edit,
                                       CalendarDialog.SetDateClickListener listener, Timestamp maxTime, Timestamp minTime) {
        edit.setOnClickListener(v -> {
            if (maxTime == null && minTime != null) {
                new CalendarDialog(context, inflater, listener).setDate(edit.getText().toString())
                        .setMinDate(minTime).show();
            } else if (maxTime != null && minTime != null)
                new CalendarDialog(context, inflater, listener).setDate(edit.getText().toString())
                        .setMaxDate(maxTime).setMinDate(minTime).show();
            else if (maxTime != null) {
                new CalendarDialog(context, inflater, listener).setDate(edit.getText().toString())
                        .setMaxDate(maxTime).show();
            }
        });
    }

    /**
     * Timestamp 시간 연산<br>
     * ex) Util.timestampOperator(time, Calendar.YEAR, 1);
     */
    public static Timestamp timestampOperator(Timestamp time, int pattern, int number) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(pattern, number);
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * Timestamp 포맷<br>
     * ex) Util.timestampOperator(time, "yyyy-MM-dd HH:mm:ss"); -> 2022-01-01 00:00:00
     */
    public static String dateFormat(Timestamp time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
        return sdf.format(time);
    }

    /**
     * Timestamp에서 연,월,일만 추출<br>
     * 2022-01-01 00:00:00 -> 2022-01-01
     */
    public static String getDate(Timestamp time) {
        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat month = new SimpleDateFormat("MM", Locale.KOREA);
        SimpleDateFormat day = new SimpleDateFormat("dd", Locale.KOREA);
        return year.format(time) + "-" + month.format(time) + "-" + day.format(time);
    }

    /**
     * 채팅작성시간을 Timestamp(String)로 기록<br>
     * 2022-01-01 00:00:00.000
     */
    public static String getChatTimeStamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    /**
     * 채팅시간에서 시,분만 추출<br>
     * 2022-01-01 08:05:00.111 -> 08:05
     */
    public static String getTime(String time) {
        return new StringBuilder(time).substring(11, 16);
    }

    /**
     * 주민번호로부터 생일가져오기<br>
     * 950217 -> 1995-02-17
     */
    public static String getBirthDay(String social_id) {
        StringBuilder sb = new StringBuilder(social_id);
        int year = Integer.parseInt(sb.substring(0, 2));
        if (year < 22) year += 2000;
        else year += 1900;
        String month = sb.substring(2, 4);
        String day = sb.substring(4);
        return year + "-" + month + "-" + day;
    }

    /**
     * 만나이 계산
     */
    public static int getAge(String social_id) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(System.currentTimeMillis()));
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);

        StringBuilder sb = new StringBuilder(social_id);
        int year = Integer.parseInt(sb.substring(0, 2));
        if (year < 22) year += 2000;
        else year += 1900;
        int month = Integer.parseInt(sb.substring(2, 4));
        int day = Integer.parseInt(sb.substring(4));

        int age = currentYear - year;
        int m = currentMonth - month;
        if (m < 0 || (m == 0 && currentDay < day)) age--;
        return age;
    }

    /**
     * 올라와있는 안드로이드 키보드를 내림
     */
    public static void keyboardDown(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {

        }
    }


}
