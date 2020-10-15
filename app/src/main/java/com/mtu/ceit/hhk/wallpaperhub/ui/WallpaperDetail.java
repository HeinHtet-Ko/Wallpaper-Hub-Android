package com.mtu.ceit.hhk.wallpaperhub.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.favouriteDb.FavouriteDAO;
import com.mtu.ceit.hhk.wallpaperhub.ui.favouriteDb.FavouriteDatabase;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.IOException;

public class WallpaperDetail extends AppCompatActivity {

    private ImageView wp_photo;
    private TextView title_textview;
    private FloatingActionButton fab_set, fab_fav, fab_download;
    private Wallpaper wp;
    private FloatingActionMenu fab_menu;
    private ProgressDialog pd;
    private Bitmap bm;
    private LottieAnimationView lv;
    private FavouriteDatabase fvDb;
    private FavouriteDAO fvDao;
    private Boolean isFav = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_detail);
        wp = (Wallpaper) getIntent().getSerializableExtra("wp");
        title_textview = findViewById(R.id.title_text);
        fab_set = findViewById(R.id.fab_set);
        fab_menu = findViewById(R.id.menu);
        fab_download = findViewById(R.id.fab_download);
        fab_fav = findViewById(R.id.fab_fav);
        pd = new ProgressDialog(this);
        pd.setMessage("Setting Up Your Wallpaper.....");
        pd.setCanceledOnTouchOutside(false);
        title_textview.append(wp.title);
        wp_photo = findViewById(R.id.wp_detail_photo);
        lv = findViewById(R.id.detail_ld_view);
        fvDb = FavouriteDatabase.getDatabase(getApplicationContext());

        fvDao = fvDb.getFavDao();


        setUpGlide();
        fabListeners();



        Thread timer = new Thread() {
            @Override
            public void run() {

                for(Wallpaper w:fvDao.getAllWallpapers())
                {
                    if(w.url.equals(wp.url))
                    {

                        Log.d("TAG", "run: "+w.url);

                        isFav = true;
                        break;
                    }
                    else {
                        Log.d("TAG", "don'trun: "+w.url);
                        isFav=false;
                    }
                }

                WallpaperDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isFav)
                        {
                            fab_fav.setColorNormalResId(R.color.colorFavOn);
                           //  Toast.makeText(getApplicationContext(),"Remove",Toast.LENGTH_LONG).show();
                         //   Toast.makeText(getApplicationContext(),"Remove from Favourites",Toast.LENGTH_LONG).show();
                            //fab_fav.setLabelText(" Remove from Favourites");
                        }

                        else{

                            fab_fav.setColorNormalResId(R.color.colorFavOff);
                           // fab_fav.setLabelText(" Add to Favourites ");

                        }

                    }
                });

            }

        };
        timer.start();







    }


    private void setUpGlide() {

        Glide.with(this)
                .asBitmap()
                .load(wp.url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        wp_photo.setImageBitmap(resource);
                        lv.setVisibility(View.GONE);
                        bm = resource;

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }


                });
    }

    private void fabListeners() {


        fab_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        setHomeWallpaper(bm);

                    }
                }.start();
            }
        });
        fab_download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View vw) {
                Permissions.check(WallpaperDetail.this/*context*/, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        Snackbar.make(vw,"Download Started",2000).show();
                        downloadFile(wp.url, "WallpaperHub", wp.title + ".jpg");
                        fab_menu.close(true);
                    }
                });

            }
        });


        fab_fav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(isFav)
                {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fvDao.deleteWallpaper(wp);
                            isFav = false;
                        }
                    }).start();

                    WallpaperDetail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Toast.makeText(getApplicationContext(),"Remove from Favourites",Toast.LENGTH_LONG).show();
                            fab_fav.setColorNormalResId(R.color.colorFavOff);
                            fab_menu.close(true);
                           // fab_fav.setLabelText("Add to Favourites");

                        }
                    });

                }else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fvDao.insertWallpaper(wp);
                            isFav = true;
                        }
                    }).start();

                    WallpaperDetail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            fab_fav.setColorNormalResId(R.color.colorFavOn);
                            fab_menu.close(true);
                            Toast.makeText(getApplicationContext(),"Added to Favourites",Toast.LENGTH_LONG)

                                    .show();
                           // fab_fav.setLabelText("Remove from Favourites");

                        }
                    });

                }



            }
        });
    }

    private void downloadFile(String url, String dir, String ext) {
        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(dir + "/", ext);
        dm.enqueue(request);


    }

    private void setHomeWallpaper(Bitmap bitmap) {


        WallpaperDetail.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                pd.show();

            }
        });


        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());


        try {

//            URL Url = new URL(url);
//            Bitmap image = BitmapFactory.decodeStream(Url.openConnection().getInputStream());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                WallpaperDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pd.dismiss();
                        fab_menu.close(true);
                        Snackbar.make(fab_set," Successfully Set Your Wallpaper Up",2000).show();
                    }
                });
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}