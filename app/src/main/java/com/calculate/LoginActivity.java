package com.calculate;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    // Имя настроек
    private static final String NameSharedPreference = "LOGIN";
    // Имя параметра в настройках
    private static final String AppTheme = "APP_THEME";
    private static final int AppThemeMaterialBlack = 0;
    private static final int AppThemeMaterialRed = 1;
    private static final int AppThemeMaterialDefault = 2;
    private static final int AppThemeMaterialBlue = 3;

    // Регулярные выражения позволяют проверить на соответствие шаблону
    // Это имя. Первая буква большая латинская, остальные маленькие латинские
    Pattern checkLogin = Pattern.compile("^[A-Z][a-z]{2,}$");
    // Это пароль, минимум 6 символов, обязательны маленькая буква, большая буква, цифра
    Pattern checkPassword = Pattern.compile("^(?=^.{6,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyCoolStyle));                                                                     // Устанавливать тему надо только до установки макета активити
        setContentView(R.layout.activity_login);
        initThemeChooser();
        initList();
        initTexts();
    }

    private void initTexts() {
        TextInputEditText login = findViewById(R.id.inputLoginName);
        TextInputEditText password = findViewById(R.id.inputPassword);
        final TextInputLayout layoutLogin = findViewById(R.id.loginName);
        final TextInputLayout layoutPassword = findViewById(R.id.password);

        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {                                               // Чтобы не докучать пользователю при вводе каждой буквы, сделаем проверку при потере фокуса

            @Override
            public void onFocusChange(View v, boolean hasFocus) {                                                       // Как только фокус потерян, сразу проверяем на валидность данные
                if (hasFocus) return;
                TextView tv = (TextView) v;
                String value = tv.getText().toString();
                if (checkLogin.matcher(value).matches()) {
                    tv.setError(null);
                } else {
                    tv.setError(getString(R.string.not_name));                                                          // Ошибка отобразится справа
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {                                            // Пароль тоже проверим при потере фокуса
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                TextView tv = (TextView) v;
                String value = tv.getText().toString();
                if (checkPassword.matcher(value).matches()) {
                    layoutPassword.setError(null);
                } else {
                    layoutPassword.setError(getString(R.string.weak_password));                                         // Ошибка отобразится снизу
                }
            }
        });
    }

    private void initList() {
        LinearLayout layoutList = findViewById(R.id.layoutList);
        LayoutInflater ltInflater = getLayoutInflater();                                                                // При помощи этого объекта будем надувать элементы, спрятанные в android_item.xml

        String[] versions = getResources().getStringArray(R.array.version_names);
        TypedArray imgs = getResources().obtainTypedArray(R.array.version_logos);                                       // Получить из ресурсов массив указателей на изображения

        for (int i = 0; i < versions.length; i++) {

            View item = ltInflater.inflate(R.layout.android_item, layoutList, false);                        // Достаём элемент из android_item.xml

            TextView tv = item.findViewById(R.id.textAndroid);                                                          // Находим в этом элементе TextView
            String version = versions[i];
            tv.setText(version);

            AppCompatImageView imgLogo = item.findViewById(R.id.imageAndroid);                                          // Выбрать по индексу подходящее изображение
            imgLogo.setImageResource(imgs.getResourceId(i, -1));

            layoutList.addView(item);
        }
    }

    // Инициализация радиокнопок
    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMaterialBlack), AppThemeMaterialBlack);
        initRadioButton(findViewById(R.id.radioButtonMaterialBlue), AppThemeMaterialBlue);
        initRadioButton(findViewById(R.id.radioButtonMaterialRed), AppThemeMaterialRed);
        initRadioButton(findViewById(R.id.radioButtonMaterialDefault), AppThemeMaterialDefault);
        RadioGroup rg = findViewById(R.id.radioButtons);
        ((MaterialRadioButton) rg.getChildAt(getCodeStyle(AppThemeMaterialBlack))).setChecked(true);
    }
// Все инициализации кнопок очень похожи, поэтому создадим метод для переиспользования

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(codeStyle);                                                                                 // сохраним настройки
                recreate();                                                                                             // пересоздадим активити, чтобы тема применилась
            }
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);                        // Работаем через специальный класс сохранения и чтения настроек
        return sharedPref.getInt(AppTheme, codeStyle);                                                                  //Прочитать тему, если настройка не найдена - взять по умолчанию
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
// Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case AppThemeMaterialDefault:
                return R.style.AppTheme;
            case AppThemeMaterialRed:
                return R.style.AppThemeLight;
            case AppThemeMaterialBlue:
                return R.style.AppThemeDark;
            default:
                return R.style.MyCoolStyle;
        }
    }
}