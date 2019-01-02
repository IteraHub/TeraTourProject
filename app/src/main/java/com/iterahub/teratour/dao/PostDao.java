package com.iterahub.teratour.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.iterahub.teratour.models.PostModels;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ACER on 4/12/2018.
 */
@Dao
public interface PostDao {

    @Insert(onConflict = REPLACE)
    void InsertPostData(PostModels.PostData... data);

    @Insert(onConflict = REPLACE)
    void InsertPostOwner(PostModels.PostData.PostOwner postOwner);

    @Insert(onConflict = REPLACE)
    void InsertPostMedia(PostModels.PostData.Media media);

    @Insert(onConflict = REPLACE)
    void InsertPostComments(PostModels.PostData.LatestComment comment);

    @Insert(onConflict = REPLACE)
    void InsertCommentOwner(PostModels.PostData.LatestComment.CommentOwner commentOwner_);

    @Query("SELECT * FROM PostData")
    List<PostModels.PostData> getPostData();

    @Query("SELECT * FROM Media WHERE postId = :postId")
    PostModels.PostData.Media getPostMedia(int postId);

    @Query("SELECT * FROM PostOwner WHERE id = :userId")
    PostModels.PostData.PostOwner getPostOwner(int userId);

    @Query("SELECT * FROM LatestComment WHERE postId = :postId")
    PostModels.PostData.LatestComment getPostComments(int postId);

    @Query("SELECT * FROM CommentOwner WHERE commentId = :commentId")
    PostModels.PostData.LatestComment.CommentOwner getCommentOwner(int commentId);

    @Query("SELECT postId FROM PostOwner WHERE id = :userId")
    int getPostId(int userId);

    @Query("SELECT * FROM PostData WHERE id = :postId")
    List<PostModels.PostData> getPostsById(int postId);

    @Query("SELECT * FROM PostData WHERE userId = :userId")
    List<PostModels.PostData> getPostsByUserId(int userId);

    @Query("SELECT COUNT(*) FROM PostData")
    int getTotalPosts();

    @Update(onConflict = REPLACE)
    void updateLike(PostModels.PostData postData);


}
