package ru.mirea.noskovaa.multiactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewActivity(View view) {
        EditText editText = findViewById(R.id.editTextData);
        String textToSend = editText.getText().toString();

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        // Передаем данные (по заданию ФИО)
        intent.putExtra("key", "MIREA - НОСКОВА А. " + textToSend);
        startActivity(intent);
    }
}