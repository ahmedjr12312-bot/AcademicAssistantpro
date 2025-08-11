package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")  // اسم الجدول في قاعدة البيانات
public class TaskItem {
    @PrimaryKey(autoGenerate = true)  // تعيين id تلقائي
    public int id;
    public String title;  // عنوان المهمة
    public String description;  // وصف المهمة
    public boolean isCompleted;  // حالة المهمة
}
