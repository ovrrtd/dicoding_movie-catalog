package com.example.movie_catalog.Alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.movie_catalog.Activity.MainActivity;
import com.example.movie_catalog.Activity.ReminderMenuActivity;
import com.example.movie_catalog.Controller.Controller;
import com.example.movie_catalog.Interface.MovieDbInterface;
import com.example.movie_catalog.Model.MovieDB.MovieDb;
import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY = "Daily Alarm";
    public static final String TYPE_RELEASE = "Release Alarm";
    private static final String CHANNEL_ID1 = "Channel_1";
    private static final String CHANNEL_NAME1 = "AlarmManager channel 1";
    private static final String CHANNEL_ID2 = "Channel_2";
    private static final String CHANNEL_NAME2 = "AlarmManager channel 2";

    // Siapkan 2 id untuk 2 macam alarm, onetime dan repeating
    private final static int ID_DAILY = 100;
    private final static int ID_RELEASE = 101;

    private static final String EXTRA_MESSAGE = "message";
    private static final String EXTRA_TYPE = "type";
    public final String API_KEY="fd141ba30ab693bd721d88bd9c88ca66";


    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = type.equalsIgnoreCase(TYPE_DAILY) ? TYPE_DAILY : TYPE_RELEASE;
        int notifId = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;

        //Jika Anda ingin menampilkan dengan Toast anda bisa menghilangkan komentar pada baris dibawah ini.
        //showToast(context, title, message);

        showAlarmNotification(context, title, message, notifId);
    }

    private void showAlarmNotification(final Context context, String title, final String message, final int notifId) {
        if (title.equalsIgnoreCase(TYPE_DAILY)) {
            notification(context,CHANNEL_ID1,CHANNEL_NAME1,title,message,1,notifId);
        }
        else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String now = dateFormat.format(date);

            MovieDbInterface movieDbInterface= Controller.getRetrofitInstance().create(MovieDbInterface.class);
            Call<MovieDb> call= movieDbInterface.getReleasedMoviesDb(API_KEY,now,now);
            call.enqueue(new Callback<MovieDb>() {
                @Override
                public void onResponse(Call<MovieDb> call, Response<MovieDb> response) {
                    ArrayList<Result> results = response.body().getResults();
                    int ids = 4;
                    for (Result result : results) {
                        String titles = result.getTitle();
                        Log.i("title",titles);
                        String desc = titles;
                        notification(context,CHANNEL_ID2,CHANNEL_NAME2,desc,message,ids,notifId);
                        ids++;
                    }
                }

                @Override
                public void onFailure(Call<MovieDb> call, Throwable t) {

                }
            });
        }
    }

    private void notification(Context context,String CHANNEL_ID,String CHANNEL_NAME,String title,String message,int id,int notifId){
        Intent intent;
        int ids=id;
        Log.i("Status", String.valueOf(notifId == 100));
        if (notifId == 101) {
            intent = new Intent(context, ReminderMenuActivity.class);
        }
        else {
            ids=1;
            intent = new Intent(context, MainActivity.class);
        }
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_new_releases_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(ids, notification);
        }
    }


    // Gunakan metode ini untuk menampilkan toast

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    // Metode ini digunakan untuk menjalankan alarm repeating
    public void setReleaseAlarm(Context context, String type,  String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
//
//        if (calendar.before(Calendar.getInstance())) {
//            calendar.add(Calendar.DATE, 1);
//        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        Toast.makeText(context, "Release Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }

    public void setDailyAlarm(Context context, String type, String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, "Daily Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show();
    }

    // Gunakan metode ini untuk mengecek apakah alarm tersebut sudah terdaftar di alarm manager
    public boolean isAlarmSet(Context context, String type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static String TIME_FORMAT = "HH:mm";

    // Metode ini digunakan untuk validasi date dan time
    private boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }


}