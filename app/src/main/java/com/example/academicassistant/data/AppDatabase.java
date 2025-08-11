package com.example.academicassistant.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Course.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract CourseDao courseDao();

    public static synchronized AppDatabase get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "aa.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
