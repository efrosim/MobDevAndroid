package com.mirea.noskovaa.mireaproject;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.concurrent.TimeUnit;

public class MyWorker extends Worker {
    static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: start");
        try {
            // Имитация долгой фоновой задачи (10 секунд)
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Log.d(TAG, "doWork: interrupted!");
            return Result.retry();
        }
        Log.d(TAG, "doWork: end");
        return Result.success();
    }
}