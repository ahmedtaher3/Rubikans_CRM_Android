package com.devartlab.data.room.poduct;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    LiveData<List<ProductEntity>> getAll();

    @Query("SELECT * FROM ProductEntity")
    List<ProductEntity>   getAllList();

    @Query("SELECT * FROM ProductEntity")
     List<ProductEntity> getAll2();


    @Query("DELETE FROM ProductEntity")
    public void deleteTable();

   /* @Query("SELECT * FROM ProductEntity WHERE id IN (:employeesIds)")
    List<Product> loadAllByIds(int[] employeesIds);*/

    @Insert
    void insert(ProductEntity product);


    @Delete
    void delete(ProductEntity product);

    @Update
    void update(ProductEntity product);

}
