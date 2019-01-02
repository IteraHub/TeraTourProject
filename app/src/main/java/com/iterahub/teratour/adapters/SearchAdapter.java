package com.iterahub.teratour.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iterahub.teratour.R;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.viewHolders.SearchViewHolders;
import com.iterahub.teratour.interfaces.SearchItemClickedInterface;
import com.iterahub.teratour.models.User;
import com.iterahub.teratour.utils.ShowUtils;

import java.util.List;

/**
 * Created by arinzedroid on 9/2/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolders> {

//    private SearchResultDataModel.SearchData searchData;
    private SearchItemClickedInterface searchItemClickedInterface;
    private List<Object> data; private Context context;

//    public SearchAdapter(SearchResultDataModel.SearchData searchData,
//                          SearchItemClickedInterface searchItemClickedInterface){
//        this.searchData = searchData;
//        this.searchItemClickedInterface = searchItemClickedInterface;
//    }
    public  SearchAdapter(Context context, List<Object> data, SearchItemClickedInterface searchItemClickedInterface){
        this.data = data;
        this.context = context;
        this.searchItemClickedInterface = searchItemClickedInterface;
    }

    @NonNull
    @Override
    public SearchViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items,parent,false);
        return new SearchViewHolders(v,searchItemClickedInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolders holder, int position) {
        if(data.get(position) instanceof User.UserData){
            User.UserData datum = (User.UserData) data.get(position);
            ShowUtils.setImage(context,holder.profilePic,datum.getImageUrl(), R.drawable.default_image);
            holder.profileDesc.setText(datum.getAbout());
            holder.profileName.setText(String.valueOf(datum.getFirstname() + " " + datum.getLastname()));
        }else if(data.get(position) instanceof PostModels.PostData){
            PostModels.PostData datum = (PostModels.PostData) data.get(position);
            ShowUtils.setImage(context,holder.profilePic,datum.getPostMedia() != null ?
                            datum.getPostMedia().getmediaUrl():"",R.drawable.default_image);
            holder.profileDesc.setText(datum.getText());
            holder.profileName.setText(datum.getUser() != null ?
                    String.valueOf(datum.getUser().getFirstname() + " " + datum.getUser().getLastname()): "");
        }
    }

    @Override
    public int getItemCount() {
//        int postCount = 0, userCount = 0;
//        if(searchData != null && searchData.getPostDataList() != null)
//             postCount = searchData.getPostDataList().size();
//        if(searchData != null && searchData.getUserDataList() != null)
//            userCount = searchData.getUserDataList().size();
//        return userCount + postCount;
        return data.size();
    }
}
