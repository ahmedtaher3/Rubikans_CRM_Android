package com.devartlab.data.room.authority;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface AuthorityDao {

    @Query("SELECT * FROM AuthorityEntity WHERE formId=:id LIMIT 1")
    AuthorityEntity getById(String id);

    @Query("SELECT * FROM AuthorityEntity")
    LiveData<List<AuthorityEntity>> getAll();

    @Query("SELECT * FROM AuthorityEntity")
    List<AuthorityEntity> getAllList();


    @Query("DELETE FROM AuthorityEntity")
    public void deleteTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AuthorityEntity entity);

    @Insert
    void insertAllEntities(List<AuthorityEntity> entities);

    @Delete
    void delete(AuthorityEntity entity);

    @Update
    void update(AuthorityEntity entity);

}
