package ru.mirea.noskovaa.buttonclicker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ButtonClicker extends AppCompatActivity {

    // Объявляем переменные
    private TextView tvOut;
    private Button btnWhoAmI;
    private Button btnItIsNotMe;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Связываем переменные с элементами на экране по их ID
        tvOut = findViewById(R.id.tvOut);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        checkBox = findViewById(R.id.checkBox);

        // СПОСОБ 1: Создаем обработчик события для первой кнопки
        View.OnClickListener oclBtnWhoAmI = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст
                tvOut.setText("Мой номер по списку № 1"); // Вместо 1 впишите свой номер
                // Меняем состояние чекбокса на противоположное
                checkBox.setChecked(!checkBox.isChecked());
            }
        };

        // Назначаем обработчик кнопке
        btnWhoAmI.setOnClickListener(oclBtnWhoAmI);
    }

    // СПОСОБ 2: Метод для второй кнопки (android:onClick="onMyButtonClick")
    public void onMyButtonClick(View view) {
        // Меняем текст
        tvOut.setText("Это не я сделал");
        // Меняем состояние чекбокса на противоположное
        checkBox.setChecked(!checkBox.isChecked());

        // Выводим всплывающее сообщение (Toast)
        Toast.makeText(this, "Ещё один способ!", Toast.LENGTH_SHORT).show();
    }
}