package com.devartlab.ui.main.ui.devartlink.letsTalk.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ChatItemModel.class},
        version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase{
    private static AppDataBase myDataBase;
    private static final String DBName="DATABASE";
    public  abstract classDAO classDAO();
    public static AppDataBase init(Context context){

        if (myDataBase==null){//create object
            myDataBase =
                    Room.databaseBuilder(context,AppDataBase.class,DBName)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return myDataBase;
    }

    public static AppDataBase getInstance(){
        return myDataBase;
    }
}
