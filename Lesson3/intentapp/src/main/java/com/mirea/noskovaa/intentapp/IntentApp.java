package com.mirea.noskovaa.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ВНИМАНИЕ: Если ваш xml называется activity_intent_app, поменяйте R.layout.activity_main на R.layout.activity_intent_app
        setContentView(R.layout.activity_intent_app);
    }

    public void sendTime(View view) {
        long dateInMillis = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(new Date(dateInMillis));

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("time", dateString);
        startActivity(intent);
    }
}