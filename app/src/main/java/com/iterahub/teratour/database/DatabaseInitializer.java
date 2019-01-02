package com.iterahub.teratour.database;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.iterahub.teratour.interfaces.PostFromDbInterface;
import com.iterahub.teratour.interfaces.UserDataFromDbInterface;
import com.iterahub.teratour.models.DummyUser;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.models.User;

import java.util.ArrayList;

/**
 * Created by ACER on 2/4/2018.
 */

public class DatabaseInitializer {
    static String TAG = DatabaseInitializer.class.getSimpleName();

    public static void addUserAsync(@NonNull final AppDatabase appDatabase, DummyUser user){
        AddUsersAsync addUsersAsync = new AddUsersAsync(appDatabase,user);
        addUsersAsync.execute();
    }

    public static void addCommentsArraylistAsync(@NonNull AppDatabase appDatabase,
                                   ArrayList<PostModels.PostData.LatestComment> latestCommentArrayList){
        AddCommentsArrayListAsync task = new AddCommentsArrayListAsync(appDatabase,latestCommentArrayList);
        task.execute();
    }

    public static void updatePostLike(@NonNull final AppDatabase appDatabase,
                                      @NonNull PostModels.PostData postData){
        UpdatePostLikeAsync task = new UpdatePostLikeAsync(appDatabase,postData);
        task.execute();
    }

    public static void addUserDataAsync(@NonNull final AppDatabase appDatabase,
                                        @NonNull User.UserData userData){
        AddUserDataAsync task = new AddUserDataAsync(appDatabase,userData);
        task.execute();
    }

    public static void getUserDataAsyc(@NonNull final AppDatabase appDatabase,
                                       @NonNull final UserDataFromDbInterface userDataFromDbInterface){
        GetUserDataAsync task = new GetUserDataAsync(appDatabase,userDataFromDbInterface);
        task.execute();
    }

    public static void addCommentsAsync(@NonNull final AppDatabase appDatabase,
                                        @NonNull final PostModels.PostData.LatestComment comment){
        AddCommentsAsync task = new AddCommentsAsync(appDatabase,comment);
        task.execute();
    }

    public static void addPostsAsync(@NonNull final AppDatabase appDatabase, @NonNull PostModels postModels){
        AddPostsAsync task = new AddPostsAsync(appDatabase, postModels);
        task.execute();
    }

    public static void readPostsAsync(@NonNull final AppDatabase appDatabase,
                                      @NonNull PostFromDbInterface postFromDbInterface){
        ReadPostsAsync task = new ReadPostsAsync(appDatabase,postFromDbInterface);
        task.execute();
    }

    public static void readPostsByOwnerAsync(@NonNull final AppDatabase appDatabase, final int userId,
                                             @NonNull final PostFromDbInterface postFromDbInterface){
        ReadPostsByOwnerAsync task = new ReadPostsByOwnerAsync(appDatabase,userId,postFromDbInterface);
        task.execute();
    }

    private static void addUser(final AppDatabase db, DummyUser user){
        for (DummyUser.DummyUserData data : user.getDummyUserData()) {
            db.userDao().InsertUserData(data);
        }
        int count = db.userDao().getCount();
        Log.e(TAG,"Count: "+ count);
    }

    private static void addPosts(final AppDatabase appDatabase, PostModels postModels){

        if(postModels != null && postModels.getData() != null){
            ArrayList<PostModels.PostData> data = postModels.getData();
            for(int i = 0; i < data.size(); i++){
                PostModels.PostData.PostOwner postOwner = data.get(i).getUser();
                PostModels.PostData.Media media = data.get(i).getPostMedia();
                PostModels.PostData.LatestComment comments = data.get(i).getLatestComment();

                //check if comments_view is not null then add to db
                if(comments != null && comments.getUser() != null){
                    PostModels.PostData.LatestComment.CommentOwner owner = comments.getUser();
                    owner.setCommentId(comments.getId());
                    appDatabase.postDao().InsertCommentOwner(owner);
                    appDatabase.postDao().InsertPostComments(comments);
                }

                //check if postOwner is not null then add to db
                if(postOwner != null)
                    postOwner.setPostId(data.get(i).getId());
                appDatabase.postDao().InsertPostOwner(postOwner);
                if(media != null)
                    //media.setPostId(data.get(i).getId());
                    appDatabase.postDao().InsertPostMedia(media);
                if(data.get(i) != null)
                    appDatabase.postDao().InsertPostData(data.get(i));
            }
        }

    }

    private static void addUserData(final AppDatabase appDatabase, final User.UserData userData){
        if(userData != null){
            appDatabase.userDao().InsertUser(userData);
        }
    }

    private static void getUserData(final AppDatabase appDatabase, final UserDataFromDbInterface
            userDataFromDbInterface){
        int count = appDatabase.userDao().getUserCount();
        if(count > 0)
            userDataFromDbInterface.UserData(appDatabase.userDao().getUserData());
        else userDataFromDbInterface.UserData(null);
    }

    private static void readPosts(final AppDatabase appDatabase, final PostFromDbInterface postFromDbInterface){
        int count = appDatabase.postDao().getTotalPosts();
        if(count > 0){
            ArrayList<PostModels.PostData> postData = new ArrayList<>(appDatabase.postDao().getPostData());
            for (PostModels.PostData postDatum : postData) {
                postDatum.setPostMedia(appDatabase.postDao().getPostMedia(postDatum.getId()));
                postDatum.setUser(appDatabase.postDao().getPostOwner(postDatum.getId()));
                PostModels.PostData.LatestComment comments = appDatabase.postDao().getPostComments(postDatum.getId());
                comments.setUser(appDatabase.postDao().getCommentOwner(comments.getUserId()));
                postDatum.setLatestComment(comments);
            }
            PostModels postModels = new PostModels();
            postModels.setData(postData);
            postFromDbInterface.Posts(postModels);
        }
        else
            postFromDbInterface.Posts(null);

    }

    private static void readPostsByOwner(AppDatabase appDatabase,int userId,
                                         PostFromDbInterface postFromDbInterface){
        ArrayList<PostModels.PostData> postData = new ArrayList<>(appDatabase.postDao().getPostsByUserId(userId));
        if(postData.size() > 0){
        for (PostModels.PostData postDatum : postData) {
            postDatum.setPostMedia(appDatabase.postDao().getPostMedia(postDatum.getId()));
            postDatum.setUser(appDatabase.postDao().getPostOwner(userId));
            PostModels.PostData.LatestComment comments = appDatabase.postDao().getPostComments(postDatum.getId());
            comments.setUser(appDatabase.postDao().getCommentOwner(comments.getId()));
            postDatum.setLatestComment(comments);
        }
        PostModels postModels = new PostModels();
        postModels.setData(postData);
        postFromDbInterface.Posts(postModels);

        }
        else postFromDbInterface.Posts(null);
    }

    private static void addComments(final AppDatabase appDatabase, final PostModels.PostData.LatestComment comment){
        if(comment != null && comment.getUser() != null)
        appDatabase.postDao().InsertCommentOwner(comment.getUser());
        appDatabase.postDao().InsertPostComments(comment);
    }

    private static void addCommentsArray(AppDatabase appDatabase,
                                          ArrayList<PostModels.PostData.LatestComment>
                                                 latestCommentArrayList){
        if(latestCommentArrayList != null)
            for (PostModels.PostData.LatestComment comment : latestCommentArrayList) {
                appDatabase.postDao().InsertCommentOwner(comment.getUser());
                appDatabase.postDao().InsertPostComments(comment);
            }
    }

    private static void updateLike(final AppDatabase appDatabase, final PostModels.PostData postData){
        if(postData != null)
            appDatabase.postDao().updateLike(postData);
    }

    private static class AddUsersAsync extends AsyncTask<Void,Void,Void>{

        private final AppDatabase mDb;
        private final DummyUser mUser;

        public AddUsersAsync(AppDatabase db, DummyUser user){
            mDb = db;
            mUser = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            addUser(mDb,mUser);
            return null;
        }
    }

    private static class AddPostsAsync extends AsyncTask<Void,Void,Void>{

        final AppDatabase appDatabase;
        final PostModels postModels;

        public AddPostsAsync(final AppDatabase appDatabase, final PostModels postModels){
            this.appDatabase = appDatabase;
            this.postModels = postModels;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            addPosts(appDatabase, postModels);
            return null;
        }
    }

    private static class ReadPostsAsync extends AsyncTask<Void,Void,Void>{

        final AppDatabase appDatabase;
        final PostFromDbInterface postFromDbInterface;

        public ReadPostsAsync(final AppDatabase appDatabase, final PostFromDbInterface postFromDbInterface){
            this.appDatabase = appDatabase;
            this.postFromDbInterface = postFromDbInterface;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            readPosts(appDatabase,postFromDbInterface);
            return null;
        }
    }

    private static class ReadPostsByOwnerAsync extends AsyncTask<Void,Void,Void>{

        final AppDatabase appDatabase;
        final int userId;
        final PostFromDbInterface postFromDbInterface;

        public ReadPostsByOwnerAsync(final AppDatabase appDatabase, final int userId, final PostFromDbInterface postFromDbInterface){
            this.appDatabase = appDatabase;
            this.userId = userId;
            this.postFromDbInterface = postFromDbInterface;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            readPostsByOwner(appDatabase,userId,postFromDbInterface);
            return null;
        }
    }

    private static class AddCommentsAsync extends AsyncTask<Void,Void,Void>{

        final AppDatabase appDatabase;
        final PostModels.PostData.LatestComment comment;

        public AddCommentsAsync(final AppDatabase appDatabase, final PostModels.PostData.LatestComment comment){
            this.appDatabase = appDatabase;
            this.comment = comment;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            addComments(appDatabase,comment);
            return null;
        }
    }

    private static class GetUserDataAsync extends AsyncTask<Void,Void,Void>{

       private final AppDatabase appDatabase;
       private final UserDataFromDbInterface userDataFromDbInterface;

       public GetUserDataAsync(AppDatabase appDatabase, UserDataFromDbInterface userDataFromDbInterface){
           this.appDatabase = appDatabase;
           this.userDataFromDbInterface = userDataFromDbInterface;
       }

        @Override
        protected Void doInBackground(Void... voids) {
           getUserData(appDatabase,userDataFromDbInterface);
            return null;
        }
    }

    private static class AddUserDataAsync extends AsyncTask<Void,Void,Void>{

        private final AppDatabase appDatabase;
        private final User.UserData userData;

        public AddUserDataAsync(AppDatabase appDatabase, User.UserData userData){
            this.appDatabase = appDatabase;
            this.userData = userData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            addUserData(appDatabase,userData);
            return null;
        }
    }

    private static class UpdatePostLikeAsync extends AsyncTask<Void,Void,Void>{

        final AppDatabase appDatabase;
        final PostModels.PostData postData;

        public UpdatePostLikeAsync(final AppDatabase appDatabase, final PostModels.PostData postData){
            this.appDatabase = appDatabase;
            this.postData = postData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            updateLike(appDatabase,postData);
            return null;
        }
    }

    private static class AddCommentsArrayListAsync extends AsyncTask<Void,Void,Void>{

        final AppDatabase appDatabase;
        final ArrayList<PostModels.PostData.LatestComment> latestComments;

        public AddCommentsArrayListAsync(AppDatabase appDatabase,
                                         ArrayList<PostModels.PostData.LatestComment> latestCommentArrayList){
            this.appDatabase = appDatabase;
            this.latestComments = latestCommentArrayList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            addCommentsArray(appDatabase,latestComments);
            return null;
        }
    }
}
