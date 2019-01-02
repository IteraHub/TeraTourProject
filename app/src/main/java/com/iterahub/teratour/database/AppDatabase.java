package com.iterahub.teratour.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.iterahub.teratour.models.DummyUser;
import com.iterahub.teratour.models.MarkerModel;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.dao.PostDao;
import com.iterahub.teratour.dao.UserDao;
import com.iterahub.teratour.models.User;

/**
 * Created by ACER on 2/4/2018.
 */
@Database(entities = {MarkerModel.class,PostModels.PostData.class,PostModels.PostData.LatestComment.class,
        PostModels.PostData.Media.class,PostModels.PostData.LatestComment.CommentOwner.class,PostModels.PostData.PostOwner.class,
        MarkerModel.Data.class,MarkerModel.Data.Image.class, DummyUser.class, DummyUser.DummyUserData.class, User.class,User.UserData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PostDao postDao();
}
