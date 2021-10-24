package com.devartlab.data.room.purchasetype;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface PurchaseTypeDao {

    @Query("SELECT * FROM PurchaseTypeEntity")
    List<PurchaseTypeEntity> getAll();

    @Query("DELETE FROM PurchaseTypeEntity")
    public void deleteTable();

    @Insert
    void insert(PurchaseTypeEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[]  insertAll(List<PurchaseTypeEntity> entities);

    @Delete
    void delete(PurchaseTypeEntity entity);

    @Update
    void update(PurchaseTypeEntity entity);

}
