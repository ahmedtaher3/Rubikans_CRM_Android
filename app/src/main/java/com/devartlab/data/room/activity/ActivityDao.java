package com.devartlab.data.room.activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.devartlab.data.room.list.ListEntity;

import java.util.List;


@Dao
public interface ActivityDao {

    @Query("SELECT * FROM ActivityEntity")
    List<ActivityEntity> getAll();

    @Query("DELETE FROM ActivityEntity")
    public void deleteTable();

    @Insert
    void insert(ActivityEntity entity);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<ActivityEntity> entities);



    @Delete
    void delete(ActivityEntity entity);

    @Update
    void update(ActivityEntity entity);

}
