package com.devartlab.ui.main.ui.devartlink.letsTalk.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface classDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForm(ChatItemModel workerFormTblDTOListItem);


    @Query("select * from ChatItemModel;")
    ChatItemModel getAllANewForms();

    @Query("delete from ChatItemModel ")
    void deleteAllRecords();


}
