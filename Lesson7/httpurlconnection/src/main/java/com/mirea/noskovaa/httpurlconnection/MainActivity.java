package com.mirea.noskovaa.httpurlconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.noskovaa.httpurlconnection.databinding.ActivityMainBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoad.setOnClickListener(v -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = null;
            if (connectivityManager != null) {
                networkinfo = connectivityManager.getActiveNetworkInfo();
            }
            if (networkinfo != null && networkinfo.isConnected()) {
                // Запускаем поток для скачивания данных
                fetchDataInBackground();
            } else {
                Toast.makeText(this, "Нет интернета", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataInBackground() {
        binding.tvWeather.setText("Загружаем...");

        new Thread(() -> {
            try {
                // 1. Получаем данные о местоположении
                String ipInfoJson = downloadData("https://ipinfo.io/json");
                JSONObject responseJson = new JSONObject(ipInfoJson);

                String ip = responseJson.getString("ip");
                String city = responseJson.getString("city");
                String region = responseJson.getString("region");
                String loc = responseJson.getString("loc"); // Формат "Широта,Долгота"

                String[] coords = loc.split(",");
                String latitude = coords[0];
                String longitude = coords[1];

                // 2. Получаем погоду по координатам
                String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";
                String weatherJsonStr = downloadData(weatherUrl);
                JSONObject weatherJson = new JSONObject(weatherJsonStr);
                JSONObject currentWeather = weatherJson.getJSONObject("current_weather");
                String temperature = currentWeather.getString("temperature");

                // 3. Обновляем UI в главном потоке
                runOnUiThread(() -> {
                    binding.tvIp.setText("IP: " + ip);
                    binding.tvCity.setText("Город: " + city);
                    binding.tvRegion.setText("Регион: " + region);
                    binding.tvWeather.setText("Погода: " + temperature + " °C");
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private String downloadData(String address) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                bos.close();
                data = bos.toString();
            } else {
                data = connection.getResponseMessage() + ". Error Code: " + responseCode;
            }
            connection.disconnect();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }
}