package com.mirea.noskovaa.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.noskovaa.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. Result: " + msg.getData().getString("result"));
            }
        };

        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("AGE", Integer.parseInt(binding.etAge.getText().toString()));
                bundle.putString("JOB", binding.etJob.getText().toString());
                msg.setData(bundle);
                if (myLooper != null && myLooper.mHandler != null) {
                    myLooper.mHandler.sendMessage(msg);
                }
            }
        });
    }
}