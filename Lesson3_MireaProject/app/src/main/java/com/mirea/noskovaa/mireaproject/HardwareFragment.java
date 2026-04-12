package com.mirea.noskovaa.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.mirea.noskovaa.mireaproject.databinding.FragmentHardwareBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HardwareFragment extends Fragment implements SensorEventListener {

    private FragmentHardwareBinding binding;

    // Для камеры
    private Uri imageUri;
    private ActivityResultLauncher<Intent> cameraLauncher;

    // Для аудио
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String audioFilePath;
    private boolean isRecording = false;
    private boolean isPlaying = false;

    // Для датчика
    private SensorManager sensorManager;
    private Sensor accelerometer;

    // Современный инструмент для запроса разрешений
    private ActivityResultLauncher<String[]> permissionsLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHardwareBinding.inflate(inflater, container, false);

        // 1. Инициализация лаунчера разрешений
        permissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            Boolean cameraGranted = result.getOrDefault(Manifest.permission.CAMERA, false);
            Boolean audioGranted = result.getOrDefault(Manifest.permission.RECORD_AUDIO, false);

            if (cameraGranted != null && cameraGranted && audioGranted != null && audioGranted) {
                Toast.makeText(requireContext(), "Разрешения получены!", Toast.LENGTH_SHORT).show();
            }
        });

        // Запрашиваем разрешения при открытии экрана
        requestPermissionsIfNeeded();

        // 2. Инициализация камеры
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        binding.imageViewProfile.setImageURI(imageUri);
                    }
                }
        );

        binding.btnTakePhoto.setOnClickListener(v -> {
            // Проверяем разрешение именно на камеру перед запуском
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                permissionsLauncher.launch(new String[]{Manifest.permission.CAMERA});
            }
        });

        // 3. Инициализация аудио
        audioFilePath = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audiorecord.3gp").getAbsolutePath();

        binding.btnRecordAudio.setOnClickListener(v -> {
            // Проверяем разрешение на микрофон перед запуском
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                if (isRecording) {
                    stopRecording();
                } else {
                    startRecording();
                }
            } else {
                permissionsLauncher.launch(new String[]{Manifest.permission.RECORD_AUDIO});
            }
        });

        binding.btnPlayAudio.setOnClickListener(v -> {
            if (isPlaying) {
                stopPlaying();
            } else {
                startPlaying();
            }
        });

        // 4. Инициализация датчика
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        return binding.getRoot();
    }

    private void requestPermissionsIfNeeded() {
        boolean needCamera = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        boolean needAudio = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED;

        if (needCamera || needAudio) {
            permissionsLauncher.launch(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            });
        }
    }

    // --- КАМЕРА ---
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File photoFile = createImageFile();
            String authorities = requireContext().getPackageName() + ".fileprovider";
            imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraLauncher.launch(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    // --- АУДИО ---
    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            binding.btnRecordAudio.setText("Остановить запись");
            binding.btnPlayAudio.setEnabled(false);
        } catch (IOException e) {
            Log.e("AudioRecord", "prepare() failed");
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            binding.btnRecordAudio.setText("Запись");
            binding.btnPlayAudio.setEnabled(true);
        }
    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlaying = true;
            binding.btnPlayAudio.setText("Остановить");
            binding.btnRecordAudio.setEnabled(false);

            mediaPlayer.setOnCompletionListener(mp -> stopPlaying());
        } catch (IOException e) {
            Log.e("AudioPlay", "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
            binding.btnPlayAudio.setText("Слушать");
            binding.btnRecordAudio.setEnabled(true);
        }
    }

    // --- ДАТЧИКИ ---
    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
        if (isRecording) stopRecording();
        if (isPlaying) stopPlaying();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            binding.tvSensorData.setText(String.format(Locale.getDefault(), "Наклон устройства:\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}