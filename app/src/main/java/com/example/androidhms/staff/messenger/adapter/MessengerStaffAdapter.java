package com.example.androidhms.staff.messenger.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemMessengerStaffBinding;
import com.example.androidhms.staff.messenger.MessengerStaffFragment;
import com.example.androidhms.staff.vo.StaffChatDTO;

import java.util.List;

public class MessengerStaffAdapter extends RecyclerView.Adapter<MessengerStaffAdapter.MessengerStaffViewHolder> {

    private final MessengerStaffFragment fragment;
    private final List<StaffChatDTO> staffList;

    public MessengerStaffAdapter(MessengerStaffFragment fragment, List<StaffChatDTO> staffList) {
        this.fragment = fragment;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public MessengerStaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessengerStaffViewHolder(
                fragment.getLayoutInflater().inflate(R.layout.item_messenger_staff, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessengerStaffViewHolder holder, int position) {
        StaffChatDTO vo = staffList.get(position);
        holder.bind.tvStaffname.setText(vo.getName());
        String department = vo.getDepartment_name();
        if (vo.getStaff_level() == 1) department += " 의사";
        else department += " 간호사";
        holder.bind.tvStaffdepart.setText(department);
        holder.bind.rlGetchat.setOnClickListener(fragment.onGetChatClick(position));
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class MessengerStaffViewHolder extends RecyclerView.ViewHolder {

        private final ItemMessengerStaffBinding bind;

        public MessengerStaffViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemMessengerStaffBinding.bind(itemView);

        }
    }
}
