package com.example.lab5database.Infrastructure;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lab5database.Entities.UserRecord;

import java.util.List;

@Dao
public interface IUserDao {
    //get all users
    @Query("SELECT * FROM userRecords")
    List<UserRecord> getAllRecords();

    //insert one user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserRecord user);

    //insert list of users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserRecord ... users);

    //find user
    @Query("SELECT * FROM userRecords WHERE name LIKE :name || '%' OR surname LIKE :name || '%'")
    LiveData<List<UserRecord>> findByNameOrSurname(String name);

}
