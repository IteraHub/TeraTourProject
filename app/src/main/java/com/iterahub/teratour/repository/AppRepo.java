package com.iterahub.teratour.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.iterahub.teratour.TeraTour;
import com.iterahub.teratour.comparators.CommentsComparator;
import com.iterahub.teratour.comparators.MessagesComparator;
import com.iterahub.teratour.database.DatabaseInitializer;
import com.iterahub.teratour.logics.AccountLogics;

import com.iterahub.teratour.models.ChatsModel;
import com.iterahub.teratour.models.CommentModel;
import com.iterahub.teratour.models.CommentModels;
import com.iterahub.teratour.models.CommentsOnPost;
import com.iterahub.teratour.models.LikeModel;
import com.iterahub.teratour.models.MarkerModel;
import com.iterahub.teratour.api_service.WebService;
import com.iterahub.teratour.models.MediaModel;
import com.iterahub.teratour.models.MessagesModel;
import com.iterahub.teratour.models.PostModel;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.models.SearchResultDataModel;
import com.iterahub.teratour.models.User;
import com.iterahub.teratour.models.UserBody;
import com.iterahub.teratour.models.CommentsBody;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arinze on 11/8/2017.
 */

public class AppRepo {
    private WebService webservice;
    private static final String BASE_URL = "http://teratour.herokuapp.com/";

    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    private static String USER_DETAILS_TABLE = "USER_DETAILS";
    private static String POST_DETAILS_TABLE = "POST_DETAILS";
    private static String MEDIA_DETAILS_TABLE = "MEDIA_DETAILS";
    private static String COMMENT_DETAILS_TABLE = "COMMENTS_DETAILS";
    private static String CHATS_DETAILS_TABLE = "CHAT_DETAILS";
    private static String MESSAGE_DETAILS_TABLE = "MESSAGE_DETAILS";


    public AppRepo(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder  httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new NetworkInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build();
        webservice = retrofit.create(WebService.class);
    }

    public LiveData<MarkerModel> getMarkerDetails(String Id){
        final MutableLiveData<MarkerModel> MarkerData = new MutableLiveData<>();
        webservice.getMarkers(Id).enqueue(new Callback<MarkerModel>() {
            @Override
            public void onResponse(Call<MarkerModel> call, Response<MarkerModel> response) {
                MarkerData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MarkerModel> call, Throwable t) {
                AccountLogics.logMsg(this.getClass(),t.getMessage());
            }
        });
        return MarkerData;
    }

    public LiveData<User> registerUser(UserBody userBody){
        final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        webservice.RegisterUser(userBody).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                if(response.isSuccessful()){
                    userMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {

            }
        });
        return userMutableLiveData;
    }

    public LiveData<User.UserData> verifyUser(String auth){
        auth = "Basic " + auth;
        AccountLogics.logMsg(this.getClass(),auth);
        final MutableLiveData<User.UserData> userMutableLiveData = new MutableLiveData<>();
        webservice.VerifyUser(auth).enqueue(new Callback<User.UserData>() {
            @Override
            public void onResponse(@NonNull Call<User.UserData> call,@NonNull Response<User.UserData> response) {
                if(response.isSuccessful()){
                    userMutableLiveData.postValue(response.body());
                }else if(response.code() == 401){

                }
            }

            @Override
            public void onFailure(@NonNull Call<User.UserData> call,@NonNull Throwable t) {

            }
        });
        return userMutableLiveData;
    }

    public LiveData<PostModels> getPosts(String auth){
        auth = "Basic "+ auth;
        final MutableLiveData<PostModels> postModelMutableLiveData = new MutableLiveData<>();
        if(NetworkUtils.isNetworkAvailable()){
            webservice.getPosts(auth).enqueue(new Callback<PostModels>() {
                @Override
                public void onResponse(@NonNull Call<PostModels> call, @NonNull Response<PostModels> response) {
                    if(response.body() != null){
                        postModelMutableLiveData.postValue(response.body());
                        DatabaseInitializer.addPostsAsync(TeraTour.getAppDatabase(), Objects.requireNonNull(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PostModels> call, @NonNull Throwable t) {

                }
            });
        }else{
            DatabaseInitializer.readPostsAsync(TeraTour.getAppDatabase(), posts -> {
                if(posts != null)
                    postModelMutableLiveData.postValue(posts);
            });
        }

       return postModelMutableLiveData;
    }

    public LiveData<CommentsOnPost> getCommentsOnPost(String auth, int postId){
        auth = "Basic "+ auth;
        final MutableLiveData<CommentsOnPost> CommentsMutableLiveData =
                new MutableLiveData<>();
        if(NetworkUtils.isNetworkAvailable()){
            //call from the server
            webservice.getComments(auth,postId).enqueue(new Callback<CommentsOnPost>() {
                @Override
                public void onResponse(@NonNull Call<CommentsOnPost> call,
                                       @NonNull Response<CommentsOnPost> response) {
                    CommentsOnPost commentsOnPost = response.body();
                    if(response.isSuccessful() && commentsOnPost != null){
                        CommentsMutableLiveData.postValue(commentsOnPost);

                        //Add posts to db
                        if(commentsOnPost.getCommentData() != null)
                        DatabaseInitializer.addCommentsArraylistAsync(TeraTour.getAppDatabase(),commentsOnPost.getCommentData());
                    }
                }

                @Override
                public void onFailure(Call<CommentsOnPost> call, Throwable t) {

                }
            });
        }else{
            //call from the db
        }
        return CommentsMutableLiveData;
    }

    public LiveData<CommentModels> postComments(String auth, int postId, CommentsBody commentsBody){
        auth = "Basic " + auth;
        final MutableLiveData<CommentModels> mutableLiveData = new MutableLiveData<>();
        webservice.postComments(auth,postId, commentsBody).enqueue(new Callback<CommentModels>() {
            @Override
            public void onResponse(@NonNull Call<CommentModels> call, @NonNull Response<CommentModels> response) {
                if(response.isSuccessful() && response.body() != null){
                    CommentModels commentModels = response.body();
                    mutableLiveData.postValue(commentModels);
                    if(commentModels != null && commentModels.getData() != null){
                        PostModels.PostData.LatestComment latestComment = commentModels.getData();
                        DatabaseInitializer.addCommentsAsync(TeraTour.getAppDatabase(),latestComment);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentModels> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<LikeModel> likePost(String auth, int postId){
        auth = "Basic " + auth;
        final MutableLiveData<LikeModel> likeModelMutableLiveData = new MutableLiveData<>();
        webservice.likePost(auth,postId).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(@NonNull Call<LikeModel> call,@NonNull Response<LikeModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null)
                        likeModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LikeModel> call,@NonNull Throwable t) {

            }
        });
        return likeModelMutableLiveData;
    }

    public LiveData<LikeModel> unLikePost(String auth, int postId){
        auth = "Basic " + auth;
        final MutableLiveData<LikeModel> unLikeModelMutableLiveData = new MutableLiveData<>();
        webservice.unLikePost(auth,postId).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(@NonNull Call<LikeModel> call,@NonNull Response<LikeModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null)
                        unLikeModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LikeModel> call,@NonNull Throwable t) {

            }
        });
        return unLikeModelMutableLiveData;
    }

    public  LiveData<SearchResultDataModel> search(String auth, String query){
        auth = "Basic " + auth;
        final MutableLiveData<SearchResultDataModel> searchResultDataModelMutableLiveData = new
                MutableLiveData<>();
        webservice.search(auth,query).enqueue(new Callback<SearchResultDataModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchResultDataModel> call,@NonNull Response<SearchResultDataModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null)
                        searchResultDataModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResultDataModel> call,@NonNull Throwable t) {

            }
        });
        return  searchResultDataModelMutableLiveData;
    }

    //Firebase start region

    public LiveData<Boolean> createUser(UserModel userModel){
        final MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        firestoreDB.collection(USER_DETAILS_TABLE).document(userModel.getId()).set(userModel)
                .addOnSuccessListener(docRef -> isSuccessful.postValue(true))
                .addOnFailureListener(exception -> {
                    isSuccessful.postValue(false);
                    Log.e(this.getClass().getSimpleName(),"Error adding data >> ",exception);
                });
        return isSuccessful;
    }

    public LiveData<UserModel> isUserNameUnique(String username){
        final MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
        firestoreDB.collection(USER_DETAILS_TABLE).whereEqualTo("username",username)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(task.getResult() == null || task.getResult().getDocuments().isEmpty()){
                            userModelMutableLiveData.postValue(null);
                        }else{
                            QuerySnapshot doc = task.getResult();
                            userModelMutableLiveData.postValue(doc.getDocuments().get(0).toObject(UserModel.class));
                        }
                    }
                });
        return userModelMutableLiveData;
    }

    public LiveData<UserModel> isEmailUnique(String email){
        final MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
        firestoreDB.collection(USER_DETAILS_TABLE).whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot doc = task.getResult();
                        if(doc == null || doc.getDocuments().isEmpty()){
                            userModelMutableLiveData.postValue(null);
                            return;
                        }
                        userModelMutableLiveData.postValue(doc.getDocuments().get(0).toObject(UserModel.class));
                    }else{
                        userModelMutableLiveData.postValue(null);
                        Log.e(this.getClass().getSimpleName(),"Error >> ", task.getException());
                    }
                });
        return userModelMutableLiveData;
    }

    public LiveData<UserModel> loginUser(String username, String password){
        MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
        firestoreDB.collection(USER_DETAILS_TABLE)
                .whereEqualTo("password",password)
                .whereEqualTo("username",username)
                .get()
                .addOnSuccessListener(data -> {
                    if(data.getDocuments().isEmpty()){
                       userModelMutableLiveData.postValue(null);
                       return;
                    }
                    userModelMutableLiveData.postValue(data.getDocuments().get(0).toObject(UserModel.class));
                })
                .addOnFailureListener(error -> {
                    userModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"error >> " + error);
                });
        return userModelMutableLiveData;
    }

    public LiveData<List<PostModel>> getAllPosts(){
        final MutableLiveData<List<PostModel>> postModelMutableLiveData = new MutableLiveData<>();
        List<PostModel> postModelsList = new ArrayList<>();
        firestoreDB.collection(POST_DETAILS_TABLE)
                .orderBy("created_at", Query.Direction.DESCENDING)
                .limit(50).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot doc : querySnapshot) {
                            postModelsList.add(doc.toObject(PostModel.class));
                        }
                        postModelMutableLiveData.postValue(postModelsList);
//                        if(querySnapshot != null && !querySnapshot.isEmpty()){
//                            for (QueryDocumentSnapshot doc :querySnapshot) {
//                                postModelsList.add(doc.toObject(PostModel.class));
//                            }
//                            postModelMutableLiveData.postValue(postModelsList);
//                        }else{
//                            postModelMutableLiveData.postValue(null);
//                        }
                    }else{
                        postModelMutableLiveData.postValue(null);
                        Log.e(this.getClass().getSimpleName(),"Error >>", task.getException());
                    }
                });
        return postModelMutableLiveData;
    }

    public LiveData<List<PostModel>> getPostsByUserId(String userId, boolean fromCache){
        final MutableLiveData<List<PostModel>> postMutableLiveData = new MutableLiveData<>();
        List<PostModel> postModelList = new ArrayList<>(); Source source;
        if(fromCache){
            source = Source.CACHE;
        }else{
            source = Source.SERVER;
        }
        firestoreDB.collection(POST_DETAILS_TABLE).whereEqualTo("user_Id",userId)
                .orderBy("created_at",Query.Direction.DESCENDING).get()
                .addOnSuccessListener( data -> {
                    for(QueryDocumentSnapshot qs : data){
                        postModelList.add(qs.toObject(PostModel.class));
                    }
                    postMutableLiveData.postValue(postModelList);
                })
                .addOnFailureListener(error -> {
                    postMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ", error);
                });
        return postMutableLiveData;
    }

    public LiveData<Boolean> addPost(PostModel postModel){
        final MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        MediaModel mediaModel = postModel.getMediaModel();
        CommentModel commentModel = postModel.getLatestComment();
        UserModel userModel = postModel.getPostOwner();
        postModel.setMediaModel(null);
        postModel.setLatestComment(null );
        postModel.setPostOwner(null);
        firestoreDB.collection(POST_DETAILS_TABLE).document(postModel.getId())
                .set(postModel)
                .addOnSuccessListener(success -> {
                    isSuccessful.postValue(true);
                    firestoreDB.collection(MEDIA_DETAILS_TABLE).document(mediaModel.getId()).set(mediaModel);
                    firestoreDB.collection(COMMENT_DETAILS_TABLE).document(commentModel.getId()).set(commentModel);
                    firestoreDB.collection(USER_DETAILS_TABLE).document(userModel.getId()).set(userModel);
                })
                .addOnFailureListener(error -> {
                    isSuccessful.postValue(false);
                    Log.e(this.getClass().getSimpleName(),"Error >> ", error);
                });

        return isSuccessful;
    }

    public LiveData<MediaModel> getMedia(String postId){
        final MutableLiveData<MediaModel> mediaModelMutableLiveData = new MutableLiveData<>();
        firestoreDB.collection(MEDIA_DETAILS_TABLE).whereEqualTo("post_id",postId).get()
                .addOnSuccessListener(data ->
                        mediaModelMutableLiveData.postValue(data.getDocuments().get(0)
                                .toObject(MediaModel.class)))
                .addOnFailureListener(error -> {
                    mediaModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });
        return mediaModelMutableLiveData;
    }

    public LiveData<CommentModel> getLatestComments(String postId){
        final MutableLiveData<CommentModel> commentModelMutableLiveData = new MutableLiveData<>();
        firestoreDB.collection(COMMENT_DETAILS_TABLE).whereEqualTo("post_id",postId)
                .orderBy("created_at",Query.Direction.DESCENDING).get()
                .addOnSuccessListener(data -> {
                    commentModelMutableLiveData.postValue(data.getDocuments().get(0).toObject(CommentModel.class));
                })
                .addOnFailureListener(error -> {
                    commentModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });
        return commentModelMutableLiveData;
    }

    public LiveData<List<CommentModel>> getAllComments(String postId){
        final MutableLiveData<List<CommentModel>> commentModelMutableLiveData = new MutableLiveData<>();
        List<CommentModel> commentModelsList = new ArrayList<>();
        firestoreDB.collection(COMMENT_DETAILS_TABLE).whereEqualTo("post_id",postId)
                .orderBy("created_at",Query.Direction.DESCENDING).get()
                .addOnSuccessListener(data -> {
                    for(QueryDocumentSnapshot qs : data){
                        commentModelsList.add(qs.toObject(CommentModel.class));
                    }
                    commentModelMutableLiveData.postValue(commentModelsList);
                })
                .addOnFailureListener(error -> {
                    commentModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });
        return commentModelMutableLiveData;
    }

    public LiveData<List<CommentModel>> getCommentUpdates(String postId){
        final MutableLiveData<List<CommentModel>> commentMutableLivedata = new MutableLiveData<>();
        List<CommentModel> commentModelsList = new ArrayList<>();
        firestoreDB.collection(COMMENT_DETAILS_TABLE).whereEqualTo("post_id",postId)
                .orderBy("created_at",Query.Direction.ASCENDING)
                .addSnapshotListener(( querySnapshots, e) -> {
                    if(querySnapshots != null){
                        for (QueryDocumentSnapshot qs : querySnapshots) {
                            commentModelsList.add(qs.toObject(CommentModel.class));
                        }

                        commentMutableLivedata.postValue(filterOutDuplicates(commentModelsList));
                    }else{
                        commentMutableLivedata.postValue(null);
                    }
                    if(e != null){
                        Log.e(this.getClass().getSimpleName()," Error >> ",e);
                    }

                });
        return commentMutableLivedata;
    }

    public LiveData<Boolean> addComments (CommentModel commentModel){
        final MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        if(commentModel.getComment_owner() != null){
            commentModel.setComment_owner(null);
        }
        firestoreDB.collection(COMMENT_DETAILS_TABLE).document(commentModel.getId()).set(commentModel)
                .addOnSuccessListener(success -> isSuccessful.postValue(true))
                .addOnFailureListener(error -> {
                    isSuccessful.postValue(false);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });
        return isSuccessful;
    }

    public LiveData<UserModel> getUserById(String userId){
        final MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
        firestoreDB.collection(USER_DETAILS_TABLE).whereEqualTo("id",userId).get()
                .addOnSuccessListener(data -> {
                    if(data != null && !data.getDocuments().isEmpty()){
                        userModelMutableLiveData.postValue(data.getDocuments()
                                .get(0).toObject(UserModel.class));
                    }
                }
                )
                .addOnFailureListener( error -> {
                    userModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });
        return userModelMutableLiveData;
    }

    public LiveData<List<ChatsModel>> getAllChatsUpdates(String userId){
        final MutableLiveData<List<ChatsModel>> chatsMutableLivedata = new MutableLiveData<>();
        List<ChatsModel> chatsModelList = new ArrayList<>();
        firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("otherUserId",userId)
                .orderBy("createdAt",Query.Direction.DESCENDING)
                .addSnapshotListener((data, error) ->{
                    if(data != null){
                        for (QueryDocumentSnapshot qs : data) {
                            chatsModelList.add(qs.toObject(ChatsModel.class));
                        }
                        chatsMutableLivedata.postValue(chatsModelList);
                    }else{
                        chatsMutableLivedata.postValue(null);
                    }
                    if(error != null){
                        chatsMutableLivedata.postValue(null);
                        Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                    }
                });
        firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("myUserId",userId)
                .orderBy("createdAt",Query.Direction.DESCENDING)
                .addSnapshotListener((data, error) ->{
                    if(data != null){
                        for (QueryDocumentSnapshot qs : data) {
                            chatsModelList.add(qs.toObject(ChatsModel.class));
                        }
                        chatsMutableLivedata.postValue(chatsModelList);
                    }else{
                        chatsMutableLivedata.postValue(null);
                    }
                    if(error != null){
                        chatsMutableLivedata.postValue(null);
                        Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                    }
                });
        return chatsMutableLivedata;
    }

    public LiveData<Boolean> addChats(ChatsModel chatsModel){
        final MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        firestoreDB.collection(CHATS_DETAILS_TABLE).document(chatsModel.getId()).set(chatsModel)
                .addOnSuccessListener( success -> isSuccessful.postValue(true))
                .addOnFailureListener( error -> {
                    isSuccessful.postValue(false);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });
        return isSuccessful;
    }

    public LiveData<ChatsModel> getChatByUsers(String myUserId, String otherUserId){
        final MutableLiveData<ChatsModel> chatsModelMutableLiveData = new MutableLiveData<>();

        firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("myUserId",myUserId)
                .whereEqualTo("otherUserId",otherUserId).get()
                .addOnSuccessListener(data ->{
                    if(data != null && !data.getDocuments().isEmpty()){
                        chatsModelMutableLiveData.setValue(data.getDocuments().get(0).toObject(ChatsModel.class));
                    }else{
                        if(chatsModelMutableLiveData.getValue() == null)
                            chatsModelMutableLiveData.postValue(null);
                    }
                })
                .addOnFailureListener(error -> {
                    chatsModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });

        firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("myUserId",otherUserId)
                .whereEqualTo("otherUserId",myUserId).get()
                .addOnSuccessListener(data ->{
                    if(data != null && !data.getDocuments().isEmpty()){
                        chatsModelMutableLiveData.postValue(data.getDocuments().get(0).toObject(ChatsModel.class));
                    }else{
                        if(chatsModelMutableLiveData.getValue() == null)
                        chatsModelMutableLiveData.postValue(null);
                    }
                })
                .addOnFailureListener(error -> {
                    chatsModelMutableLiveData.postValue(null);
                    Log.e(this.getClass().getSimpleName(),"Error >> ",error);
                });

        return chatsModelMutableLiveData;
    }

    public LiveData<Boolean> addMessage(MessagesModel messagesModel){
        final MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        DatabaseReference ref = mFirebaseDatabase.getReference(MESSAGE_DETAILS_TABLE);
        ref.child(messagesModel.getChatId()).child(messagesModel.getId()).setValue(messagesModel)
                .addOnSuccessListener(data -> isSuccessful.postValue(true))
                .addOnFailureListener(error -> {
                    isSuccessful.postValue(false);
                    Log.e(getClass().getSimpleName(),"Error >>",error);
                });
        return isSuccessful;
    }

    public LiveData<List<MessagesModel>> getMessages(String chatId){
        final MutableLiveData<List<MessagesModel>> msgMutableLiveData = new MutableLiveData<>();
        List<MessagesModel> messagesModelList = new ArrayList<>();
        DatabaseReference ref = mFirebaseDatabase.getReference(MESSAGE_DETAILS_TABLE).child(chatId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        messagesModelList.add(ds.getValue(MessagesModel.class));
                    }
                    msgMutableLiveData.postValue(filterOutDuplicatesMsg(messagesModelList));
                }else{
                    msgMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                msgMutableLiveData.postValue(null);
                Log.e(getClass().getSimpleName()," Error >> " + databaseError.getMessage());
            }
        });
        return msgMutableLiveData;
    }

//    public LiveData<List<UserModel>> getUsers(String searchText){
//        final MutableLiveData<List<UserModel>> userMutableLiveData = new MutableLiveData<>();
//    }

    private List<CommentModel> filterOutDuplicates(List<CommentModel> commentModelList){
        Set<CommentModel> comments = new TreeSet<>(new CommentsComparator());
        comments.addAll(commentModelList);
        commentModelList.clear();
        commentModelList.addAll(comments);
        return commentModelList;
    }

    private List<MessagesModel> filterOutDuplicatesMsg(List<MessagesModel> messagesModelList){
        Set<MessagesModel> messagesModels = new TreeSet<>(new MessagesComparator());
        messagesModels.addAll(messagesModelList);
        messagesModelList.clear();
        messagesModelList.addAll(messagesModels);
        Collections.sort(messagesModelList, (msg1, msg2) -> {
            if(msg1.getCreatedAt() == null || msg2.getCreatedAt() == null)
                return 0;
           return  msg1.getCreatedAt().compareTo(msg2.getCreatedAt());
        });
        return messagesModelList;
    }


    //Firebase end region
}
