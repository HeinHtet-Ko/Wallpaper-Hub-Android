package com.mtu.ceit.hhk.wallpaperhub.ui.adapters;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.WallpaperActivity;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Category;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {

    private ArrayList<Wallpaper> wpArrayList ;
    private Context mContext;
    public WallpaperAdapter(Context mContext, ArrayList<Wallpaper> list)
    {
        this.mContext = mContext;
        wpArrayList = list ;
    }


    @NonNull
    @Override
    public WallpaperAdapter.WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.wallpaper_item,parent,false);
        return new WallpaperViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final WallpaperViewHolder holder, int position) {

        holder.bindViews();

        final Wallpaper wp = wpArrayList.get(position);
        holder.wp_title.setText(wp.title);
        Glide.with(mContext).load(wp.url).into( holder.wp_photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                new Thread(new Runnable(){
//                    @Override
//                    public void run() {
//                        try {
//                            setWallpaper(wp.url);
//                          //  Toast.makeText(mContext,"Done",Toast.LENGTH_LONG).show();
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                downloadFile(wp.url,"Wallpaper",wp.title+".jpg");


            }
        });


    }


    private void setWallpaper(String url) throws MalformedURLException {

        WallpaperManager manager = WallpaperManager.getInstance(mContext);


        try {
            URL Url = new URL(url);
            Bitmap image = BitmapFactory.decodeStream(Url.openConnection().getInputStream());

               // Toast.makeText(mContext,"Doing",Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(image,null,true,WallpaperManager.FLAG_LOCK);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }

    private void downloadFile(String url,String dir,String ext)
    {
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(dir+"/",ext);

        dm.enqueue(request);
    }


    @Override
    public int getItemCount() {
        return wpArrayList.size();
    }

    static class WallpaperViewHolder extends RecyclerView.ViewHolder
{

    private TextView wp_title;
    private ImageView wp_photo;
    private View itemView;
    public WallpaperViewHolder(@NonNull View itemView) {

        super(itemView);
        this.itemView = itemView;
    }
    public void bindViews()
    {
        wp_title = itemView.findViewById(R.id.wallpaper_item_name);
        wp_photo = itemView.findViewById(R.id.wallpaper_item_photo);
    }
}
}
