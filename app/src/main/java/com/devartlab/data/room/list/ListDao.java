package com.devartlab.data.room.list;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.devartlab.data.room.plan.PlanEntity;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface ListDao {
    @Query("SELECT * FROM ListEntity")
    List<ListEntity> getAll();

    @Query("SELECT * FROM ListEntity WHERE customerEnName LIKE '%' || :name || '%'")
    List<ListEntity> getAllByText(String name);

    @Query("SELECT * FROM ListEntity  WHERE customerTypeId=:id AND customerState !=0")
    List<ListEntity> getAll(String id);

    @Query("SELECT * FROM ListEntity  WHERE customerEnName LIKE '%' || :name || '%' AND isSynced =0")
    List<ListEntity> getByName(String name);

    @Query("SELECT * FROM ListEntity")
    List<ListEntity> getPharmacies();

    @Query("SELECT * FROM ListEntity WHERE customerEnName LIKE '%' || :name || '%'")
    List<ListEntity> getPharmacies(String name);


    @Query("DELETE FROM ListEntity")
    public void deleteTable();


    @Insert
    void insert(ListEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<ListEntity> entities);


    @Delete
    void delete(ListEntity entity);

    @Update
    void update(ListEntity entity);

}
