package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Lecture {
    @PrimaryKey(autoGenerate = true) public long id;
    public long courseId;
    public int dayOfWeek; // 1..7
    public String startTime;
    public String endTime;
    public String location;
    public String note;
    public Lecture(long courseId,int dayOfWeek,String startTime,String endTime,String location,String note){
        this.courseId=courseId; this.dayOfWeek=dayOfWeek; this.startTime=startTime; this.endTime=endTime;
        this.location=location; this.note=note;
    }
}
