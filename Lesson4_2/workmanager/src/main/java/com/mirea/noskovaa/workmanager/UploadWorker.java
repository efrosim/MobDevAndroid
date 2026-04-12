package com.mirea.noskovaa.workmanager;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.concurrent.TimeUnit;

public class UploadWorker extends Worker {
    static final String TAG = "UploadWorker";

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: start");

        try {
            // Имитация долгой фоновой операции
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Log.d(TAG, "doWork: interrupted!");
            // Если поток прервали, значит WorkManager отменил задачу.
            // Прекращаем работу и возвращаем retry() (хотя WorkManager сам решит, перезапускать ли её)
            return Result.retry();
        }

        // Дополнительная проверка: не была ли задача отменена
        if (isStopped()) {
            Log.d(TAG, "doWork: stopped by WorkManager!");
            return Result.retry();
        }

        Log.d(TAG, "doWork: end");
        return Result.success();
    }
}