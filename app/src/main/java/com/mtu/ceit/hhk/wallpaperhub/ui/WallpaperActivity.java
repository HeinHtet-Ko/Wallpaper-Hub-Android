package com.mtu.ceit.hhk.wallpaperhub.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.adapters.WallpaperAdapter;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;

import java.util.ArrayList;

public class WallpaperActivity extends AppCompatActivity {

    private RecyclerView wpRecycler;
    private DatabaseReference wpDbRef;
    private ArrayList<Wallpaper> wpList = new ArrayList<>();
    private WallpaperAdapter wpAdapter;
    LottieAnimationView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        Intent i = getIntent();
        String cate_name = i.getStringExtra("category");
        wpDbRef = FirebaseDatabase.getInstance().getReference("Images").child(cate_name);
        lv = findViewById(R.id.wp_ld_view);

        recyclerSetup();
        readDB();

    }


    private void readDB()
    {
        wpDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


             //   lv.setVisibility(View.GONE);
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Wallpaper wp = ds.getValue(Wallpaper.class);
                    wpList.add(wp);
                }
                lv.setVisibility(View.GONE);
                wpAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  void recyclerSetup()
    {
     wpRecycler = findViewById(R.id.wallpaper_recycler);
     GridLayoutManager gl = (GridLayoutManager) wpRecycler.getLayoutManager();
     gl.setSpanCount(2);
     wpAdapter = new WallpaperAdapter(WallpaperActivity.this,wpList);
     wpRecycler.setHasFixedSize(true);
     wpRecycler.setAdapter(wpAdapter);
    }
}