package com.example.mycomputer.igotafriend.activities;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.mycomputer.igotafriend.R;
import com.google.gson.Gson;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;

public class CalenderActivty extends AppCompatActivity {
    private CalendarPickerView calendar_view;
    List<Date> dates;
    Calendar nextYear;
    Button btnSubmit;
    private static final int DATEPICK = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
//        final Intent intent = getIntent();
//        if (intent != null) {
/////am i getting a date;
//        }

        calendar_view = (CalendarPickerView) findViewById(R.id.calendar_view);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        nextYear = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nextYear = Calendar.getInstance();
            nextYear.add(Calendar.YEAR, 1);

            CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
            Date today = new Date();


            calendar.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);
        }
        dates = new ArrayList<>();
        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                dates.add(date);
            }

            @Override
            public void onDateUnselected(Date date) {
                dates.remove(date);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    Gson gson = new Gson();
                    String array = gson.toJson(dates);

                    intent.putExtra("dates", array);
                    setResult(RESULT_OK, intent);
                    finish();



            }
        });
    }

}