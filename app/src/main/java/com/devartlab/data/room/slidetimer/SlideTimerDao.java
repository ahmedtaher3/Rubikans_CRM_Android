package com.devartlab.data.room.slidetimer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface SlideTimerDao {

    @Query("SELECT * FROM SlideTimerEntity")
     List<SlideTimerEntity>  getAllByID();

    @Query("DELETE FROM  SlideTimerEntity")
    void deleteAll();
   /* @Query("SELECT * FROM ProductEntity WHERE id IN (:employeesIds)")
    List<Product> loadAllByIds(int[] employeesIds);*/

    @Insert
    void insert(SlideTimerEntity slide);

    @Delete
    void delete(SlideTimerEntity slide);

    @Update
    void update(SlideTimerEntity slide);

}
