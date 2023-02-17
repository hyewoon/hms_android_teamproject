package com.example.androidhms.customer.reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.customer.vo.RecieptVO;
import com.example.androidhms.databinding.ItemCustomerStepTowBinding;

import java.util.ArrayList;

public class StepTwoAdapter extends RecyclerView.Adapter<StepTwoAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    ArrayList<RecieptVO> reciept = new ArrayList<>();

    public StepTwoAdapter(LayoutInflater inflater, Context context, ArrayList<RecieptVO> reciept) {
        this.inflater = inflater;
        this.context = context;
        this.reciept = reciept;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_customer_step_tow, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.bind.tvName.setText(reciept.get(i).getName());
        h.bind.tvIntroduction.setText(reciept.get(i).getIntroduction());
        if((i % 2) != 0) {
            h.bind.imgvPic.setImageResource(R.drawable.profile1);
        }else if ((i % 2) == 0) {
            h.bind.imgvPic.setImageResource(R.drawable.profile2);
        }



        h.bind.llSelect.setOnClickListener(v -> {
            ReservationSelect.selectedStaff_id = reciept.get(i).getStaff_id();
            ReservationSelect.selectedStaff_name = reciept.get(i).getName();
            ReservationSelect.selectedDepartment_name = reciept.get(i).getDepartment_name();
            StepCnt.cnt = 3;
            ((ReservationActivity) context).changeStep();
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
        return reciept.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemCustomerStepTowBinding bind;
        public ViewHolder(@NonNull View v) {
            super(v);
            bind = ItemCustomerStepTowBinding.bind(v);
        }
    }
}
