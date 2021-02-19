package com.calculate;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

// 1. Создать проект со следующими пользовательскими элементами: TextView, EditText, Button,
//  Switch, CheckBox, ToggleButton. Для работы использовать LinearLayout. Использовать
//  различные шрифты, цвета, размеры, прочие атрибуты.
// 2. Создать ещё один макет (если не получается, то проект) с несколькими EditText, где
//  использовать атрибут inputType: text, textPersonName, phone, number, textPassword,
//  textEmailAddress и другие значения.
// 3. Добавить в проект элемент CalendarView.
// 4. * Разобраться, что такое параметр ems.
public class MainActivity extends AppCompatActivity {
    Integer a;
    Integer b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtField1 = findViewById(R.id.txtField1);
        final EditText txtField2 = findViewById(R.id.txtField2);
        final TextView viewResult = findViewById(R.id.txtResult);
        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnToSecond = findViewById(R.id.btnToSecond);
        final Switch swCheckBox = findViewById(R.id.switch1);
        final CheckBox checkBox1 = findViewById(R.id.checkBox1);
        final CheckBox checkBox2 = findViewById(R.id.checkBox2);
        final ToggleButton tglCheckbox = findViewById(R.id.tglCheckbox);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.w("CALC", "Посчитал");
                    a = Integer.valueOf(txtField1.getText().toString());
                    b = Integer.valueOf(txtField2.getText().toString());
                    viewResult.setText(String.valueOf(a + b));
                } catch (Exception ex) {
                    Log.e("CALC", "Получено исключение");
                    ex.printStackTrace();
                }
            }
        });

        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.w("MOVE", "На вторую активити");
//                    setContentView(R.layout.activity_second);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Log.e("MOVE", "Получено исключение");
                    ex.printStackTrace();
                }
            }
        });


        swCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.w("SWITCH", "Переключил");
                    checkBox1.setChecked(!checkBox1.isChecked());
                    checkBox2.setChecked(!checkBox2.isChecked());
                } catch (Exception ex) {
                    Log.e("SWITCH", "Получено исключение");
                    ex.printStackTrace();
                }
            }
        });

        tglCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.w("TOOGGLE", "Переключил");
                    checkBox1.setChecked(!checkBox1.isChecked());
                    checkBox2.setChecked(!checkBox2.isChecked());
                } catch (Exception ex) {
                    Log.e("TOOGGLE", "Получено исключение");
                    ex.printStackTrace();
                }
            }
        });
    }

}