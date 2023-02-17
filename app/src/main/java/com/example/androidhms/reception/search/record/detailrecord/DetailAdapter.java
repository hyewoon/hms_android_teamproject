package com.example.androidhms.reception.search.record.detailrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionNamelistBinding;
import com.example.androidhms.staff.vo.PatientVO;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DViewHolder> {
    LayoutInflater inflater;
    ArrayList<PatientVO> list;
    DetailRecordActivity activity;

    public DetailAdapter(LayoutInflater inflater, PatientVO patientVO, ArrayList<PatientVO> list, DetailRecordActivity activity) {
        this.inflater = inflater;
        this.list = list;
        this.activity = activity;
    }


    @NonNull
    @Override
    public DViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_reception_namelist,parent,false);
        DViewHolder viewHolder = new DViewHolder(v);
        return viewHolder;
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
    public void onBindViewHolder(@NonNull DViewHolder h, int i) {
        h.bind.tvPatientNo.setText(list.get(i).getPatient_id()+"");
        h.bind.tvPatientName.setText(list.get(i).getName());
        h.bind.tvSocialId.setText(list.get(i).getSocial_id());
        h.bind.tvPatientPhone.setText(list.get(i).getPhone_number());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DViewHolder extends RecyclerView.ViewHolder {

        public ItemReceptionNamelistBinding bind;

        public DViewHolder(@NonNull View v) {
            super(v);
            bind = ItemReceptionNamelistBinding.bind(v);
        }
    }
}
