package com.mirea.noskovaa.mireaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mirea.noskovaa.mireaproject.databinding.FragmentFileOperationsBinding;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperationsFragment extends Fragment {

    private FragmentFileOperationsBinding binding;
    private final String FILE_NAME = "secret_record.txt";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFileOperationsBinding.inflate(inflater, container, false);

        // Загружаем данные при открытии фрагмента
        loadFile();

        // Обработка нажатия на Floating Action Button
        binding.fabAddRecord.setOnClickListener(v -> showAddRecordDialog());

        return binding.getRoot();
    }

    private void showAddRecordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Новая секретная запись");

        final EditText input = new EditText(requireContext());
        input.setHint("Введите текст для шифрования");
        builder.setView(input);

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String textToSave = input.getText().toString();
            saveFileEncrypted(textToSave);
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveFileEncrypted(String text) {
        try {
            // Простейшая криптография: кодируем строку в Base64
            String encryptedText = Base64.encodeToString(text.getBytes(), Base64.DEFAULT);

            FileOutputStream fos = requireActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(encryptedText.getBytes());
            fos.close();

            Toast.makeText(requireContext(), "Запись зашифрована и сохранена", Toast.LENGTH_SHORT).show();
            loadFile(); // Обновляем экран
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFile() {
        try {
            FileInputStream fis = requireActivity().openFileInput(FILE_NAME);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();

            String encryptedText = new String(bytes);

            // Расшифровываем из Base64
            String decryptedText = new String(Base64.decode(encryptedText, Base64.DEFAULT));

            binding.tvFileContent.setText("Зашифровано:\n" + encryptedText + "\n\nРасшифровано:\n" + decryptedText);
        } catch (Exception e) {
            binding.tvFileContent.setText("Файл пуст или произошла ошибка");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}