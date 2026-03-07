package ru.mirea.noskovaa.multiactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.textViewData);

        // Получаем данные из Intent
        String text = getIntent().getStringExtra("key");
        if (text != null) {
            textView.setText(text);
        }
    }
}