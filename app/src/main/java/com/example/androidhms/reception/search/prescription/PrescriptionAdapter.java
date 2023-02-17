package com.example.androidhms.reception.search.prescription;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionPrescriptionBinding;
import com.example.androidhms.reception.vo.PrescriptionVO;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PViewHolder> {
    LayoutInflater inflater;
    PrescriptionVO vo;
    ArrayList<PrescriptionVO> preList;

    public PrescriptionAdapter(LayoutInflater inflater,PrescriptionVO vo, ArrayList<PrescriptionVO> preList) {
        this.inflater = inflater;
        this.vo = vo;
        this.preList = preList;
    }

    @NonNull
    @Override
    public PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =inflater.inflate(R.layout.item_reception_prescription,parent,false);
        PViewHolder viewHolder = new PViewHolder(v);
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
    public void onBindViewHolder(@NonNull PViewHolder h, int i) {
            h.bind.date.setText(preList.get(i).getTreate_date());
            h.bind.treamentName.setText(preList.get(i).getTreatment_name());
            h.bind.department.setText(preList.get(i).getDepartment_name());
            h.bind.doctor.setText(preList.get(i).getDoctor_name());

    }

    @Override
    public int getItemCount() {
        return preList.size();
    }

    public class PViewHolder extends RecyclerView.ViewHolder {

        public ItemReceptionPrescriptionBinding bind;
        public PViewHolder(@NonNull View v) {
            super(v);
            bind=ItemReceptionPrescriptionBinding.bind(v);
        }
    }
}
