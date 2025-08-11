package com.example.academicassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academicassistant.R;
import com.example.academicassistant.data.TaskItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH> {
    private final List<TaskItem> data; private final Consumer<TaskItem> onChange;
    public TaskAdapter(List<TaskItem> data, Consumer<TaskItem> onChange){ this.data=data; this.onChange=onChange; }
    static class VH extends RecyclerView.ViewHolder { TextView title,due; Spinner status; CheckBox done; VH(View v){ super(v);
        title=v.findViewById(R.id.txt_task_title); due=v.findViewById(R.id.txt_task_due); status=v.findViewById(R.id.sp_task_status); done=v.findViewById(R.id.cb_done);} }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int vt){ return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_task,p,false)); }
    @Override public void onBindViewHolder(@NonNull VH h,int i){
        TaskItem t=data.get(i);
        h.title.setText(t.title);
        h.due.setText(t.dueAt>0? new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(new Date(t.dueAt)) : "بدون موعد");
        ArrayAdapter<CharSequence> a = ArrayAdapter.createFromResource(h.itemView.getContext(), R.array.task_status, android.R.layout.simple_spinner_item);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); h.status.setAdapter(a);
        int pos = "IN_PROGRESS".equals(t.status)?1: "DONE".equals(t.status)?2:0;
        h.status.setSelection(pos,false);
        h.done.setChecked("DONE".equals(t.status));
        h.done.setOnCheckedChangeListener((c,checked)->{ t.status=checked?"DONE":"NEW"; onChange.accept(t); });
        h.status.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id){
                String nv = position==0? "NEW" : position==1? "IN_PROGRESS" : "DONE";
                if(!nv.equals(t.status)){ t.status=nv; onChange.accept(t); }
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent){}
        });
    }
    @Override public int getItemCount(){ return data.size(); }
}
