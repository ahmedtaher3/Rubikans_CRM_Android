package com.devartlab.data.room.startPoint;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface StartPointDao {

    @Query("SELECT * FROM StartPointEntity WHERE date=:date AND shift=:shift LIMIT 1")
    StartPointEntity  getAll(String date , String shift);

    @Query("DELETE FROM StartPointEntity")
    public void deleteTable();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insert(StartPointEntity entity);

    @Delete
    void delete(StartPointEntity entity);

    @Update
    void update(StartPointEntity entity);

}
