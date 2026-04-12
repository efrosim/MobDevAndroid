package com.mirea.noskovaa.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.noskovaa.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализация SharedPreferences
        sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);

        // Загрузка сохраненных данных при запуске
        binding.etGroup.setText(sharedPref.getString("GROUP", ""));
        binding.etNumber.setText(String.valueOf(sharedPref.getInt("NUMBER", 0)));
        binding.etMovie.setText(sharedPref.getString("MOVIE", ""));

        // Сохранение данных по кнопке
        binding.btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("GROUP", binding.etGroup.getText().toString());

            String numberStr = binding.etNumber.getText().toString();
            int number = numberStr.isEmpty() ? 0 : Integer.parseInt(numberStr);
            editor.putInt("NUMBER", number);

            editor.putString("MOVIE", binding.etMovie.getText().toString());
            editor.apply(); // Асинхронное сохранение

            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
        });
    }
}