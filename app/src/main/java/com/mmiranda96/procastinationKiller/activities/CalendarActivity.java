package com.mmiranda96.procastinationKiller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.util.IntentExtras;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

    private Date date;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView = findViewById(R.id.calendarViewCalendarActivityDate);
        this.dateView = findViewById(R.id.textViewCalendarActivityDate);

        Date today = new Date();
        calendarView.setDate(today.getTime());
        calendarView.setOnDateChangeListener(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarView.getDate());
        this.date = calendar.getTime();
    }

    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int day, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, day, month);

        this.date = calendar.getTime();
        this.dateView.setText(DATE_FORMAT.format(this.date));
    }

    public void sendDate(View view) {
        Intent intent = getIntent();
        long epoch = this.date.getTime();
        intent.putExtra(IntentExtras.DATE, epoch);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
