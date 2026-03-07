package ru.mirea.noskovaa.toastapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void countChars(View view) {
        EditText editText = findViewById(R.id.editTextString);
        int length = editText.getText().toString().length();
        // номер нарандом написал
        String message = "СТУДЕНТ № 18 ГРУППА БСБО-08-23 Количество символов - " + length;

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}