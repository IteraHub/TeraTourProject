package com.iterahub.teratour.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iterahub.teratour.R;
import com.iterahub.teratour.interfaces.SearchItemClickedInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arinzedroid on 8/31/2018.
 */

public class SearchViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.profile_pic)
    public ImageView profilePic;
    @BindView(R.id.profile_name)
    public TextView profileName;
    @BindView(R.id.profile_desc)
    public TextView profileDesc;
    @BindView(R.id.mock_layout)
    public View mockLayout;

    private SearchItemClickedInterface searchItemClickedInterface;

    public SearchViewHolders(View itemView, SearchItemClickedInterface searchItemClickedInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mockLayout.setOnClickListener(this);
        profilePic.setOnClickListener(this);
        this.searchItemClickedInterface = searchItemClickedInterface;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profile_pic)
            searchItemClickedInterface.profileImageClicked(this.getLayoutPosition());
        else if(v.getId() == R.id.mock_layout)
            searchItemClickedInterface.profileClicked(this.getLayoutPosition());

    }
}
