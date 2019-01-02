package com.iterahub.teratour.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iterahub.teratour.R;
import com.iterahub.teratour.adapters.CommentsAdapter;
import com.iterahub.teratour.interfaces.UserButtonsClickedInterface;
import com.iterahub.teratour.models.CommentModel;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommentsFragment extends Fragment implements UserButtonsClickedInterface {


    @BindView(R.id.comments_rv)
    RecyclerView commentRv;
    @BindView(R.id.comment_edit_text)
    EditText commentEt;
    @BindView(R.id.send_comment_button)
    ImageButton sendBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_text)
    TextView emptyTv;

    PrefUtils prefUtils;
    AppViewModel appViewModel;
    CommentsAdapter commentsAdapter;

    private static String POST_ID = "POST_ID";
    private String postId;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance(String postId){
        CommentsFragment frag = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(POST_ID,postId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        prefUtils = new PrefUtils(getActivity());
        if(getArguments() != null){
            postId = getArguments().getString(POST_ID);
        }
    }

    private void getAllComments(String postId, boolean refresh){
        appViewModel.getCommentsUpdates(postId).observe(this, commentModels -> {
            progressBar.setVisibility(View.GONE);
            if(commentModels != null){
                commentsAdapter = new CommentsAdapter(getActivity(),commentModels,this);
                commentRv.setAdapter(commentsAdapter);
                for(int i = 0; i < commentModels.size(); i++){
                    appViewModel.getUserById(commentModels.get(i).getUser_id()).observe(this, userModel -> {
                        if(userModel != null){
                            commentsAdapter.addCommentOwner(userModel);
                        }
                    });
                }
            }
        });
    }


    private void toggleSoftKeyPad(boolean show,View view){
        if(getActivity() != null){
            InputMethodManager inputMethodManager =
                    (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager != null){
                if(show){
                    inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
                }else{
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                }

            }
        }

    }

    private void sendComment(String text,String postId){
        CommentModel commentModel = new CommentModel();
        commentModel.setUpdated_at(new Date());
        commentModel.setCreated_at(new Date());
        commentModel.setUser_id(prefUtils.getUserId());
        commentModel.setPost_id(postId);
        commentModel.setComment_text(text);
        appViewModel.addComments(commentModel).observe(this, isSuccessful -> {
            if(isSuccessful != null && isSuccessful)
            Log.e(this.getClass().getSimpleName()," comment added succesfully");
        });
    }

    @OnClick(R.id.send_comment_button)
    public void send(View view){
        if(!TextUtils.isEmpty(commentEt.getText())){
            sendComment(commentEt.getText().toString(),postId);
            commentEt.setText("");
            toggleSoftKeyPad(false,view);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this,v);

        getAllComments(postId,false);

        return v;
    }

    @Override
    public void onUsernameClick(int position) {

    }

    @Override
    public void onUserImageClick(int position) {

    }
}
