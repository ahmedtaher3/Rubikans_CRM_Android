package com.devartlab.data.room.specialty;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface SpecialtyDao {

    @Query("SELECT * FROM SpecialtyParentEntity")
    List<SpecialtyParentEntity> getAll();

    @Query("DELETE FROM SpecialtyParentEntity")
    public void deleteTable();

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertAll(List<SpecialtyParentEntity> entities);

    @Insert
    void insert(SpecialtyParentEntity entity);

    @Delete
    void delete(SpecialtyParentEntity entity);

    @Update
    void update(SpecialtyParentEntity entity);

}
