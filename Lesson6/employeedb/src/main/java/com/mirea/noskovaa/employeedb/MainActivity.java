package com.mirea.noskovaa.employeedb;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.noskovaa.employeedb.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String TAG = "DatabaseTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = App.getInstance().getDatabase();
        SuperheroDao superheroDao = db.superheroDao();

        // Создаем супергероя
        Superhero hero = new Superhero();
        hero.name = "Spider-Man";
        hero.superpower = "Web-shooting";

        // Запись в базу
        superheroDao.insert(hero);

        // Загрузка всех героев
        List<Superhero> heroes = superheroDao.getAll();

        // Получение героя с id = 1
        Superhero savedHero = superheroDao.getById(1);
        if (savedHero != null) {
            // Обновление
            savedHero.superpower = "Web-shooting and Spidey-sense";
            superheroDao.update(savedHero);

            String result = "Герой: " + savedHero.name + "\nСила: " + savedHero.superpower;
            Log.d(TAG, result);
            binding.tvResult.setText(result);
        }
    }
}