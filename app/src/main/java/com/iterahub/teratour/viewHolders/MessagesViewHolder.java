package com.iterahub.teratour.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iterahub.teratour.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.dm_sent_text)
    public TextView dmSentTv;
    @BindView(R.id.dm_received_text)
    public TextView dmReceivedTv;
    @BindView(R.id.dm_received_layout)
    public View receivedLayout;
    @BindView(R.id.dm_sent_layout)
    public View sentLayout;

    public MessagesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
