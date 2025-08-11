package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lecture")  // اسم الجدول في قاعدة البيانات
public class Lecture {
    @PrimaryKey(autoGenerate = true)  // تعيين id تلقائي
    public int id;
    public String name;  // اسم المقرر
    public String code;  // رمز المقرر
}
