package com.example.academicassistant.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.academicassistant.MyApp;
import com.example.academicassistant.R;
import com.example.academicassistant.data.AppDatabase;
import com.example.academicassistant.data.TaskItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReminderWorker extends Worker {
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params){ super(context, params); }
    @NonNull @Override public Result doWork() {
        long now = System.currentTimeMillis();
        long ahead = 15 * 60 * 1000;
        List<TaskItem> due = AppDatabase.get(getApplicationContext()).taskDao().dueWithin(now, ahead);
        int id = 2000;
        for (TaskItem t: due) {
            String when = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date(t.dueAt));
            NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext(), MyApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("تذكير: " + t.title)
                .setContentText("الموعد: " + when)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat.from(getApplicationContext()).notify(id++, b.build());
        }
        return Result.success();
    }
}
