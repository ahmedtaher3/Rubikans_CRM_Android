package com.devartlab.data.room.filterdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface FilterDataDao {

    @Query("SELECT * FROM FilterDataEntity WHERE parentName=:name AND parentId =:id")
    List<FilterDataEntity> getAll(String name , int id);

    @Query("DELETE FROM FilterDataEntity")
    public void deleteTable();

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertAll(List<FilterDataEntity> entities);

    @Insert
    void insert(FilterDataEntity entity);

    @Delete
    void delete(FilterDataEntity entity);

    @Update
    void update(FilterDataEntity entity);

}
