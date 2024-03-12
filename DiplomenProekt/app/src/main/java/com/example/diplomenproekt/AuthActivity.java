package com.example.diplomenproekt;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diplomenproekt.databinding.AuthActivityBinding;

public class AuthActivity extends AppCompatActivity {
    private AuthActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        binding = AuthActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
