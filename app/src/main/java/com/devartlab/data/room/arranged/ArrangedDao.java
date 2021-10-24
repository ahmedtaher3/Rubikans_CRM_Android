package com.devartlab.data.room.arranged;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ArrangedDao {

    @Query("SELECT * FROM ArrangedEntity WHERE customerId=:id")
     List<ArrangedEntity>  getAll(int id);


    @Query("DELETE FROM ArrangedEntity")
    public void deleteTable();

    @Insert
    long insert(ArrangedEntity entity);

    @Delete
    void delete(ArrangedEntity entity);

    @Update
    void update(ArrangedEntity entity);

}
