package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true) public long id;
    public String name;
    public String code;
    public Course(String name, String code){ this.name=name; this.code=code; }
    public String toString(){ return (code==null? "": code+" - ")+name; }
}
