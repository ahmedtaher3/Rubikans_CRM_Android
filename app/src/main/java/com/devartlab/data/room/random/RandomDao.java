package com.devartlab.data.room.random;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devartlab.data.room.poduct.ProductEntity;

import java.util.List;


@Dao
public interface RandomDao {
    @Query("SELECT * FROM RandomEntity")
    List<RandomEntity>  getAll();


    @Query("DELETE FROM RandomEntity")
    public void deleteTable();

    @Insert
    void insert(RandomEntity entity);

    @Delete
    void delete(RandomEntity entity);

    @Update
    void update(RandomEntity entity);

}
