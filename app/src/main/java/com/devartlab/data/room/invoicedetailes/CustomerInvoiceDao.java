package com.devartlab.data.room.invoicedetailes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;


@Dao
public interface CustomerInvoiceDao {

    @Query("SELECT * FROM CustomerInvoiceEntity")
    List<CustomerInvoiceEntity> getAll();

    @Query("DELETE FROM CustomerInvoiceEntity")
    public void deleteTable();

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertAll(List<CustomerInvoiceEntity> entities);

    @Insert
    void insert(CustomerInvoiceEntity entity);

    @Delete
    void delete(CustomerInvoiceEntity entity);

    @Update
    void update(CustomerInvoiceEntity entity);

}
