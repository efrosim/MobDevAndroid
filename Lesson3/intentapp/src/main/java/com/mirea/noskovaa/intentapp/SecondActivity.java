package com.mirea.noskovaa.intentapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.textView);
        String time = getIntent().getStringExtra("time");

        // ВАЖНО: Замените цифру 1 на ваш номер по списку
        int myNumber = 18;
        int square = myNumber * myNumber;

        String text = String.format("КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ %d, а текущее время %s", square, time);
        textView.setText(text);
    }
}