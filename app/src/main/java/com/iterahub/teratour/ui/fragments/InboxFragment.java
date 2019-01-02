package com.iterahub.teratour.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iterahub.teratour.R;
import com.iterahub.teratour.adapters.ChatsAdapter;
import com.iterahub.teratour.interfaces.InboxItemClickedInterface;
import com.iterahub.teratour.models.ChatsModel;
import com.iterahub.teratour.ui.activities.DirectMessageActivity;
import com.iterahub.teratour.utils.Constants;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InboxFragment extends Fragment implements InboxItemClickedInterface{

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.inbox_recycler)
    RecyclerView inboxRv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;

    private List<ChatsModel> chatsModelsList;
    private PrefUtils prefUtils;
    private AppViewModel appViewModel;
    private ChatsAdapter chatsAdapter;

    public InboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
      prefUtils = new PrefUtils(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this,view);

        loadChats(prefUtils.getUserId());

        return view;
    }

    private void loadChats(String userId){
        progressBar.setVisibility(View.VISIBLE);
        noDataTv.setVisibility(View.GONE);
        appViewModel.getAllChats(userId).observe(this, chatsModels -> {
            progressBar.setVisibility(View.GONE);
            noDataTv.setVisibility(View.GONE);
            if(chatsModels != null && !chatsModels.isEmpty()){
                chatsModelsList = chatsModels;
                chatsAdapter = new ChatsAdapter(getActivity(),chatsModels,this);
                inboxRv.setAdapter(chatsAdapter);
                String id;
                for(ChatsModel cm : chatsModels){
                    id = cm.getOtherUserId();
                    if(cm.getOtherUserId().equals(userId)){
                        id = cm.getMyUserId();
                    }
                    appViewModel.getUserById(id).observe(this, userModel -> {
                        if(userModel != null){
                            chatsAdapter.addChatOwner(userModel, prefUtils.getUserId());
                        }
                    });
                }
            }else{
                noDataTv.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void itemClicked(int position) {
        if(chatsAdapter != null && chatsAdapter.getChatOwner(position) != null){
            Intent intent = new Intent(getActivity(),DirectMessageActivity.class);
            intent.putExtra(Constants.USER_DATA, Parcels.wrap(chatsAdapter.getChatOwner(position)));
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "Invalid chat selected.", Toast.LENGTH_SHORT).show();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}