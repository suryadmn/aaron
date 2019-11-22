package com.simpleMan.aaron;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContentDao {

    @Insert
    void insert(Content content);

    @Query("SELECT * FROM content_table ORDER BY id DESC")
    LiveData<List<Content>> getAllNotes();

}
