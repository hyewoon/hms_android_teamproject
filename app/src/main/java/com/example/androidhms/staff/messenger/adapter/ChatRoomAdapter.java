package com.example.androidhms.staff.messenger.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemMessengerChatroomBinding;
import com.example.androidhms.staff.messenger.MessengerFragment;
import com.example.androidhms.staff.vo.ChatRoomVO;
import com.example.androidhms.util.Util;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    private final List<ChatRoomVO> chatRoomList;
    private final MessengerFragment fragment;
    private final String name;

    public ChatRoomAdapter(MessengerFragment fragment, List<ChatRoomVO> chatRoomList, String name) {
        this.chatRoomList = chatRoomList;
        this.fragment = fragment;
        this.name = name;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(fragment.getLayoutInflater().inflate(R.layout.item_messenger_chatroom, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ChatRoomVO vo = chatRoomList.get(position);
        String title = vo.getRoomTitle();
        // 1:1 채팅방일 경우, 단톡방일경우
        if (title.contains("#")) {
            String titleRv = title.replace("#", "");
            titleRv = titleRv.replaceAll(name, "");
            holder.bind.tvTitle.setText(titleRv);
        } else {
            holder.bind.tvTitle.setText(title);
            holder.bind.imgvGroup.setImageResource(R.drawable.icon_group);
        }
        if (vo.getLastChat().contains("##")) {
            holder.bind.tvLastchat.setText("공유된 링크");
        } else holder.bind.tvLastchat.setText(vo.getLastChat());
        holder.bind.tvTime.setText(Util.getTime(vo.getLastChatTime()));
        if (vo.getCount().equals("0")) holder.bind.tvCount.setVisibility(View.GONE);
        else holder.bind.tvCount.setText(vo.getCount());
        holder.itemView.setOnClickListener(v -> fragment.getChatRoomClick(vo.getKey(), title));
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ChatRoomViewHolder extends RecyclerView.ViewHolder {

        private final ItemMessengerChatroomBinding bind;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemMessengerChatroomBinding.bind(itemView);
        }
    }
}
