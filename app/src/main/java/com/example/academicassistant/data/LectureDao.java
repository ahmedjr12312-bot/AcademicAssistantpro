package com.example.academicassistant.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LectureDao {
    @Insert long insert(Lecture l);
    @Delete void delete(Lecture l);
    @Query("SELECT Lecture.*, Course.name as cname, Course.code as ccode FROM Lecture JOIN Course ON Lecture.courseId = Course.id ORDER BY dayOfWeek, startTime")
    List<JoinedLecture> allWithCourse();
    @Query("SELECT * FROM Lecture ORDER BY dayOfWeek, startTime")
    List<Lecture> all();
}
