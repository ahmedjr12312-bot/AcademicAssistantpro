package com.example.academicassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academicassistant.R;
import com.example.academicassistant.data.NoteItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.VH> {
    private final List<NoteItem> data;
    public NoteAdapter(List<NoteItem> data){ this.data=data; }
    static class VH extends RecyclerView.ViewHolder { TextView content,date; VH(View v){ super(v); content=v.findViewById(R.id.txt_note_content); date=v.findViewById(R.id.txt_note_date);} }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int vt){ return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_note,p,false)); }
    @Override public void onBindViewHolder(@NonNull VH h,int i){
        NoteItem n=data.get(i);
        h.content.setText(n.content);
        h.date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date(n.createdAt)));
    }
    @Override public int getItemCount(){ return data.size(); }
}
