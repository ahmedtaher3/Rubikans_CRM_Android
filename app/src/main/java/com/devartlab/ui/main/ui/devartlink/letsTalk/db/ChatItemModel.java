package com.devartlab.ui.main.ui.devartlink.letsTalk.db;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.devartlab.ui.main.ui.eShopping.utils.filesUpload.VolleyFileObj;

import java.io.Serializable;

@Entity

public class ChatItemModel implements Serializable{

    @ColumnInfo
    private String message;

    @ColumnInfo
    private String userId;

    @NonNull
    @PrimaryKey
    @ColumnInfo
    private String  id;

    @ColumnInfo
    private VolleyFileObj volleyFileObjs;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public VolleyFileObj getVolleyFileObjs() {
        return volleyFileObjs;
    }

    public void setVolleyFileObjs(VolleyFileObj volleyFileObjs) {
        this.volleyFileObjs = volleyFileObjs;
    }
}
