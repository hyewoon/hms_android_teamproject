package com.example.androidhms.customer.info.reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.customer.vo.MedicalReceiptVO;
import com.example.androidhms.databinding.ItemCustomerReservationMedicalBinding;
import com.example.conn.RetrofitMethod;

import java.util.ArrayList;

public class MedicalReservationAdapter extends RecyclerView.Adapter<MedicalReservationAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    private ArrayList<MedicalReceiptVO> receipt = new ArrayList<>();

    public MedicalReservationAdapter(LayoutInflater inflater, Context context, ArrayList<MedicalReceiptVO> receipt) {
        this.inflater = inflater;
        this.context = context;
        this.receipt = receipt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_customer_reservation_medical, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.bind.tvDepartment.setText(receipt.get(i).getDepartment_name());
        h.bind.tvName.setText(receipt.get(i).getName());
        String receiptTime = receipt.get(i).getTime().substring(0, 16);
        h.bind.tvDate.setText(receiptTime);
        h.bind.tvLocation.setText(receipt.get(i).getLocation());
        final int index = i;
        h.bind.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("삭제 하시겠습니까?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // 확인시 처리 로직
                            Toast.makeText(context, "삭제를 완료했습니다.", Toast.LENGTH_SHORT).show();
                            new RetrofitMethod().setParams("staff_id", receipt.get(index).getStaff_id())
                                    .setParams("time", receipt.get(index).getTime())
                                    .sendPost("delete_medical.cu", (isResult, data) -> {
                                        Log.d("로그", "예약취소 : " + receipt.get(index).getStaff_id() + " " + receipt.get(index).getTime());
                                        ((Activity)context).recreate();
                                    });
                        }})
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }})
                    .show();

        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return receipt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCustomerReservationMedicalBinding bind;

        public ViewHolder(@NonNull View v) {
            super(v);
            bind = ItemCustomerReservationMedicalBinding.bind(v);
        }
    }
}
