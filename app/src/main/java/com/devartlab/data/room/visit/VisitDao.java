package com.devartlab.data.room.visit;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface VisitDao {

    @Query("SELECT * FROM VisitEntity")
    LiveData<List<VisitEntity>> getAllByID();
   /* @Query("SELECT * FROM ProductEntity WHERE id IN (:employeesIds)")
    List<Product> loadAllByIds(int[] employeesIds);*/


    @Query("SELECT * FROM VisitEntity WHERE shiftId =:shiftId AND date =:date AND customerId =:customerId AND userId =:userId  LIMIT 1")
    VisitEntity  getVisit(int shiftId, String date, int customerId, int userId);


    @Insert
    void insert(VisitEntity visitEntity);

    @Delete
    void delete(VisitEntity visitEntity);

    @Update
    void update(VisitEntity visitEntity);

}
