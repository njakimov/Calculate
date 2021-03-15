package com.calculate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class SettingsActivity extends AppCompatActivity {

    public static final String NAME_ACTIVITY = "SettingsActivity";

    // Имя настроек
    private static final String GenericPreference = "CALC";
    private static final String SettingsPreference = "SETTINGS";
    // Имя параметра в настройках
    private static final String AppTheme = "APP_THEME";
    private static final int AppThemeMaterialBlack = 0;
    private static final int AppThemeMaterialRed = 1;
    private static final int AppThemeMaterialDefault = 2;
    private static final int AppThemeMaterialBlue = 3;

    private int codeToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        codeToSave = getCodeStyle(GenericPreference, 0);
        if (codeToSave != getCodeStyle(SettingsPreference, 0)) {
            codeToSave = getCodeStyle(SettingsPreference, 0);
            setTheme(getAppTheme(SettingsPreference, 0));
        } else {
            setTheme(getAppTheme(GenericPreference, 0));
        }

        setContentView(R.layout.activity_settings);
        initThemeChooser();

        Button btSaveSettings = findViewById(R.id.btnSaveSettings);
        btSaveSettings.setOnClickListener((view) -> {
            saveTheme(SettingsPreference, codeToSave);
            saveTheme(GenericPreference, codeToSave);
            Intent result = new Intent();
            result.putExtra(CalcActivity.KEY_NAME, "ok");
            setResult(RESULT_OK, result);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(NAME_ACTIVITY, "onDestroy() ");
    }

    private int getAppTheme(String NameSharedPreference, int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(NameSharedPreference, codeStyle));
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(String NameSharedPreference, int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);                        // Работаем через специальный класс сохранения и чтения настроек
        return sharedPref.getInt(AppTheme, codeStyle);                                                                  //Прочитать тему, если настройка не найдена - взять по умолчанию
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        codeToSave = codeStyle;
        saveTheme(SettingsPreference, codeToSave);
        recreate();
    }

    private void saveTheme(String NameSharedPreference, int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();                                                            // Настройки сохраняются посредством специального класса editor.
        editor.putInt(AppTheme, codeStyle);
        editor.commit();
    }

    public static int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case AppThemeMaterialDefault:
                return R.style.AppThemeOrange;
            case AppThemeMaterialRed:
                return R.style.AppThemeLight;
            case AppThemeMaterialBlue:
                return R.style.AppThemeDark;
            case AppThemeMaterialBlack:
                return R.style.MyCoolStyle;
            default:
                return R.style.AppThemeOrange;
        }
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMaterialBlack), AppThemeMaterialBlack);
        initRadioButton(findViewById(R.id.radioButtonMaterialBlue), AppThemeMaterialBlue);
        initRadioButton(findViewById(R.id.radioButtonMaterialRed), AppThemeMaterialRed);
        initRadioButton(findViewById(R.id.radioButtonMaterialDefault), AppThemeMaterialDefault);
        RadioGroup rg = findViewById(R.id.radioButtons);
        ((MaterialRadioButton) rg.getChildAt(codeToSave)).setChecked(true);
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(v -> {
            setAppTheme(codeStyle);                                                                                 // сохраним настройки
        });
    }
}