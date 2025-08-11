package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskItem {
    @PrimaryKey(autoGenerate = true) public long id;
    public Long courseId; // nullable
    public String title;
    public long dueAt;
    public String status; // NEW, IN_PROGRESS, DONE
    public int remindBeforeMinutes;

    public static final String NEW = "NEW";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String DONE = "DONE";

    public TaskItem(Long courseId,String title,long dueAt,String status,int remindBeforeMinutes){
        this.courseId=courseId; this.title=title; this.dueAt=dueAt; this.status=status; this.remindBeforeMinutes=remindBeforeMinutes;
    }
}
