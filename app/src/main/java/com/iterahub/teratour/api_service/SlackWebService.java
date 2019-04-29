package com.iterahub.teratour.api_service;

import com.iterahub.teratour.models.CommentModels;
import com.iterahub.teratour.models.CommentsBody;
import com.iterahub.teratour.models.SlackMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ACER on 4/14/2018.
 */

public interface SlackWebService extends WebService{

    @POST("/services/T5JQ7S5RR/BA7T0KPNK/7VsX3S9Fat0RROGYvTfQUxwh")
    Call<Void> sendMessageToSlack(@Body SlackMessage slackMessage);

    @Override
    Call<CommentModels> postComments(String Authorization, int postId, CommentsBody commentsBody);
}
