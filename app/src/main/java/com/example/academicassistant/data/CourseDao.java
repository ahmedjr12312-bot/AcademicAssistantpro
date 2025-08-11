package com.example.academicassistant.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CourseDao {
    @Insert long insert(Course c);
    @Delete void delete(Course c);
    @Query("SELECT * FROM Course ORDER BY name") List<Course> all();
    @Query("SELECT * FROM Course WHERE id=:id LIMIT 1") Course byId(long id);
}
