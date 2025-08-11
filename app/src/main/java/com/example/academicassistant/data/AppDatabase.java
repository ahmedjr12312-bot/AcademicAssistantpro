package com.example.academicassistant.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// قاعدة البيانات التي تحتوي على جميع الكيانات
@Database(entities = {Lecture.class, TaskItem.class, NoteItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LectureDao lectureDao();  // إضافة LectureDao
    public abstract TaskDao taskDao();  // إضافة TaskDao
    public abstract NoteDao noteDao();  // إضافة NoteDao
}
