package com.example.androidhms.reception.search.ward;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionWardBinding;
import com.example.androidhms.reception.vo.WardVO;

import java.util.ArrayList;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.WViewholder> {
    LayoutInflater inflater;
    WardVO vo;
    ArrayList <WardVO>list;

    public WardAdapter(LayoutInflater inflater, WardVO vo, ArrayList<WardVO> list) {
        this.inflater = inflater;
        this.vo = vo;
        this.list = list;
    }

    @NonNull
    @Override
    public WViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_reception_ward,parent,false);
        WViewholder viewholder = new WViewholder(v);
        return viewholder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull WViewholder h, int i) {
        h.bind.startDate.setText(list.get(i).getAdmission_date());
        h.bind.ward.setText(list.get(i).getWard_id()+"");
        h.bind.bed.setText(list.get(i).getBed() +"");
        h.bind.department.setText(list.get(i).getDepartment_name());
        h.bind.doctor.setText(list.get(i).getDoctor_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WViewholder extends RecyclerView.ViewHolder {

        public ItemReceptionWardBinding bind;

        public WViewholder(@NonNull View v) {

            super(v);
            bind =ItemReceptionWardBinding.bind(v);
        }
    }
}
