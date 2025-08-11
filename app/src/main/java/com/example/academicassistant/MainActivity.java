package com.example.academicassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.academicassistant.databinding.ActivityMainBinding;
import com.example.academicassistant.ui.NotesFragment;
import com.example.academicassistant.ui.ScheduleFragment;
import com.example.academicassistant.ui.TasksFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding b;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        b.bottomNav.setOnItemSelectedListener(this::onNav);
        b.bottomNav.setSelectedItemId(R.id.menu_schedule);
    }
    private boolean onNav(@NonNull MenuItem item){
        Fragment f = item.getItemId()==R.id.menu_schedule? new ScheduleFragment()
                  : item.getItemId()==R.id.menu_tasks? new TasksFragment()
                  : new NotesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
        return true;
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){ getMenuInflater().inflate(R.menu.main_menu, menu); return true; }
    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId()==R.id.menu_courses){ startActivity(new Intent(this, ManageCoursesActivity.class)); return true; }
        if (item.getItemId()==R.id.menu_share_export){ ShareUtil.exportJson(this); return true; }
        if (item.getItemId()==R.id.menu_share_import){ startActivity(new Intent(this, ImportJsonActivity.class)); return true; }
        return super.onOptionsItemSelected(item);
    }
}
