package com.devartlab.data.room.listtypes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.devartlab.data.room.purchasetype.PurchaseTypeEntity;

import java.util.List;


@Dao
public interface ListTypesDao {
    @Query("SELECT * FROM ListTypesEntity")
    List<ListTypesEntity> getAll();


    @Query("DELETE FROM ListTypesEntity")
    public void deleteTable();


    @Insert
    void insert(ListTypesEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[]  insertAll(List<ListTypesEntity> entities);

    @Delete
    void delete(ListTypesEntity entity);

    @Update
    void update(ListTypesEntity entity);

}
