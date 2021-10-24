package com.devartlab.data.room.plan;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface PlanDao {
    @Query("SELECT * FROM PlanEntity WHERE deleted=0")
    LiveData<List<PlanEntity>> getAll();

    @Query("SELECT * FROM PlanEntity WHERE IsVisit=1")
    List<PlanEntity> getAllConfirmed();

    @Query("SELECT * FROM PlanEntity")
    Flowable<List<PlanEntity>> getAllPlan();


    @Query("SELECT * FROM PlanEntity WHERE planId=:id AND deleted=0 LIMIT 1")
    PlanEntity getAllById(String id);

    @Query("SELECT * FROM PlanEntity WHERE planId=:id AND deleted=0 LIMIT 1")
    Flowable<PlanEntity> getAllById2(String id);

    @Query("SELECT * FROM PlanEntity  WHERE Date > :date")
    List<PlanEntity> getListAfterToday(String date);

    @Query("SELECT * FROM PlanEntity  WHERE Date > :date AND deleted=0")
    List<PlanEntity> getListAfterTodayNotDeleted(String date);


    @Query("SELECT * FROM PlanEntity WHERE Date > :date AND deleted=1 AND updated =1")
    List<PlanEntity> getDeletedPlan(String date);


    @Query("SELECT * FROM PlanEntity WHERE date=:date AND deleted=1")
    List<PlanEntity> getAllByDate2(String date);


    @Query("SELECT * FROM PlanEntity WHERE date=:date AND shift=:shift AND deleted=0")
    List<PlanEntity> getAllByDateAndShift(String date, String shift);

    @Query("SELECT * FROM PlanEntity WHERE date=:date AND shift=:shift AND deleted=0 AND activityId!=2")
    List<PlanEntity> getAllByDateAndShiftExceptDouble(String date, String shift);


    @Query("SELECT * FROM PlanEntity WHERE date=:date AND shift=:shift AND deleted=0 AND activityId=2")
    List<PlanEntity> getAllByDateAndShiftDouble(String date, String shift);


    @Query("SELECT * FROM PlanEntity WHERE date=:date AND shift=:shift AND CustomerName=:customerName AND Customerid=:customerId ")
    PlanEntity getModel(String date, String shift, String customerName, int customerId);

    @Query("SELECT * FROM PlanEntity WHERE date=:date AND shift=:shift AND DoubleVAccountId =:DoubleVAccountId AND deleted=0")
    List<PlanEntity> getDouble(String DoubleVAccountId, String date, String shift);


    @Query("DELETE FROM PlanEntity WHERE Date > :date AND deleted=0 ")
    void deleteTableAfterUpdate(String date);


    @Query("DELETE FROM PlanEntity")
    public void deleteTable();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PlanEntity planEntity);

    @Insert
    long[] insertAll(PlanEntity... planEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllEntities(List<PlanEntity> planEntities);


    @Delete
    void delete(PlanEntity planEntity);

    @Update
    void update(PlanEntity planEntity);

}
