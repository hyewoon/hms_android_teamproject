package com.example.androidhms.reception.search;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionNamelistBinding;
import com.example.androidhms.reception.search.record.detailrecord.DetailRecordActivity;
import com.example.androidhms.staff.vo.PatientVO;

import java.util.ArrayList;

public class PatientNameAdapter extends RecyclerView.Adapter<PatientNameAdapter.PViewHolder> {
    LayoutInflater inflater;
    ArrayList<PatientVO> list;
    SearchActivity activity;
    public PatientNameAdapter(LayoutInflater inflater, PatientVO vo, ArrayList<PatientVO> list ,SearchActivity activity) {
        this.inflater = inflater;
        this.list = list;
        this.activity = activity;

    }
    public PatientNameAdapter(LayoutInflater inflater, PatientVO vo, ArrayList<PatientVO> list ) {
        this.inflater = inflater;
        this.list = list;
    }

    @NonNull
    @Override
    public PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_reception_namelist,parent,false);
        PViewHolder viewHolder = new PViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull PViewHolder h, int i) {
            h.bind.tvPatientNo.setText(list.get(i).getPatient_id()+"");
            h.bind.tvPatientName.setText(list.get(i).getName());
            h.bind.tvSocialId.setText(list.get(i).getSocial_id());
            h.bind.tvPatientPhone.setText(list.get(i).getPhone_number());

          h.bind.llChoiceName.setOnClickListener(v -> {
              Log.d("로그", "onBindViewHolder: " + "어댑터");
              activity.patient_id =  h.bind.tvPatientNo.getText().toString();
              Log.d("로그", "onBindViewHolder: " +  activity.patient_id);
              activity.searchPatientInfo();
              activity.searchAppointment();
              activity.searchMedicalRecord();
              activity.bind.recvNameList.setVisibility(View.GONE);

              //어댑터에서 액티비티로 값 보내기
//              String patient_id = h.bind.tvPatientNo.getText().toString() ;
//              Intent intent_info = new Intent(v.getContext(),SearchActivity.class); //<----연결할 액티비티
//              intent_info.putExtra("patient_id",patient_id);
//              Log.d("로그", "onBindViewHolder: " +patient_id + "어댑터에서 보내는 값" );
  //           ((SearchActivity)v.getContext()).startActivity(intent_info); // (SearchActivity)v.getContext()<----어댑터를 호출한 상위 액티비티 반드시
          });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PViewHolder extends RecyclerView.ViewHolder {

        public ItemReceptionNamelistBinding bind;

        public PViewHolder(@NonNull View v) {
            super(v);
            bind = ItemReceptionNamelistBinding.bind(v);
        }
    }
}
