package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")  // اسم الجدول في قاعدة البيانات
public class NoteItem {
    @PrimaryKey(autoGenerate = true)  // تعيين id تلقائي
    public int id;
    public String title;  // عنوان الملاحظة
    public String content;  // محتوى الملاحظة
}
