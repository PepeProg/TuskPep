package com.example.tusk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.Locale;

public class ListWithDay extends AppCompatActivity {

    public String conv(int index) {
        SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.setFirstDayOfWeek(cal.MONDAY);
        int day = cal.get (Calendar.DAY_OF_WEEK);
        cal.add (Calendar.DATE, cal.getFirstDayOfWeek () - day);
        String day_of_week = "";

        cal.add(Calendar.DATE, --index);
        day_of_week = sdf.format(cal.getTime());
        String cgro = day_of_week;
        return cgro;
    }
    String d1 = conv(1), d2 = conv(2), d3 = conv(3), d4 = conv(4),
            d5 = conv(5), d6 = conv(6), d7 = conv(7);
    String[] day_of_weeks = {"Понедельник, " + d1, "Вторник, " + d2, "Среда, " + d3,
            "Четверг, " + d4, "Пятница, " + d5, "Суббота, " + d6, "Воскресенье, " + d7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_with_day);

        ListView lvMain = (ListView) findViewById(R.id.lv);
        final TextView txt = (TextView) findViewById(R.id.txt);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, day_of_weeks);
        lvMain.setAdapter(adapter);
    }
}