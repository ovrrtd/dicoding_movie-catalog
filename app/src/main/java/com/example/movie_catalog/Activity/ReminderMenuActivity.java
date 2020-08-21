package com.example.movie_catalog.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.movie_catalog.Adapter.MovieDbAdapter;
import com.example.movie_catalog.Alarm.AlarmReceiver;
import com.example.movie_catalog.Controller.Controller;
import com.example.movie_catalog.Interface.MovieDbInterface;
import com.example.movie_catalog.Model.MovieDB.MovieDb;
import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderMenuActivity extends AppCompatActivity {

    private SwitchCompat drSwitch;
    private SwitchCompat rrSwitch;
    private RecyclerView rv_reminder;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEdit;
    private AlarmReceiver alarmReceiver;
    private final String REMINDER="reminder";
    private ArrayList<Result> listDb=new ArrayList<>();

    public final String API_KEY="fd141ba30ab693bd721d88bd9c88ca66";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_menu);
        drSwitch=findViewById(R.id.daily_reminder);
        rrSwitch=findViewById(R.id.release_reminder);
        rv_reminder=findViewById(R.id.rv_reminder);

        sharedPreferences = getSharedPreferences(REMINDER, Context.MODE_PRIVATE);
        alarmReceiver = new AlarmReceiver();
        releaseSwitch();
        dailySwitch();
        setSwitch();

        rv_reminder.setHasFixedSize(true);
        if (savedInstanceState != null) {
            listDb = savedInstanceState.getParcelableArrayList("ParcelableMovies");
            showRecyclerListDb();
        }
        else{
            prepareDB();
        }
    }

    private void prepareDB(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);

        MovieDbInterface movieDbInterface= Controller.getRetrofitInstance().create(MovieDbInterface.class);
        Call<MovieDb> call= movieDbInterface.getReleasedMoviesDb(API_KEY,now,now);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("Please waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<MovieDb>() {
            @Override
            public void onResponse(Call<MovieDb> call, Response<MovieDb> response) {
                progressDialog.dismiss();
                listDb.addAll(response.body().getResults());
                showRecyclerListDb();
            }

            @Override
            public void onFailure(Call<MovieDb> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void dailySwitch() {
        drSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isTrue) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("daily_reminder", isTrue);
                sharedPreferenceEdit.apply();
                if (isTrue) {
                    alarmReceiver.setDailyAlarm(getApplicationContext(),AlarmReceiver.TYPE_DAILY,String.valueOf(R.string.daily_description));
                } else {
                    alarmReceiver.cancelAlarm(getApplicationContext(),AlarmReceiver.TYPE_DAILY);
                }
            }
        });}
    private void releaseSwitch(){
        rrSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isTrue) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("release_reminder", isTrue);
                sharedPreferenceEdit.apply();
                if (isTrue) {
                    alarmReceiver.setReleaseAlarm(getApplicationContext(),AlarmReceiver.TYPE_RELEASE,String.valueOf(R.string.release_description));
                } else {
                    alarmReceiver.cancelAlarm(getApplicationContext(),AlarmReceiver.TYPE_RELEASE);
                }
            }
        });
    }

    private void setSwitch() {
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);

        drSwitch.setChecked(dailyReminder);
        rrSwitch.setChecked(releaseReminder);
    }

    private void showRecyclerListDb(){
        rv_reminder.setLayoutManager(new LinearLayoutManager(this));
        MovieDbAdapter movieDbAdapter = new MovieDbAdapter(listDb);
        rv_reminder.setAdapter(movieDbAdapter);

        movieDbAdapter.setOnItemClickCallback(new MovieDbAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Result result) {
                Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailMovieActivity.class);
                moveWithObjectIntent.putExtra(DetailMovieActivity.DETAIL_MOVIE, result);
                startActivity(moveWithObjectIntent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ParcelableMovies",listDb);
        super.onSaveInstanceState(outState);
    }

}
