package com.example.academicassistant.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NoteItem {
    @PrimaryKey(autoGenerate = true) public long id;
    public Long courseId;
    public String content;
    public long createdAt;
    public NoteItem(Long courseId,String content,long createdAt){ this.courseId=courseId; this.content=content; this.createdAt=createdAt; }
}
