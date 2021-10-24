package com.devartlab.data.room.trademaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TradeMasterDao {

    @Query("SELECT * FROM TradeMasterEntity")
     List<TradeMasterEntity>  getAll();


    @Query("DELETE FROM TradeMasterEntity")
    public void deleteTable();

    @Insert
    long insert(TradeMasterEntity entity);

    @Delete
    void delete(TradeMasterEntity entity);

    @Update
    void update(TradeMasterEntity entity);

}
