package com.mirea.noskovaa.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FavoriteBook extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    public static final String USER_MESSAGE = "MESSAGE";
    private TextView textViewUserBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_book);

        textViewUserBook = findViewById(R.id.textViewBook);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String userBook = data.getStringExtra(USER_MESSAGE);
                            textViewUserBook.setText("Название Вашей любимой книги: " + userBook);
                        }
                    }
                }
        );
    }

    public void openInputScreen(View view) {
        Intent intent = new Intent(this, ShareActivity.class);
        activityResultLauncher.launch(intent);
    }
}