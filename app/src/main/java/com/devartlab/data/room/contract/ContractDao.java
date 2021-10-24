package com.devartlab.data.room.contract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.devartlab.data.room.purchasetype.PurchaseTypeEntity;

import java.util.List;


@Dao
public interface ContractDao {

    @Query("SELECT * FROM ContractEntity")
    List<ContractEntity> getAll();

    @Query("DELETE FROM ContractEntity")
    public void deleteTable();


    @Insert
    void insert(ContractEntity entity);


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    long[]  insertAll(List<ContractEntity> entities);

    @Delete
    void delete(ContractEntity entity);

    @Update
    void update(ContractEntity entity);

}
