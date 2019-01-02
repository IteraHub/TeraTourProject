package com.iterahub.teratour.api_service;


import com.iterahub.teratour.models.CommentModels;
import com.iterahub.teratour.models.CommentsOnPost;
import com.iterahub.teratour.models.LikeModel;
import com.iterahub.teratour.models.MarkerModel;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.models.SearchResultDataModel;
import com.iterahub.teratour.models.User;
import com.iterahub.teratour.models.UserBody;
import com.iterahub.teratour.models.CommentsBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Arinze on 11/8/2017.
 */

public interface WebService {

    @GET("./")
    Call<MarkerModel> getMarkers(@Query("id") String id);

    @POST("/user/register")
    Call<User> RegisterUser (@Body UserBody userBody);

    @GET("/user")
    Call<User.UserData> VerifyUser(@Header("Authorization")String Authorization);

    @GET("/posts")
    Call<PostModels> getPosts(@Header("Authorization") String Authorization);

    @GET("/posts/{post}/comments_view")
    Call<CommentsOnPost> getComments(@Header("Authorization") String Authorization, @Path("post") int postId);

    @POST("/posts/{post}/comments_view")
    Call<CommentModels> postComments(@Header("Authorization") String Authorization,
                                     @Path("post") int postId, @Body CommentsBody commentsBody);

    @GET("/posts/{post_id}/like_btn")
    Call<LikeModel> likePost(@Header("Authorization") String Authorization, @Path("post_id") int postId);

    @GET("/posts/{post_id}/unlike")
    Call<LikeModel> unLikePost(@Header("Authorization") String Authorization,@Path("post_id") int postId);

    @GET("/search")
    Call<SearchResultDataModel> search(@Header("Authorization") String Authorization, @Query("q") String query);




}
