package com.mirea.noskovaa.yandexmaps;

import android.app.Application;
import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private final String MAPKIT_API_KEY = "d1614ab1-8708-456f-83fb-f10cc020c15d";

    @Override
    public void onCreate() {
        super.onCreate();
        // Инициализация ключа должна происходить до инициализации самого MapKitFactory
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}