package com.example.academicassistant.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert long insert(NoteItem n);
    @Delete void delete(NoteItem n);
    @Query("SELECT * FROM NoteItem ORDER BY createdAt DESC") List<NoteItem> all();
}
