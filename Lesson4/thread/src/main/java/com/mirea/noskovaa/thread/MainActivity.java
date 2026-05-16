package com.mirea.noskovaa.thread;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mirea.noskovaa.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Информация о главном потоке
        Thread mainThread = Thread.currentThread();
        binding.tvInfo.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-08-23, НОМЕР ПО СПИСКУ: 18, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Да");
        binding.tvInfo.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int pairs = Integer.parseInt(binding.etTotalPairs.getText().toString());
                            int days = Integer.parseInt(binding.etStudyDays.getText().toString());

                            // Имитация долгих вычислений
                            Thread.sleep(2000);

                            final double result = (double) pairs / days;

                            // Возврат в UI поток для обновления интерфейса
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.tvResult.setText("Среднее кол-во пар в день: " + String.format("%.2f", result));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}