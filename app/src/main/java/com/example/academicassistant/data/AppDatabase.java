package com.example.academicassistant.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, Lecture.class, TaskItem.class, NoteItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract CourseDao courseDao();  // موجودة مسبقًا
    public abstract LectureDao lectureDao();  // إضافة LectureDao
    public abstract TaskDao taskDao();  // إضافة TaskDao
    public abstract NoteDao noteDao();  // إضافة NoteDao

    // إنشاء الدالة التي ترجع قاعدة البيانات
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "academic_assistant_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
