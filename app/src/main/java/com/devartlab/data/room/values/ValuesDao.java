package com.devartlab.data.room.values;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface ValuesDao {

    @Query("SELECT * FROM ValuesEntity")
    Flowable<List<ValuesEntity>> getAll();


    @Query("SELECT * FROM ValuesEntity  LIMIT 1")
    Flowable<ValuesEntity> getModel();

    @Query("DELETE FROM  ValuesEntity")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ValuesEntity entity);

    @Delete
    void delete(ValuesEntity entity);

    @Update
    void update(ValuesEntity entity);

}
