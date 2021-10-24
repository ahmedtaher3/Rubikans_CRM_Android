package com.devartlab.data.room.tradedetails;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface TradeDetailsDao {

    @Query("SELECT * FROM TradeDetailsEntity")
     List<TradeDetailsEntity>  getAll();


    @Query("DELETE FROM TradeDetailsEntity")
    public void deleteTable();

    @Insert
    long insert(TradeDetailsEntity entity);

    @Delete
    void delete(TradeDetailsEntity entity);

    @Update
    void update(TradeDetailsEntity entity);

}
