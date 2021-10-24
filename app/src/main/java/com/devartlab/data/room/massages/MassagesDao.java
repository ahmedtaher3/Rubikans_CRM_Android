package com.devartlab.data.room.massages;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface MassagesDao {

    @Query("SELECT * FROM MassageEntity WHERE ItemId=:id")
     List<MassageEntity>  getAll(int id);

    @Query("DELETE FROM MassageEntity")
    public void deleteTable();

    @Insert
    long insert(MassageEntity entity);

    @Delete
    void delete(MassageEntity entity);

    @Update
    void update(MassageEntity entity);

}
