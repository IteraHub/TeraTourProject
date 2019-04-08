package com.iterahub.teratour.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iterahub.teratour.R;
import com.iterahub.teratour.adapters.MessagesAdapter;
import com.iterahub.teratour.models.ChatsModel;
import com.iterahub.teratour.models.MessagesModel;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.Constants;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DirectMessageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dm_user_dp)
    ImageView userDp;
    @BindView(R.id.dm_user_name)
    TextView userName;
    @BindView(R.id.dm_edit_text)
    EditText messageEt;
    @BindView(R.id.send_dm_button)
    ImageButton sendBtn;
    @BindView(R.id.no_chats)
    TextView noChatsTv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.dm_recyler)
    RecyclerView recyclerView;


    UserModel userModel;
    AppViewModel appViewModel;
    PrefUtils prefUtils;
    MessagesAdapter messagesAdapter;
    ChatsModel chatsModel;
    List<MessagesModel> messagesModelList = new ArrayList<>();
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        prefUtils = new PrefUtils(this);

        toolbar.setNavigationIcon(R.drawable.ic_back_icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getDataFromIntent(getIntent());

        messageEt.requestFocus();
        toggleSoftKeyPad(true,messageEt);

        //layoutManager = new LinearLayoutManager(this);


    }

    private void getDataFromIntent(Intent intent){
        if(intent != null){
            userModel = Parcels.unwrap(intent.getParcelableExtra(Constants.USER_DATA));
            if(userModel != null){
                userName.setText(String.valueOf(userModel.getFirstname() + " " + userModel.getLastname()));
                ShowUtils.setImage(this,userDp,userModel.getImage_url());
                getChatsByUser(prefUtils.getUserId(),userModel.getId());
                prefUtils.setChattingUserId(userModel.getId());
            }
        }
    }

    @Override
    public void finish(){
        prefUtils.setChattingUserId("");
        super.finish();
    }

    private void getChatsByUser(String myUserId, String otherUserId){
        progressBar.setVisibility(View.VISIBLE);
        noChatsTv.setVisibility(View.GONE);
        appViewModel.getChatsByUser(myUserId,otherUserId).observe(this, chatsModel -> {
            if(chatsModel != null){
                this.chatsModel = chatsModel;
                //getMessageUpdate(chatsModel.getId());
                getMessages(chatsModel.getId());
            }else{
                progressBar.setVisibility(View.GONE);
                noChatsTv.setVisibility(View.VISIBLE);
//                if(this.chatsModel != null){
//                    getMessages(this.chatsModel.getId());
//                }else{
//                    progressBar.setVisibility(View.GONE);
//                    noChatsTv.setVisibility(View.VISIBLE);
//                }
            }
        });
    }

    private void getMessageUpdate(String chatId){
        appViewModel.getMessageUpdates(chatId).observe(this,messagesModel -> {
            progressBar.setVisibility(View.GONE);
            noChatsTv.setVisibility(View.GONE);
            if(messagesModel != null){
                messagesModelList.add(messagesModel);
                if(messagesAdapter == null){
                    messagesAdapter = new MessagesAdapter(prefUtils.getUserId(),messagesModelList);
                    recyclerView.setAdapter(messagesAdapter);
                }else{
                    messagesAdapter.addData(messagesModel);
                }
            }else{
                Log.e(this.toString(),"MessageModel is null");
            }
        });
    }

    private void getMessages(String chatId) {
        appViewModel.getMessages(chatId).observe(this, messagesModelList -> {
            progressBar.setVisibility(View.GONE);
            noChatsTv.setVisibility(View.GONE);
            if(messagesModelList != null && !messagesModelList.isEmpty()){
               this.messagesModelList = messagesModelList;
               messagesAdapter = new MessagesAdapter(prefUtils.getUserId(),messagesModelList);
                recyclerView.setAdapter(messagesAdapter);
            }else{
                noChatsTv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void validateChatExists(String myUserId,String otherUserId,
                             String message){

        if(this.chatsModel == null){
            appViewModel.getChatsByUser(myUserId,otherUserId).observe(this, chatsModel1 -> {
                if(chatsModel1 != null){
                    this.chatsModel = chatsModel1;
                    addMessages(myUserId,message,chatsModel1.getId());
                }else {
                    if (!TextUtils.isEmpty(otherUserId)) {
                        ChatsModel chatsModel = new ChatsModel();
                        chatsModel.setCreatedAt(new Date());
                        chatsModel.setLastMessage(message);
                        chatsModel.setMyUserId(myUserId);
                        chatsModel.setOtherUserId(otherUserId);
                        appViewModel.addChats(chatsModel).observe(this, isSuccessful -> {
                            if(isSuccessful != null && isSuccessful){
                                Log.e(getClass().getSimpleName(),"Chat created successfully");
                                addMessages(myUserId,message,chatsModel.getId()); 
                            }else{
                                Log.e(getClass().getSimpleName(),"Chat could not be created");
                            }
                        });
                    }else{
                        Toast.makeText(this, "Invalid user. Chat cannot be sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Log.e(getClass().getSimpleName(),"ChatModel is not null");
            addMessages(myUserId,message,this.chatsModel.getId());
        }
    }

    private void addMessages(String myUserId, String msg, String chatId){
        MessagesModel messagesModel = new MessagesModel();
        messagesModel.setChatId(chatId);
        messagesModel.setText(msg);
        messagesModel.setUserId(myUserId);
        appViewModel.addMessages(messagesModel).observe(this, isSuccessful -> {
           if(isSuccessful == null || !isSuccessful){
               Toast.makeText(this, "Message could not be sent. Try again later.", Toast.LENGTH_SHORT).show();
           }else{
               Log.e(getClass().getSimpleName(),"Message created successfully");
               if(this.messagesModelList == null || this.messagesModelList.isEmpty()){
                   //getMessageUpdate(chatId);
                   getMessages(chatId);
               }
           }
        });
    }

    private void toggleSoftKeyPad(boolean show,View view){
        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null){
            if(show){
                inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
            }else{
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
            }

        }
    }

    @OnClick(R.id.send_dm_button)
    public void onSend(View v){
        if(!TextUtils.isEmpty(messageEt.getText()) && userModel != null){
            validateChatExists(prefUtils.getUserId(),userModel.getId(),messageEt.getText().toString());
            messageEt.setText("");
            toggleSoftKeyPad(false,v);
        }
    }


}
