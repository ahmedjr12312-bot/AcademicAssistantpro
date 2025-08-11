package com.example.academicassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academicassistant.R;
import com.example.academicassistant.data.JoinedLecture;
import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.VH> {
    private final List<JoinedLecture> data;
    public LectureAdapter(List<JoinedLecture> data){ this.data=data; }
    static class VH extends RecyclerView.ViewHolder {
        TextView title,time,location,day;
        VH(View v){ super(v); title=v.findViewById(R.id.txt_title); time=v.findViewById(R.id.txt_time); location=v.findViewById(R.id.txt_location); day=v.findViewById(R.id.txt_day); }
    }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int vt){ return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_lecture,p,false)); }
    @Override public void onBindViewHolder(@NonNull VH h,int i){
        JoinedLecture l = data.get(i);
        h.title.setText((l.ccode==null? "" : l.ccode+" - ") + l.cname);
        h.time.setText(l.startTime+" - "+l.endTime);
        h.location.setText(l.location);
        String[] days = h.itemView.getResources().getStringArray(R.array.days_ar);
        int idx=Math.max(1,Math.min(7,l.dayOfWeek))-1; h.day.setText(days[idx]);
    }
    @Override public int getItemCount(){ return data.size(); }
}
