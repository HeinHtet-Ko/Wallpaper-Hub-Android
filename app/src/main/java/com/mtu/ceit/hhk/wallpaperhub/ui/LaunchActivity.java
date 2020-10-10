package com.mtu.ceit.hhk.wallpaperhub.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i  = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
