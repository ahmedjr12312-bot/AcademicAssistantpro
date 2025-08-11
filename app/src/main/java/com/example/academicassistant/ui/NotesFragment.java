package com.example.academicassistant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.academicassistant.R;
import com.example.academicassistant.adapters.NoteAdapter;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.Course;
import com.example.academicassistant.data.NoteItem;
import com.example.academicassistant.databinding.FragmentNotesBinding;
import java.util.List;

public class NotesFragment extends Fragment {
    private FragmentNotesBinding b; private AppDatabase db;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        b = FragmentNotesBinding.inflate(inflater, container, false);
        db = AppDatabase.get(requireContext());
        b.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        b.fabAdd.setOnClickListener(v -> addDialog());
        refresh(); return b.getRoot();
    }
    private void refresh(){ b.recycler.setAdapter(new NoteAdapter(db.noteDao().all())); }
    private void addDialog(){
        View v = getLayoutInflater().inflate(R.layout.dialog_add_note,null,false);
        Spinner spCourse=v.findViewById(R.id.sp_course); EditText content=v.findViewById(R.id.input_content);
        List<Course> courses = db.courseDao().all();
        ArrayAdapter<Course> a = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courses);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spCourse.setAdapter(a);
        new AlertDialog.Builder(requireContext()).setTitle("إضافة ملاحظة").setView(v)
            .setPositiveButton("حفظ",(d,w)->{
                Long courseId = courses.isEmpty()? null : ((Course)spCourse.getSelectedItem()).id;
                db.noteDao().insert(new NoteItem(courseId, content.getText().toString(), System.currentTimeMillis()));
                refresh();
            }).setNegativeButton("إلغاء",null).show();
    }
}
