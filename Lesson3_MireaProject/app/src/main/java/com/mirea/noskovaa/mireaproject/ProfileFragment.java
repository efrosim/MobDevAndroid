package com.mirea.noskovaa.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mirea.noskovaa.mireaproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        // Инициализация SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("mirea_profile", Context.MODE_PRIVATE);

        // Загрузка сохраненных данных
        binding.etProfileName.setText(sharedPreferences.getString("NAME", ""));
        binding.etProfileAge.setText(String.valueOf(sharedPreferences.getInt("AGE", 0)));
        binding.etProfileHobby.setText(sharedPreferences.getString("HOBBY", ""));

        // Сохранение данных
        binding.btnSaveProfile.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("NAME", binding.etProfileName.getText().toString());

            String ageStr = binding.etProfileAge.getText().toString();
            int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
            editor.putInt("AGE", age);

            editor.putString("HOBBY", binding.etProfileHobby.getText().toString());
            editor.apply();

            Toast.makeText(requireContext(), "Профиль сохранен!", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}