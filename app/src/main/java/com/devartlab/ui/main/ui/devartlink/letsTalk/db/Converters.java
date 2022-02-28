package com.devartlab.ui.main.ui.devartlink.letsTalk.db;
import androidx.room.TypeConverter;

import com.devartlab.ui.main.ui.eShopping.utils.filesUpload.VolleyFileObj;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {
    @TypeConverter
    public static VolleyFileObj fromString(String value) {
        Type listType = new TypeToken<VolleyFileObj>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(VolleyFileObj list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
