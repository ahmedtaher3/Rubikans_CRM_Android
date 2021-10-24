package com.devartlab.data.room.myballance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface MyBallanceDao {

    @Query("SELECT * FROM MyBallanceEntity")
    List<MyBallanceEntity> getAll();

    @Query("DELETE FROM MyBallanceEntity")
    public void deleteTable();


    @Insert
    void insert(MyBallanceEntity entity);


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    long[]  insertAll(List<MyBallanceEntity> entities);

    @Delete
    void delete(MyBallanceEntity entity);

    @Update
    void update(MyBallanceEntity entity);

}
