package com.mirea.noskovaa.simplefragmentapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
    }

    public void onClick(View view) {
        Fragment fragment = null;

        if (view.getId() == R.id.btnFirstFragment) {
            fragment = new FirstFragment();
        } else if (view.getId() == R.id.btnSecondFragment) {
            fragment = new SecondFragment();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}