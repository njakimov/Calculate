package com.calculate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("ONCREATE", "Создал вторую активити");
        setContentView(R.layout.activity_second);

        Button btnToMain = findViewById(R.id.btnToMain);
        final EditText editTextDate = findViewById(R.id.editTextDate);

        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.w("MOVE", "На первую активити");
//                    setContentView(R.layout.activity_main);
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Log.e("MOVE", "Получено исключение");
                    ex.printStackTrace();
                }
            }
        });

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String monthText;
                if (month + 1 < 10) {
                    monthText = '0' + String.valueOf(month + 1);
                } else {
                    monthText = String.valueOf(month + 1);
                }
                String dayText;
                if (dayOfMonth < 10) {
                    dayText = '0' + String.valueOf(dayOfMonth);
                } else {
                    dayText = String.valueOf(dayOfMonth);
                }
                editTextDate.setText(dayText + '.' + monthText + '.' + year);
            }
        });
    }
}
