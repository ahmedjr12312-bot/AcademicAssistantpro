package com.example.academicassistant;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.Course;
import com.example.academicassistant.data.Lecture;
import com.example.academicassistant.data.NoteItem;
import com.example.academicassistant.data.TaskItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ImportJsonActivity extends AppCompatActivity {
    private final ActivityResultLauncher<String> picker =
            registerForActivityResult(new ActivityResultContracts.GetContent(), this::onPicked);
    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_json);
        Button btn = findViewById(R.id.btn_pick);
        btn.setOnClickListener(v -> picker.launch("application/json"));
    }
    private void onPicked(Uri uri){
        if(uri==null) return;
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
            StringBuilder sb=new StringBuilder(); String line; while((line=r.readLine())!=null) sb.append(line); r.close();
            Map<String,Object> map = new Gson().fromJson(sb.toString(), Map.class);
            Gson g = new Gson();
            AppDatabase db = AppDatabase.get(this);
            List<Course> courses = g.fromJson(g.toJson(map.get("courses")), new TypeToken<List<Course>>(){}.getType());
            List<Lecture> lectures = g.fromJson(g.toJson(map.get("lectures")), new TypeToken<List<Lecture>>(){}.getType());
            List<TaskItem> tasks = g.fromJson(g.toJson(map.get("tasks")), new TypeToken<List<TaskItem>>(){}.getType());
            List<NoteItem> notes = g.fromJson(g.toJson(map.get("notes")), new TypeToken<List<NoteItem>>(){}.getType());
            if(courses!=null) for(Course c: courses){ c.id=0; db.courseDao().insert(c); }
            if(lectures!=null) for(Lecture l: lectures){ l.id=0; db.lectureDao().insert(l); }
            if(tasks!=null) for(TaskItem t: tasks){ t.id=0; db.taskDao().insert(t); }
            if(notes!=null) for(NoteItem n: notes){ n.id=0; db.noteDao().insert(n); }
            Toast.makeText(this,"تم الاستيراد",Toast.LENGTH_LONG).show(); finish();
        } catch(Exception e){ Toast.makeText(this,"فشل: "+e.getMessage(),Toast.LENGTH_LONG).show(); }
    }
}
