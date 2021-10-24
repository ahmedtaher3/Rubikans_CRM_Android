package com.devartlab.data.room.locations;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devartlab.data.room.listtypes.ListTypesEntity;

import java.util.List;


@Dao
public interface LocationsDao {

    @Query("SELECT * FROM LocationsEntity")
    List<LocationsEntity> getAll();


    @Query("DELETE FROM LocationsEntity")
    public void deleteTable();


    @Insert
    void insert(LocationsEntity entity);

    @Delete
    void delete(LocationsEntity entity);

    @Update
    void update(LocationsEntity entity);

}
