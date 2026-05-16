package com.mirea.noskovaa.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.mirea.noskovaa.mireaproject.databinding.FragmentNetworkBinding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkFragment extends Fragment {

    private FragmentNetworkBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNetworkBinding.inflate(inflater, container, false);

        binding.btnFetchData.setOnClickListener(v -> {
            binding.tvNetworkData.setText("Загрузка...");
            fetchData();
        });

        return binding.getRoot();
    }

    private void fetchData() {
        new Thread(() -> {
            try {
                // Публичный API, возвращающий случайный факт в формате JSON
                URL url = new URL("https://official-joke-api.appspot.com/random_joke");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int read;
                    while ((read = inputStream.read()) != -1) {
                        bos.write(read);
                    }
                    bos.close();
                    String result = bos.toString();

                    // Обновляем UI в главном потоке
                    requireActivity().runOnUiThread(() -> binding.tvNetworkData.setText(result));
                } else {
                    requireActivity().runOnUiThread(() -> binding.tvNetworkData.setText("Ошибка сервера"));
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> binding.tvNetworkData.setText("Ошибка сети"));
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}