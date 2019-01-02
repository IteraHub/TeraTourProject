package com.iterahub.teratour.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.iterahub.teratour.models.ChatsModel;
import com.iterahub.teratour.models.CommentModel;
import com.iterahub.teratour.models.CommentModels;
import com.iterahub.teratour.models.CommentsOnPost;
import com.iterahub.teratour.models.LikeModel;
import com.iterahub.teratour.models.MarkerModel;
import com.iterahub.teratour.models.MediaModel;
import com.iterahub.teratour.models.MessagesModel;
import com.iterahub.teratour.models.PostModel;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.models.SearchResultDataModel;
import com.iterahub.teratour.models.User;
import com.iterahub.teratour.models.UserBody;
import com.iterahub.teratour.models.CommentsBody;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.repository.AppRepo;

import java.util.List;

/**
 * Created by Arinze on 11/8/2017.
 */

public class AppViewModel extends ViewModel {
    private AppRepo appRepo;
    private LiveData<MarkerModel> markerModelLiveData;
    private LiveData<PostModels> postModelLiveData;
    private LiveData<CommentsOnPost> commentLiveData;
    private MutableLiveData<List<PostModel>> postModelListLiveData;
    private LiveData<List<PostModel>> lip;
    private LiveData<List<CommentModel>> commentsListLiveData;
    private LiveData<ChatsModel> chatsModelLiveData;


    public AppViewModel() {
        this.appRepo = new AppRepo();
    }

    public LiveData<MarkerModel> getMarkerModelLiveData(String Id) {
        if (markerModelLiveData == null) {
            markerModelLiveData = appRepo.getMarkerDetails(Id);
        }
        return markerModelLiveData;
    }

    public LiveData<User> registerUser(UserBody userBody) {
        return appRepo.registerUser(userBody);
    }

    public LiveData<User.UserData> verifyUser(String auth) {
        return appRepo.verifyUser(auth);
    }

    public LiveData<PostModels> getPosts(String auth){
        if(postModelLiveData == null)
            postModelLiveData = appRepo.getPosts(auth);
        return postModelLiveData;
    }

    public LiveData<CommentsOnPost> getCommentLiveData(String auth, int postId) {
        if(commentLiveData == null)
            commentLiveData = appRepo.getCommentsOnPost(auth,postId);
        return commentLiveData;
    }

    public LiveData<LikeModel> likePost(String auth, int postId){
        return appRepo.likePost(auth,postId);
    }

    public LiveData<LikeModel> unLikePost(String auth, int postId){
        return appRepo.unLikePost(auth,postId);
    }

    public LiveData<CommentModels> postComments(String auth, int postId, CommentsBody commentsBody){
        return appRepo.postComments(auth,postId, commentsBody);
    }

    public  LiveData<SearchResultDataModel> search(String auth, String query){
        return  appRepo.search(auth,query);
    }

    //Firebase query start region


    public LiveData<Boolean> createNewUser(UserModel userModel){
        return appRepo.createUser(userModel);
    }

    public LiveData<UserModel> loginUser(String username, String password){
        return appRepo.loginUser(username,password);
    }

    public LiveData<UserModel> isUsernameUnique(String username){
        return appRepo.isUserNameUnique(username);
    }

    public LiveData<UserModel> isEmailUnique(String email){
        return appRepo.isEmailUnique(email);
    }

    public LiveData<List<PostModel>> getAllPosts(boolean refresh){
        if(!refresh) {
            if (lip == null) {
                lip = appRepo.getAllPosts();
            }
        }else{
            lip = appRepo.getAllPosts();
        }
        return lip;
//            if(postModelListLiveData == null){
//                postModelListLiveData = new MutableLiveData<>();
//                postModelListLiveData.setValue(appRepo.getAllPosts().getValue());
//            }
//        }else{
//            postModelListLiveData.setValue(appRepo.getAllPosts().getValue());
//        }
//        return postModelListLiveData;
    }

    public LiveData<List<PostModel>> getPostsByUserId(String userId, boolean fromCache, boolean refresh){
        if(!refresh){
            if(lip == null){
                lip = appRepo.getPostsByUserId(userId,fromCache);
            }
        }else{
            lip = appRepo.getPostsByUserId(userId,fromCache);
        }
        return lip;
    }

    public void setPosts(List<PostModel> postModelList){
        if(postModelListLiveData == null){
            postModelListLiveData = new MutableLiveData<>();
            postModelListLiveData.setValue(postModelList);
        }else{
            postModelListLiveData.setValue(postModelList);
        }
    }

    public LiveData<Boolean> addAllPosts(PostModel postModel){
        return appRepo.addPost(postModel);
    }

    public LiveData<UserModel> getUserById(String id){
        return appRepo.getUserById(id);
    }

    public LiveData<CommentModel> getLatestComment(String postId){
        return appRepo.getLatestComments(postId);
    }

    public LiveData<List<CommentModel>> getAllComments(String postId, boolean refresh){
        if(!refresh){
            if(commentsListLiveData == null){
                commentsListLiveData = appRepo.getAllComments(postId);
            }
        }else {
            commentsListLiveData = appRepo.getAllComments(postId);
        }
        return commentsListLiveData;
    }

    public LiveData<List<CommentModel>> getCommentsUpdates(String postId){
        return appRepo.getCommentUpdates(postId);
    }

    public LiveData<Boolean> addComments(CommentModel commentModel){
        return appRepo.addComments(commentModel);
    }

    public LiveData<MediaModel> getMedia(String postId){
        return appRepo.getMedia(postId);
    }

    public LiveData<List<ChatsModel>> getAllChats(String userId){
        return appRepo.getAllChatsUpdates(userId);
    }

    public LiveData<Boolean> addChats(ChatsModel chatsModel){
        return appRepo.addChats(chatsModel);
    }

    public LiveData<ChatsModel> getChatsByUser(String myUserId, String otherUserId){
//        if(chatsModelLiveData == null){
//            chatsModelLiveData = appRepo.getChatByUsers(myUserId,otherUserId);
//        }
//        return chatsModelLiveData;
        return appRepo.getChatByUsers(myUserId,otherUserId);
    }

    public LiveData<Boolean> addMessages(MessagesModel messagesModel){
        return appRepo.addMessage(messagesModel);
    }

    public LiveData<List<MessagesModel>> getMessages(String chatId){
        return appRepo.getMessages(chatId);
    }



    //Firebase query end region
}
