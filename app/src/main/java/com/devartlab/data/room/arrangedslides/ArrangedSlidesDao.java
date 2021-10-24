package com.devartlab.data.room.arrangedslides;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.devartlab.data.room.callslide.CallSlideEntity;
import com.devartlab.data.room.plan.PlanEntity;


@Dao
public interface ArrangedSlidesDao {

    @Query("SELECT * FROM ArrangedSlidesEntity WHERE arrangedID=:arrangedID")
    List<ArrangedSlidesEntity> getAll(int arrangedID);


    @Query("SELECT * FROM ArrangedSlidesEntity WHERE slideId=:slideId")
     ArrangedSlidesEntity  getById(int slideId);


    @Query("DELETE FROM ArrangedSlidesEntity WHERE arrangedID=:arrangedID")
    void deleteById(int arrangedID);

    @Query("DELETE FROM ArrangedSlidesEntity")
    public void deleteTable();

    @Insert
    void insert(ArrangedSlidesEntity entity);

    @Insert
    void insertAll(List<ArrangedSlidesEntity> entities);

    @Delete
    void delete(ArrangedSlidesEntity entity);

    @Update
    void update(ArrangedSlidesEntity entity);

}
