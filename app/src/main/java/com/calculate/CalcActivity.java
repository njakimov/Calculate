package com.calculate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CalcActivity extends AppCompatActivity {

    //  1. Напишите обработку каждой кнопки из макета калькулятора.
    //  2. Создайте объект с данными и операциями калькулятора. Продумайте, каким образом будете
    //      хранить введённые пользователем данные.
    //  3. * Создайте макет калькулятора для горизонтальной ориентации экрана и отображайте его в
    //      ландшафтной ориентации.


    // lesson4
    //  1. Переделайте все кнопки на материал.
    //  2. Все размеры и строки сделайте ресурсами.
    //  3. Создайте стиль для своего приложения.
    //  4. * Создайте светлую и тёмную тему для приложения.

    public static final String NAME_ACTIVITY = "CalcActivity";

    public static final String KEY_FIRST = NAME_ACTIVITY + ".mFirst";
    public static final String KEY_SECOND = NAME_ACTIVITY + ".mSecond";
    public static final String KEY_RESULT = NAME_ACTIVITY + ".mResult";
    public static final String KEY_OPERATION = NAME_ACTIVITY + ".mOperation";
    public static final String KEY_TV_RESULT = NAME_ACTIVITY + ".tvResult";

    public static final String KEY_NAME = "our.prefix.user.name";

    private String mFirst = "";
    private String mSecond = "";
    private String tvResultText = "0";
    private float mResult = 0;
    private char mOperation = ' ';
    TextView tvResult;
    Intent mItMoveToTest;

    // Имя настроек
    private static final String NameSharedPreference = "CALC";
    private static final String appTheme = "APP_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(-1));
        setContentView(R.layout.activity_calc);

        Button key0 = findViewById(R.id.key_0);
        Button key1 = findViewById(R.id.key_1);
        Button key2 = findViewById(R.id.key_2);
        Button key3 = findViewById(R.id.key_3);
        Button key4 = findViewById(R.id.key_4);
        Button key5 = findViewById(R.id.key_5);
        Button key6 = findViewById(R.id.key_6);
        Button key7 = findViewById(R.id.key_7);
        Button key8 = findViewById(R.id.key_8);
        Button key9 = findViewById(R.id.key_9);
        Button keyC = findViewById(R.id.key_c);
        Button keyMulti = findViewById(R.id.key_multi);
        Button keyDivision = findViewById(R.id.key_division);
        Button keyBack = findViewById(R.id.key_back);
        Button keyMinus = findViewById(R.id.key_minus);
        Button keySum = findViewById(R.id.key_sum);
        Button keyResult = findViewById(R.id.key_result);
        Button keyPoint = findViewById(R.id.key_point);
        tvResult = findViewById(R.id.tvResult);
        setTvResult("0");

        key0.setOnClickListener(key0ClickListener);
        key1.setOnClickListener(key1ClickListener);
        key2.setOnClickListener(key2ClickListener);
        key3.setOnClickListener(key3ClickListener);
        key4.setOnClickListener(key4ClickListener);
        key5.setOnClickListener(key5ClickListener);
        key6.setOnClickListener(key6ClickListener);
        key7.setOnClickListener(key7ClickListener);
        key8.setOnClickListener(key8ClickListener);
        key9.setOnClickListener(key9ClickListener);
        keyC.setOnClickListener(keyCClickListener);
        keyMulti.setOnClickListener(keyMultiClickListener);
        keyDivision.setOnClickListener(keyDivisionClickListener);
        keyBack.setOnClickListener(keyBackClickListener);
        keyMinus.setOnClickListener(keyMinusClickListener);
        keySum.setOnClickListener(keySumClickListener);
        keyResult.setOnClickListener(keyResultClickListener);
        keyPoint.setOnClickListener(keyPointClickListener);

        mItMoveToTest = new Intent(this, SettingsActivity.class);

        Button btnMoveToTest = findViewById(R.id.btnMoveToTest);
        btnMoveToTest.setOnClickListener(moveToTestClickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(NAME_ACTIVITY, "onActivityResult() ");
        if (data != null && resultCode == RESULT_OK) {
            recreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(NAME_ACTIVITY, "onStart() ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(NAME_ACTIVITY, "onRestart() ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(NAME_ACTIVITY, "onResume() ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(NAME_ACTIVITY, "onPause() ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(NAME_ACTIVITY, "onStop() ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(NAME_ACTIVITY, "onDestroy() ");
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putChar(KEY_OPERATION, mOperation);
        state.putFloat(KEY_RESULT, mResult);
        state.putString(KEY_SECOND, mSecond);
        state.putString(KEY_FIRST, mFirst);
        state.putString(KEY_TV_RESULT, tvResultText);

    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mOperation = state.getChar(KEY_OPERATION);
        mResult = state.getFloat(KEY_RESULT);
        mSecond = state.getString(KEY_SECOND);
        mFirst = state.getString(KEY_FIRST);
        setTvResult(state.getString(KEY_TV_RESULT));
    }

    private final View.OnClickListener moveToTestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivityForResult(mItMoveToTest, 1);
        }
    };

    private final View.OnClickListener keyCClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initCalc();
        }
    };

    private final View.OnClickListener key0ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("0");
        }
    };

    private final View.OnClickListener key1ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("1");
        }
    };

    private final View.OnClickListener key2ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("2");
        }
    };

    private final View.OnClickListener key3ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("3");
        }
    };

    private final View.OnClickListener key4ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("4");
        }
    };

    private final View.OnClickListener key5ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("5");
        }
    };

    private final View.OnClickListener key6ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("6");
        }
    };

    private final View.OnClickListener key7ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("7");
        }
    };

    private final View.OnClickListener key8ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("8");
        }
    };

    private final View.OnClickListener key9ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam("9");
        }
    };

    private final View.OnClickListener keyPointClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToParam(".");
        }
    };

    private final View.OnClickListener keySumClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOperation == '!' || mOperation != ' ') {
                return;
            } else if (mFirst.contains("-") && mFirst.length()==1) {
                initCalc();
            } else {
                mOperation = '+';
                addCharToText(" + ");
            }
        }
    };

    private final View.OnClickListener keyMinusClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOperation == '!' || mOperation != ' ') {
                return;
            } else if (mFirst.equals("")) {
                addCharToParam("-");
            } else {
                mOperation = '-';
                addCharToText(" - ");
            }
        }
    };

    private final View.OnClickListener keyDivisionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOperation == '!' || mOperation != ' ' || mFirst.equals("")) return;
            mOperation = '/';
            addCharToText(" / ");
        }
    };

    private final View.OnClickListener keyMultiClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOperation == '!' || mOperation != ' ' || mFirst.equals("")) return;

            mOperation = '*';
            addCharToText(" * ");
        }
    };

    private final View.OnClickListener keyBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOperation == '!') return;

            if (tvResultText.contains("=")) return;

            if (tvResultText.length() == 1) {
                mFirst = "";
                setTvResult("0");
            } else if (
                    tvResultText.charAt(tvResultText.length() - 1) != '+' && tvResultText.charAt(tvResultText.length() - 1) != '*' &&
                            tvResultText.charAt(tvResultText.length() - 1) != '/' && tvResultText.charAt(tvResultText.length() - 1) != '-'
            ) {
                if (mOperation == ' ') {
                    if (!mFirst.equals("")) {
                        mFirst = mFirst.substring(0, mFirst.length() - 1);
                    }
                } else {
                    if (!mSecond.equals("")) {
                        mSecond = mSecond.substring(0, mSecond.length() - 1);
                    }
                }
                setTvResult(tvResultText.substring(0, tvResultText.length() - 1));
            } else {
                mOperation = ' ';
                mSecond = "";
                setTvResult(tvResultText.substring(0, tvResultText.length() - 1));
            }
        }
    };

    private final View.OnClickListener keyResultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOperation == '!') return;

            String resultText = "";
            try {
                if (!mFirst.equals("") && !mSecond.equals("")) {
                    float first = Float.parseFloat(mFirst);
                    float second = Float.parseFloat(mSecond);

                    if (mOperation != ' ' && tvResultText.contains("=")) {
                        first = mResult;
                        mFirst = "" + mResult;
                        setTvResult("" + splitZero("" + mResult) + mOperation + mSecond);
                    }
                    addCharToText(" = ");

                    switch (mOperation) {
                        case '+':
                            mResult = first + second;
                            break;
                        case '-':
                            mResult = first - second;
                            break;
                        case '*':
                            mResult = first * second;
                            break;
                        case '/':
                            if (mSecond.equals("0")) {
                                throw new ArithmeticException("Деление на 0");
                            }
                            mResult = first / second;
                            break;
                        default:
                            mResult = 0;
                    }
                    resultText = "" + mResult;
                    if (Float.isInfinite(mResult)) {
                        throw new ArithmeticException("Переполнение");
                    }
                    resultText = splitZero(resultText);

                }

                addCharToText(resultText);
            } catch (Exception ex) {
                addCharToText(" : " + ex.getMessage());
                mOperation = '!';
            }


        }
    };

    /**
     * Метод удаления 0, точек и запятых
     *
     * @param resultText - строка на обработку
     * @return обработанная строка
     */
    private String splitZero(String resultText) {
        boolean exist0 = false;
        if (resultText.contains(".") || resultText.contains(",")) {
            exist0 = true;
        }
        while (exist0) {
            if (resultText.charAt(resultText.length() - 1) == '0' && resultText.length() != 1) {
                resultText = resultText.substring(0, resultText.length() - 1);
            } else if (resultText.charAt(resultText.length() - 1) == ',' || resultText.charAt(resultText.length() - 1) == '.') {
                resultText = resultText.substring(0, resultText.length() - 1);
                exist0 = false;
            } else {
                exist0 = false;
            }
        }
        return resultText;
    }

    /**
     * Метод добавления символа к параметру
     *
     * @param key - добавляемый символ
     */
    private void addCharToParam(String key) {
        if (tvResultText.contains("=")) {
            initCalc();
        }
        if (mOperation == '!') {
            return;
        } else if (mOperation == ' ') {
            if ((!key.equals(".") || !mFirst.contains(".")) && mFirst.length() < 15) {
                if (mFirst.length() == 0 && key.equals(".")) key = "0" + key;
                mFirst += key;
                addCharToText(key);
            }
        } else {
            if ((!key.equals(".") || !mSecond.contains(".")) && mSecond.length() < 15) {
                if (mSecond.length() == 0 && key.equals(".")) key = "0" + key;
                mSecond += key;
                addCharToText(key);
            }
        }


    }

    /**
     * Метод инициализации результирующего поля
     *
     * @param key - добавляемый символ
     */
    private void addCharToText(String key) {

        if (tvResult.getText() == "0") {
            setTvResult(key);
        } else {
            setTvResult(tvResult.getText() + key);
        }
    }

    /**
     * Метод заполнения итогового текстового поля
     *
     * @param text - результат для записи в итоговое текстовое поле
     */
    private void setTvResult(String text) {
        tvResultText = text;
        tvResult.setText(text);
    }

    /**
     * Метод инициализации калькулятора старновыми значениями
     */
    private void initCalc() {
        mFirst = "";
        mSecond = "";
        mResult = 0f;
        mOperation = ' ';
        setTvResult("0");
    }

    private int getAppTheme(int codeStyle) {
        return SettingsActivity.codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);                        // Работаем через специальный класс сохранения и чтения настроек
        return sharedPref.getInt(appTheme, codeStyle);                                                                  //Прочитать тему, если настройка не найдена - взять по умолчанию
    }

}