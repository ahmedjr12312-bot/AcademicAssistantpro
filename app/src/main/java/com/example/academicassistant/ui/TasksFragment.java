package com.example.academicassistant.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.example.academicassistant.adapters.TaskAdapter;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.Course;
import com.example.academicassistant.data.TaskItem;
    import com.example.academicassistant.databinding.FragmentTasksBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TasksFragment extends Fragment {
    private FragmentTasksBinding b; private AppDatabase db;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        b = FragmentTasksBinding.inflate(inflater, container, false);
        db = AppDatabase.get(requireContext());
        b.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        b.fabAdd.setOnClickListener(v -> addDialog());
        refresh(); return b.getRoot();
    }
    private void refresh(){ b.recycler.setAdapter(new TaskAdapter(db.taskDao().all(), t->{ db.taskDao().update(t); refresh(); })); }
    private void addDialog(){
        View v = getLayoutInflater().inflate(R.layout.dialog_add_task,null,false);
        Spinner spCourse=v.findViewById(R.id.sp_course), spStatus=v.findViewById(R.id.sp_status);
        EditText title=v.findViewById(R.id.input_title), due=v.findViewById(R.id.input_due), remind=v.findViewById(R.id.input_remind);
        List<Course> courses = db.courseDao().all();
        ArrayAdapter<Course> a = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courses);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spCourse.setAdapter(a);
        ArrayAdapter<CharSequence> s = ArrayAdapter.createFromResource(requireContext(), R.array.task_status, android.R.layout.simple_spinner_item);
        s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spStatus.setAdapter(s);
        Calendar cal = Calendar.getInstance();
        due.setOnClickListener(v1-> new DatePickerDialog(getContext(), (dp,y,m,d)->{
            cal.set(y,m,d);
            new TimePickerDialog(getContext(), (tp,h,mm)->{ cal.set(Calendar.HOUR_OF_DAY,h); cal.set(Calendar.MINUTE,mm);
                due.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(cal.getTime())); },
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show());
        new AlertDialog.Builder(requireContext()).setTitle("إضافة مهمة/واجب").setView(v)
            .setPositiveButton("حفظ",(d,w)->{
                Long courseId = courses.isEmpty()? null : ((Course)spCourse.getSelectedItem()).id;
                long dueAt=0; try{ dueAt=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).parse(due.getText().toString()).getTime(); }catch(Exception ignored){}
                int rem=0; try{ rem=Integer.parseInt(remind.getText().toString()); }catch(Exception ignored){}
                String st = spStatus.getSelectedItem().toString();
                db.taskDao().insert(new TaskItem(courseId, title.getText().toString(), dueAt, st, rem));
                refresh();
            }).setNegativeButton("إلغاء",null).show();
    }
}
