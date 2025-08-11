package com.example.academicassistant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.Course;
import com.example.academicassistant.data.Lecture;
import com.example.academicassistant.data.NoteItem;
import com.example.academicassistant.data.TaskItem;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareUtil {
    public static void exportJson(Context ctx){
        try{
            AppDatabase db = AppDatabase.get(ctx);
            Map<String,Object> map = new HashMap<>();
            map.put("courses", db.courseDao().all());
            map.put("lectures", db.lectureDao().all());
            map.put("tasks", db.taskDao().all());
            map.put("notes", db.noteDao().all());
            String json = new Gson().toJson(map);
            File f = new File(ctx.getCacheDir(), "academic-assistant-share.json");
            try(FileWriter w = new FileWriter(f)){ w.write(json); }
            Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName()+".provider", f);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("application/json"); i.putExtra(Intent.EXTRA_STREAM, uri);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ctx.startActivity(Intent.createChooser(i, "مشاركة البيانات"));
        }catch(Exception e){ e.printStackTrace(); }
    }
}
