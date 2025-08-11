package com.example.academicassistant.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Course.class, Lecture.class, TaskItem.class, NoteItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract CourseDao courseDao();
    public abstract LectureDao lectureDao();
    public abstract TaskDao taskDao();
    public abstract NoteDao noteDao();
    public static synchronized AppDatabase get(Context ctx){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "aa.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
