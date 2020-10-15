package com.mtu.ceit.hhk.wallpaperhub.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentController;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mtu.ceit.hhk.wallpaperhub.R;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences appSettings = getSharedPreferences("appSet", Context.MODE_PRIVATE);
        final Boolean isDark = appSettings.getBoolean("dark",false);


        final SharedPreferences.Editor editor = appSettings.edit();

        if(isDark)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController controller = Navigation.findNavController(this,R.id.my_nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,controller);


    }


}