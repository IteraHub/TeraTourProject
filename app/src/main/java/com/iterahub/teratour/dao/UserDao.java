package com.iterahub.teratour.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.iterahub.teratour.models.DummyUser;
import com.iterahub.teratour.models.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ACER on 2/4/2018.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void InsertUserData(DummyUser.DummyUserData... dummyUserData);

    @Query("SELECT * FROM DummyUserData WHERE UserId = :userId")
    DummyUser.DummyUserData getUser(String userId);

    @Query("SELECT COUNT(*) FROM DummyUser")
    int getCount();

    @Insert(onConflict = REPLACE)
    void InsertUser(User.UserData... userData);

    @Query("SELECT COUNT(*) FROM UserData")
    int getUserCount();

    @Query("SELECT * FROM UserData")
    User.UserData getUserData();


}
