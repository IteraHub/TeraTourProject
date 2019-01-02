package com.iterahub.teratour.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iterahub.teratour.R;
import com.iterahub.teratour.models.MessagesModel;
import com.iterahub.teratour.viewHolders.MessagesViewHolder;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesViewHolder> {

    private List<MessagesModel> messagesModelList;
    private String userId;


    public MessagesAdapter(String userId, List<MessagesModel> messagesModelList){
        this.messagesModelList = messagesModelList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_dm_layout,parent,false);
        return new MessagesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        MessagesModel msg = messagesModelList.get(position);
        if(msg.getUserId().equals(userId)){
            holder.dmSentTv.setText(msg.getText());
            holder.receivedLayout.setVisibility(View.GONE);
        }else{
            holder.dmReceivedTv.setText(msg.getText());
            holder.sentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messagesModelList.size();
    }
}
