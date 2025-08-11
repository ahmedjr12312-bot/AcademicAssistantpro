package com.example.academicassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.Course;
import java.util.List;

public class ManageCoursesActivity extends AppCompatActivity {
    private AppDatabase db;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);
        db = AppDatabase.get(this);
        list = findViewById(R.id.list_courses);

        findViewById(R.id.fab_add_course).setOnClickListener(v -> addDialog());
        refresh();
    }

    private void refresh() {
        List<Course> courses = db.courseDao().all();
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener((parent, view, position, id) -> {
            db.courseDao().delete(courses.get(position));
            Toast.makeText(this, "تم الحذف", Toast.LENGTH_SHORT).show();
            refresh();
            return true;
        });
    }

    private void addDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null, false);
        EditText name = view.findViewById(R.id.input_course_name);
        EditText code = view.findViewById(R.id.input_course_code);

        new AlertDialog.Builder(this)
            .setTitle("إضافة مقرر")
            .setView(view)
            .setPositiveButton("حفظ", (dialog, which) -> {
                String courseName = name.getText().toString().trim();
                String courseCode = code.getText().toString().trim();
                if (!courseName.isEmpty()) {
                    db.courseDao().insert(new Course(courseName, courseCode));
                    refresh();
                }
            })
            .setNegativeButton("إلغاء", null)
            .show();
    }
}
