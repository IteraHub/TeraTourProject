package com.iterahub.teratour.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iterahub.teratour.R;
import com.iterahub.teratour.models.ChatsModel;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.DateTimeUtils;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewHolders.InboxViewHolder;
import com.iterahub.teratour.interfaces.InboxItemClickedInterface;
import com.iterahub.teratour.models.InboxModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<InboxViewHolder> {

    private InboxItemClickedInterface inboxItemClickedInterface;
    private List<ChatsModel> chatsModelsList;
    private Context context;

    public ChatsAdapter(Context context, List<ChatsModel> chatsModelsList,
                        InboxItemClickedInterface inboxItemClickedInterface){
        this.inboxItemClickedInterface = inboxItemClickedInterface;
        this.chatsModelsList = chatsModelsList;
        this.context = context;
    }

    @Override
    public InboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_items_layout,parent,false);
        return new InboxViewHolder(view,inboxItemClickedInterface);
    }

    @Override
    public void onBindViewHolder(InboxViewHolder holder, int position) {
        ChatsModel chats = chatsModelsList.get(position);
        if(chats.getChatOwner() != null){
            ShowUtils.setImage(context,holder.dpImage,chats.getChatOwner().getImage_url());
            holder.nameTv.setText(String.valueOf(chats.getChatOwner().getFirstname() + " " + chats.getChatOwner().getLastname()));
        }
        holder.chatTextTv.setText(chats.getLastMessage());
        holder.chatTime.setText(DateTimeUtils.formatDateTimeWithNow(chats.getCreatedAt()));
    }

    public void addChatOwner(UserModel userModel,String myUserId){
        String userId;
        for(int i = 0; i < chatsModelsList.size(); i++){
            if(chatsModelsList.get(i).getOtherUserId().equals(myUserId)){
                userId = chatsModelsList.get(i).getMyUserId();
            }else{
                userId = chatsModelsList.get(i).getOtherUserId();
            }
            if(userModel.getId().equals(userId)){
                chatsModelsList.get(i).setChatOwner(userModel);
                notifyItemChanged(i);
                return;
            }
        }
    }

    public UserModel getChatOwner(int position){
        if(position < chatsModelsList.size()){
            return chatsModelsList.get(position).getChatOwner();
        }else{
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return chatsModelsList.size();
    }

}
