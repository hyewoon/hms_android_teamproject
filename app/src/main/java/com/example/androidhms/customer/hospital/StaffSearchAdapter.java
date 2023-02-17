package com.example.androidhms.customer.hospital;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.customer.reservation.ReservationActivity;
import com.example.androidhms.customer.vo.StaffSearchVO;
import com.example.androidhms.databinding.ItemCustomerStaffSearchBinding;

import java.util.ArrayList;

public class StaffSearchAdapter extends RecyclerView.Adapter<StaffSearchAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    ArrayList<StaffSearchVO> staff = new ArrayList<>();

    public StaffSearchAdapter(LayoutInflater inflater, Context context, ArrayList<StaffSearchVO> staff) {
        this.inflater = inflater;
        this.context = context;
        this.staff = staff;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_customer_staff_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.bind.tvName.setText(staff.get(i).getName());
        h.bind.tvIntroduction.setText(staff.get(i).getIntroduction());
        if ((i % 2) != 0) {
            h.bind.ivProfile.setImageResource(R.drawable.profile3);
        }else if ((i % 2) == 0) {
            h.bind.ivProfile.setImageResource(R.drawable.profile4);
        }
        h.bind.btnReceipt.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReservationActivity.class);
            intent.putExtra("search", 1);
            intent.putExtra("staff_id", staff.get(i).getStaff_id());
            intent.putExtra("department_id", staff.get(i).getDepartment_id());
            context.startActivity(intent);

        });



    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return staff.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemCustomerStaffSearchBinding bind;

        public ViewHolder(@NonNull View v) {
            super(v);
            bind = ItemCustomerStaffSearchBinding.bind(v);
        }
    }

}
