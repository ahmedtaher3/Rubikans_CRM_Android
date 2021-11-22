package com.devartlab.data.room.collect;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface CollectDao {

    @Query("SELECT * FROM CollectEntity")
    List<CollectEntity> getAll();

    @Query("DELETE FROM CollectEntity")
    public void deleteTable();

    @Insert
    void insert(CollectEntity entity);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<CollectEntity> entities);



    @Delete
    void delete(CollectEntity entity);

    @Update
    void update(CollectEntity entity);

}
