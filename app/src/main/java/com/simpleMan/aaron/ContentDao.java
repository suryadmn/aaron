package com.simpleMan.aaron;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContentDao {

    @Insert
    void insert(Content content);

    @Delete
    void delete(Content content);

    @Query("DELETE FROM content_table")
    void deleteAllContents();

    @Query("SELECT * FROM content_table ORDER BY id")
    LiveData<List<Content>> getAllContents();

}
