package com.devartlab.data.room.callslide;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface CallSlideDao {

    @Query("SELECT * FROM CallSlideEntity")
    LiveData<List<CallSlideEntity>> getAll();

    @Query("DELETE FROM CallSlideEntity")
    public void deleteTable();

    @Insert
    void insert(CallSlideEntity entity);

    @Delete
    void delete(CallSlideEntity entity);

    @Update
    void update(CallSlideEntity entity);

}
