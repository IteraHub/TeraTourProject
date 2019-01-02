package com.iterahub.teratour.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iterahub.teratour.R;
import com.iterahub.teratour.interfaces.InboxItemClickedInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.dp)
    public ImageView dpImage;
    @BindView(R.id.name_tv)
    public TextView nameTv;
    @BindView(R.id.chat_text)
    public TextView chatTextTv;
    @BindView(R.id.chat_time)
    public TextView chatTime;
    @BindView(R.id.mock_layout)
    View MockLayout;

    private InboxItemClickedInterface inboxItemClickedInterface;

    public InboxViewHolder(View itemView,InboxItemClickedInterface inboxItemClickedInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.inboxItemClickedInterface = inboxItemClickedInterface;
        MockLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        inboxItemClickedInterface.itemClicked(this.getLayoutPosition());
    }
}
