package com.example.academicassistant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.academicassistant.R;
import com.example.academicassistant.adapters.LectureAdapter;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.Course;
import com.example.academicassistant.data.Lecture;
import com.example.academicassistant.databinding.FragmentScheduleBinding;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private FragmentScheduleBinding b; private AppDatabase db;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        b = FragmentScheduleBinding.inflate(inflater, container, false);
        db = AppDatabase.get(requireContext());
        b.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        b.fabAdd.setOnClickListener(v -> addDialog());
        refresh(); return b.getRoot();
    }
    private void refresh(){ b.recycler.setAdapter(new LectureAdapter(db.lectureDao().allWithCourse())); }
    private void addDialog(){
        View v = getLayoutInflater().inflate(R.layout.dialog_add_lecture,null,false);
        Spinner spCourse=v.findViewById(R.id.sp_course), spDay=v.findViewById(R.id.sp_day);
        EditText start=v.findViewById(R.id.input_start), end=v.findViewById(R.id.input_end), loc=v.findViewById(R.id.input_location), note=v.findViewById(R.id.input_note);
        List<Course> courses = db.courseDao().all();
        if(courses.isEmpty()){ Toast.makeText(getContext(),"أضف المقررات أولاً من القائمة",Toast.LENGTH_LONG).show(); return; }
        ArrayAdapter<Course> a = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courses);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spCourse.setAdapter(a);
        ArrayAdapter<CharSequence> days = ArrayAdapter.createFromResource(requireContext(), R.array.days_ar, android.R.layout.simple_spinner_item);
        days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spDay.setAdapter(days);
        new AlertDialog.Builder(requireContext()).setTitle("إضافة محاضرة").setView(v)
            .setPositiveButton("حفظ",(d,w)->{
                Course c=(Course) spCourse.getSelectedItem();
                int dayIdx = spDay.getSelectedItemPosition()+1;
                db.lectureDao().insert(new Lecture(c.id, dayIdx, start.getText().toString(), end.getText().toString(), loc.getText().toString(), note.getText().toString()));
                refresh();
            }).setNegativeButton("إلغاء",null).show();
    }
}
