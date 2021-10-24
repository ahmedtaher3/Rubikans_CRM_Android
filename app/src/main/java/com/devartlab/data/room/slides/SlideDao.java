package com.devartlab.data.room.slides;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devartlab.data.room.arrangedslides.ArrangedSlidesEntity;


@Dao
public interface SlideDao {
    @Query("SELECT * FROM SlideEntity WHERE MessageId=:id")
     List<SlideEntity>  getAllByID(int id);


    @Query("SELECT * FROM SlideEntity WHERE ItemId=:id")
    List<SlideEntity>  getAllByProductID(int id);


    @Query("SELECT * FROM SlideEntity WHERE id=:slideId")
    SlideEntity getById(int slideId);

    @Query("DELETE FROM SlideEntity")
    public void deleteTable();

   /* @Query("SELECT * FROM ProductEntity WHERE id IN (:employeesIds)")
    List<Product> loadAllByIds(int[] employeesIds);*/

    @Insert
    void insert(SlideEntity slide);

    @Delete
    void delete(SlideEntity slide);

    @Update
    void update(SlideEntity slide);



}
