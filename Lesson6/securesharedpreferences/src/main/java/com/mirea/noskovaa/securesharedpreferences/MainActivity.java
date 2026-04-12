package com.mirea.noskovaa.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import com.mirea.noskovaa.securesharedpreferences.databinding.ActivityMainBinding;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences secureSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            // Создание мастер-ключа
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Инициализация зашифрованных SharedPreferences
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Загрузка данных
            binding.etPoet.setText(secureSharedPreferences.getString("POET", ""));

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        binding.btnSaveSecure.setOnClickListener(v -> {
            secureSharedPreferences.edit().putString("POET", binding.etPoet.getText().toString()).apply();
            Toast.makeText(this, "Поэт надежно сохранен!", Toast.LENGTH_SHORT).show();
        });
    }
}