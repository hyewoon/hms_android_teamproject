package com.example.androidhms.customer.reservation;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.customer.LoginInfo;
import com.example.androidhms.customer.common.CommonMethod;
import com.example.androidhms.customer.vo.MedicalReceiptVO;
import com.example.androidhms.databinding.FragmentCustomerStepFourBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StepFourFragment extends Fragment {
    private FragmentCustomerStepFourBinding bind;
    private final String TAG = "로그";
    ArrayList<MedicalReceiptVO> receipt = new ArrayList<>();
    private int reservedTime;
    Dialog dialog;
    TextView tv_department, tv_name, tv_date, tv_time;
    EditText et_memo;
    Button btn_back, btn_insert;
    private int nowTime;

    private int[] count = new int[26];
    private int tempTime;

    private int cnt0830, cnt0845, cnt0900, cnt0915, cnt0930, cnt0945, cnt1000, cnt1015, cnt1030, cnt1045, cnt1100;
    private int cnt1115, cnt1130, cnt1145, cnt1400, cnt1415, cnt1430, cnt1445, cnt1500, cnt1515, cnt1530, cnt1545;
    private int cnt1600, cnt1615, cnt1630, cnt1645;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCustomerStepFourBinding.inflate(inflater, container, false);

        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");

        dialog = new Dialog(getActivity());



        new RetrofitMethod().setParams("staff_id", ReservationSelect.selectedStaff_id)
                .setParams("date", ReservationSelect.selectedDate)
                .sendPost("medical_schedule.cu", (isResult, data) -> {
            receipt = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalReceiptVO>>() {
            }.getType());

            for (int i = 0; i < 9; i++) {

                int test = i * 100;
                if (i == 3) {i = 5;}

                LinearLayout ln_layout2 =  newLinear();
                //08:00 ~ 16:45 까지 고정
                int[] flag = new int[4];
                for (int j = 0; j < receipt.size(); j++) { // 08:15
                    int tempTime =  Integer.parseInt(CommonMethod.extractDate(receipt.get(j).getTime()).substring(8, 12) );
                    if((800 + test) <= tempTime && tempTime  <=  (815 + test) ){
                        flag[0] = flag[0] + 1;
                    }else if((815 + test) <= tempTime  && tempTime  <=  (830 + test) ){
                        flag[1] = flag[1] + 1;
                    }else if((830 + test) <= tempTime  && tempTime  <=  (845 + test)){
                        flag[2] = flag[2] + 1;
                    }
                }
                ln_layout2.addView(newTextView(2, 800 + test , flag[0]));//08:00 <= 검정
                ln_layout2.addView(newTextView(2, 815 + test , flag[1]));//08:15 <= 검정
                ln_layout2.addView(newTextView(2, 830 + test , flag[2]));//08:30
                ln_layout2.addView(newTextView(2, 845 + test , flag[3]));
                bind.lnLayout.addView(ln_layout2);
            }

        });




//
//        new RetrofitMethod().setParams("staff_id", 1051).sendPost("medical_schedule.cu", (isResult, data) -> {
//            receipt = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalReceiptVO>>(){}.getType());
//
//            Log.d(TAG, "선택된 부서ID : " + ReservationSelect.selectedDepartment_id);
//            Log.d(TAG, "선택된 부서명 : " + ReservationSelect.selectedDepartment_name);
//            Log.d(TAG, "선택된 의사ID : " + ReservationSelect.selectedStaff_id);
//            Log.d(TAG, "선택된 의사명 : " + ReservationSelect.selectedStaff_name);
//            Log.d("로그", "선택된 날짜 : " + ReservationSelect.selectedDate);
//
//            Log.d("로그", "사이즈 : " + receipt.size());
//            for (int i = 0; i < receipt.size(); i++) {
//                tempTime = 83000;
//                if (Integer.parseInt(CustomerCommon.extractDate(receipt.get(i).getTime()).substring(0, 8))
//                        == Integer.parseInt(ReservationSelect.selectedDate)) {
//                    reservedTime = Integer.parseInt(CustomerCommon.extractDate(receipt.get(i).getTime()).substring(8, 14));
//
//                    for (int j = 0; j < count.length - 1; j++) {
//                        tempTime += 1500;
//                        //60분이 지나면 1시간 +
//                        if (Integer.toString(tempTime).substring(Integer.toString(tempTime).length()-4, Integer.toString(tempTime).length()-3).equals("6")) {
//                            tempTime = tempTime + 4000;
//                        }
//                        //12시 ~ 14시 점심시간
//                        if (tempTime > 120000 && tempTime < 140000) {tempTime = 140000;}
//
//                        if (reservedTime >= tempTime && reservedTime < tempTime + 1500) {count[j]++;}
//                        Log.d(TAG,  "i" + i + " " +"j" + j + " : " + count[j]);
//
//                    }
//
//
//                 /*   if (reservedTime >= 83000 && reservedTime < 84500) {cnt0830++;}
//                    if (reservedTime >= 84500 && reservedTime < 90000) {cnt0845++;}
//                    if (reservedTime >= 90000 && reservedTime < 91500) {cnt0900++;}
//                    if (reservedTime >= 91500 && reservedTime < 93000) {cnt0915++;}
//                    if (reservedTime >= 93000 && reservedTime < 94500) {cnt0930++;}
//                    if (reservedTime >= 94500 && reservedTime < 100000) {cnt0945++;}
//                    if (reservedTime >= 100000 && reservedTime < 101500) {cnt1000++;}
//                    if (reservedTime >= 101500 && reservedTime < 103000) {cnt1015++;}
//                    if (reservedTime >= 103000 && reservedTime < 104500) {cnt1030++;}
//                    if (reservedTime >= 104500 && reservedTime < 110000) {cnt1045++;}
//                    if (reservedTime >= 110000 && reservedTime < 111500) {cnt1100++;}
//                    if (reservedTime >= 111500 && reservedTime < 113000) {cnt1115++;}
//                    if (reservedTime >= 113000 && reservedTime < 114500) {cnt1130++;}
//                    if (reservedTime >= 114500 && reservedTime < 120000) {cnt1145++;}
//                    if (reservedTime >= 140000 && reservedTime < 141500) {cnt1400++;}
//                    if (reservedTime >= 141500 && reservedTime < 143000) {cnt1415++;}
//                    if (reservedTime >= 143000 && reservedTime < 144500) {cnt1430++;}
//                    if (reservedTime >= 144500 && reservedTime < 150000) {cnt1445++;}
//                    if (reservedTime >= 150000 && reservedTime < 151500) {cnt1500++;}
//                    if (reservedTime >= 151500 && reservedTime < 153000) {cnt1515++;}
//                    if (reservedTime >= 153000 && reservedTime < 154500) {cnt1530++;}
//                    if (reservedTime >= 154500 && reservedTime < 160000) {cnt1545++;}
//                    if (reservedTime >= 160000 && reservedTime < 161500) {cnt1600++;}
//                    if (reservedTime >= 161500 && reservedTime < 163000) {cnt1615++;}
//                    if (reservedTime >= 163000 && reservedTime < 164500) {cnt1630++;}
//                    if (reservedTime >= 164500 && reservedTime < 170000) {cnt1645++;}*/
//
//                }
//            }
//
//
///*            if (count[0] < 3) {bind.tv0830.setTextColor(Color.BLACK);bind.tv0830.setOnClickListener(v -> {ReservationSelect.selectTime = "083000";
//                String date = ReservationSelect.selectedDate.substring(0, 4) + "-"
//                        + ReservationSelect.selectedDate.substring(4, 6) + "-"
//                        + ReservationSelect.selectedDate.substring(6, 8) + " "
//                        + ReservationSelect.selectTime.substring(0, 2) + ":"
//                        + ReservationSelect.selectTime.substring(2, 4) + ":"
//                        + ReservationSelect.selectTime.substring(4, 6);
//                goDialog(date);
//            });}
//            if (count[1] < 3) {bind.tv0845.setTextColor(Color.BLACK);bind.tv0845.setOnClickListener(v -> {ReservationSelect.selectTime = "084500";
//                Log.d(TAG, "count : " + count[1]);
//                String date = ReservationSelect.selectedDate.substring(0, 4) + "-"
//                        + ReservationSelect.selectedDate.substring(4, 6) + "-"
//                        + ReservationSelect.selectedDate.substring(6, 8) + " "
//                        + ReservationSelect.selectTime.substring(0, 2) + ":"
//                        + ReservationSelect.selectTime.substring(2, 4) + ":"
//                        + ReservationSelect.selectTime.substring(4, 6);
//                goDialog(date);
//                Log.d("로그", "날짜형식 포맷 : " + date);
//            });}
//            if (count[2] < 3) {bind.tv0900.setTextColor(Color.BLACK);bind.tv0900.setOnClickListener(v -> {ReservationSelect.selectTime = "090000";
//                dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.dialog_receipt_selected);
//                dialog.show();
//            });}
//            if (cnt0915 < 3) {bind.tv0915.setTextColor(Color.BLACK);bind.tv0915.setOnClickListener(v -> {ReservationSelect.selectTime = "091500";});}
//            if (cnt0930 < 3) {bind.tv0930.setTextColor(Color.BLACK);bind.tv0930.setOnClickListener(v -> {ReservationSelect.selectTime = "093000";});}
//            if (cnt0945 < 3) {bind.tv0945.setTextColor(Color.BLACK);bind.tv0945.setOnClickListener(v -> {ReservationSelect.selectTime = "094500";});}
//            if (cnt1000 < 3) {bind.tv1000.setTextColor(Color.BLACK);bind.tv1000.setOnClickListener(v -> {ReservationSelect.selectTime = "100000";});}
//            if (cnt1015 < 3) {bind.tv1015.setTextColor(Color.BLACK);bind.tv1015.setOnClickListener(v -> {ReservationSelect.selectTime = "101500";});}
//            if (cnt1030 < 3) {bind.tv1030.setTextColor(Color.BLACK);bind.tv1030.setOnClickListener(v -> {ReservationSelect.selectTime = "103000";});}
//            if (cnt1045 < 3) {bind.tv1045.setTextColor(Color.BLACK);bind.tv1045.setOnClickListener(v -> {ReservationSelect.selectTime = "104500";});}
//            if (cnt1100 < 3) {bind.tv1100.setTextColor(Color.BLACK);bind.tv1100.setOnClickListener(v -> {ReservationSelect.selectTime = "110000";});}
//            if (cnt1115 < 3) {bind.tv1115.setTextColor(Color.BLACK);bind.tv1115.setOnClickListener(v -> {ReservationSelect.selectTime = "111500";});}
//            if (cnt1130 < 3) {bind.tv1130.setTextColor(Color.BLACK);bind.tv1130.setOnClickListener(v -> {ReservationSelect.selectTime = "113000";});}
//            if (cnt1145 < 3) {bind.tv1145.setTextColor(Color.BLACK);bind.tv1145.setOnClickListener(v -> {ReservationSelect.selectTime = "114500";});}
//            if (cnt1400 < 3) {bind.tv1400.setTextColor(Color.BLACK);bind.tv1400.setOnClickListener(v -> {ReservationSelect.selectTime = "140000";});}
//            if (cnt1415 < 3) {bind.tv1415.setTextColor(Color.BLACK);bind.tv1415.setOnClickListener(v -> {ReservationSelect.selectTime = "141500";});}
//            if (cnt1430 < 3) {bind.tv1430.setTextColor(Color.BLACK);bind.tv1430.setOnClickListener(v -> {ReservationSelect.selectTime = "143000";});}
//            if (cnt1445 < 3) {bind.tv1445.setTextColor(Color.BLACK);bind.tv1445.setOnClickListener(v -> {ReservationSelect.selectTime = "144500";});}
//            if (cnt1500 < 3) {bind.tv1500.setTextColor(Color.BLACK);bind.tv1500.setOnClickListener(v -> {ReservationSelect.selectTime = "150000";});}
//            if (cnt1515 < 3) {bind.tv1515.setTextColor(Color.BLACK);bind.tv1515.setOnClickListener(v -> {ReservationSelect.selectTime = "151500";});}
//            if (cnt1530 < 3) {bind.tv1530.setTextColor(Color.BLACK);bind.tv1530.setOnClickListener(v -> {ReservationSelect.selectTime = "153000";});}
//            if (cnt1545 < 3) {bind.tv1545.setTextColor(Color.BLACK);bind.tv1545.setOnClickListener(v -> {ReservationSelect.selectTime = "154500";});}
//            if (cnt1600 < 3) {bind.tv1600.setTextColor(Color.BLACK);bind.tv1600.setOnClickListener(v -> {ReservationSelect.selectTime = "160000";});}
//            if (cnt1615 < 3) {bind.tv1615.setTextColor(Color.BLACK);bind.tv1615.setOnClickListener(v -> {ReservationSelect.selectTime = "161500";});}
//            if (cnt1630 < 3) {bind.tv1630.setTextColor(Color.BLACK);bind.tv1630.setOnClickListener(v -> {ReservationSelect.selectTime = "163000";});}
//            if (cnt1645 < 3) {bind.tv1645.setTextColor(Color.BLACK);bind.tv1645.setOnClickListener(v -> {ReservationSelect.selectTime = "164500";});}
//
//            if (cnt0900 < 3) {bind.tv0900.setTextColor(Color.BLACK);}
//
//            bind.tv0830.setOnClickListener(v1 -> {
//                ReservationSelect.selectTime = "083000";
//
//
//
//
//
//            });*/
//
//
//
//
//        });


        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    //리니어 레이아웃
    public LinearLayout newLinear(){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(40, 20, 40, 20);
        return layout;
    }

    //텍스트뷰
    public TextView newTextView(int gravity, int time , int flag ){
        TextView textView = new TextView(getContext());
        String a = "";
        String b = "";

        textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        if(gravity == 1){
            textView.setGravity(Gravity.LEFT);
        }else if(gravity==2){
            textView.setGravity(Gravity.CENTER);
        }else if(gravity==3){
            textView.setGravity(Gravity.RIGHT);
        }

        if (Integer.toString(time).length() == 3) {
            a = Integer.toString(time).substring(Integer.toString(time).length()-3, Integer.toString(time).length()-2);
        }else if (Integer.toString(time).length() == 4) {
            a = Integer.toString(time).substring(Integer.toString(time).length()-4, Integer.toString(time).length()-2);
        }
        b = Integer.toString(time).substring(Integer.toString(time).length()-2, Integer.toString(time).length());

        textView.setText(a + " : " + b);


        if(flag >= 3){
            textView.setTextColor(Color.LTGRAY);
            textView.setEnabled(false);
        }else{
            textView.setTextColor(Color.BLACK);
        }
        textView.setTextSize(16);
        textView.setOnClickListener(v -> {

//                        + ReservationSelect.selectTime.substring(0, 2) + ":"
//                        + ReservationSelect.selectTime.substring(2, 4) + ":"
//                        + ReservationSelect.selectTime.substring(4, 6);

            String tempDate =   ReservationSelect.selectedDate.substring(0, 4) + "-"
                        + ReservationSelect.selectedDate.substring(4, 6) + "-"
                        + ReservationSelect.selectedDate.substring(6, 8) + " " + String.format("%04d",time).substring(0,2) + ":" + String.format("%04d",time).substring(2,4);

            Log.d(TAG, "newTextView: ");
            goDialog(tempDate);
        });
        
        
        return textView;
    }


    public void goDialog(String date) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_receipt_selected);
        WindowManager.LayoutParams lp =  new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = 800;
        tv_department = dialog.findViewById(R.id.tv_department);
        tv_name = dialog.findViewById(R.id.tv_name);
        tv_date = dialog.findViewById(R.id.tv_date);
        tv_time = dialog.findViewById(R.id.tv_time);
        et_memo = dialog.findViewById(R.id.et_memo);
        btn_back = dialog.findViewById(R.id.btn_back);
        btn_insert = dialog.findViewById(R.id.btn_insert);

        tv_department.setText(ReservationSelect.selectedDepartment_name);
        tv_name.setText(ReservationSelect.selectedStaff_name);
        tv_date.setText(date.substring(0, 10));
        tv_time.setText(date.substring(10, 16));

        dialog.show();
        Window window = dialog.getWindow();
        window.setAttributes(lp);

        btn_back.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btn_insert.setOnClickListener(v -> {
            new RetrofitMethod().setParams("patient_id", LoginInfo.check_id)
                    .setParams("staff_id", ReservationSelect.selectedStaff_id)
                    .setParams("time", date)
                    .setParams("memo", String.valueOf(et_memo.getText()))
                    .sendPost("insert_medical.cu", (isResult1, data1) -> {
                        dialog.dismiss();
                        getActivity().onBackPressed();
                        getActivity().finish();
                        Toast.makeText(getActivity(), "예약이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    });
        });


    }



}