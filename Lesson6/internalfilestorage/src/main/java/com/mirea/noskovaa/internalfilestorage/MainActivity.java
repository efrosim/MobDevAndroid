package com.mirea.noskovaa.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.noskovaa.internalfilestorage.databinding.ActivityMainBinding;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName = "history_date.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSaveFile.setOnClickListener(v -> {
            String data = binding.etDate.getText().toString() + " - " + binding.etDescription.getText().toString();
            try {
                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.close();
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Чтение файла в отдельном потоке с задержкой (по методичке)
        new Thread(() -> {
            try {
                Thread.sleep(10000); // Ждем 5 секунд
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            binding.tvResult.post(() -> binding.tvResult.setText(getTextFromFile()));
        }).start();
    }

    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            return "Файл пока пуст или не создан";
        } finally {
            try {
                if (fin != null) fin.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}