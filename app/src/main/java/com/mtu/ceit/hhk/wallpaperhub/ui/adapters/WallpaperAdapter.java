package com.mtu.ceit.hhk.wallpaperhub.ui.adapters;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.WallpaperActivity;
import com.mtu.ceit.hhk.wallpaperhub.ui.WallpaperDetail;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Category;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;
import com.squareup.picasso.Picasso;

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
       // holder.wp_title.setText(wp.title);

        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .override(350, 350);
        Glide.with(mContext)
                .asBitmap()
                .apply(myOptions)
                .load(wp.url).into( holder.wp_photo);

//        Picasso
//                .get()
//                .load(wp.url)
//                .resize(300, 300)
//                .centerInside()
//                .into(holder.wp_photo);

//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(mContext)
//
//                .setDownsampleEnabled(true)
//                .build();
//        ImageRequest request = ImageRequestBuilder
//                .newBuilderWithSource(Uri.parse(wp.url))
//                .setAutoRotateEnabled(true)
//                .setLocalThumbnailPreviewsEnabled(true)
//                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
//                .setProgressiveRenderingEnabled(false)
//                .setResizeOptions(new ResizeOptions(300, 400))
//                .build();
//
//        Fresco.initialize(mContext, config);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mContext, WallpaperDetail.class);

                i.putExtra("wp",wp);
                mContext.startActivity(i);




            }
        });


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
       // wp_title = itemView.findViewById(R.id.wallpaper_item_name);
        wp_photo = itemView.findViewById(R.id.wallpaper_item_photo);
    }
}
}
