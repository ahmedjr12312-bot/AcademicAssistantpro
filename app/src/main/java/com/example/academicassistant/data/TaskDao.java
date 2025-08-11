package com.example.academicassistant.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert long insert(TaskItem t);
    @Update void update(TaskItem t);
    @Delete void delete(TaskItem t);
    @Query("SELECT * FROM TaskItem ORDER BY dueAt") List<TaskItem> all();
    @Query("SELECT * FROM TaskItem WHERE status != 'DONE' AND dueAt > 0 AND (dueAt - :now) <= :ahead")
    List<TaskItem> dueWithin(long now, long ahead);
}
