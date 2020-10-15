package com.mtu.ceit.hhk.wallpaperhub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.WallpaperActivity;
import com.mtu.ceit.hhk.wallpaperhub.ui.adapters.WallpaperAdapter;
import com.mtu.ceit.hhk.wallpaperhub.ui.favouriteDb.FavouriteDAO;
import com.mtu.ceit.hhk.wallpaperhub.ui.favouriteDb.FavouriteDatabase;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment{


    private FavouriteDatabase fvDb;
    private FavouriteDAO fvDao;
    private RecyclerView fav_recycler;
    private WallpaperAdapter wp_adapter;
    private ArrayList<Wallpaper> wp_list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.favourite_fragment,container,false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fvDao = FavouriteDatabase.getDatabase(getContext()).getFavDao();

        setUpRecycler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(final Wallpaper wp:fvDao.getAllWallpapers())
                {
                    wp_list.add(wp);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wp_adapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();



    }

    private void setUpRecycler()
    {
        fav_recycler = getView().findViewById(R.id.fav_wallpaper_recycler);
        GridLayoutManager gl = (GridLayoutManager)fav_recycler.getLayoutManager();
        gl.setSpanCount(3);
        wp_adapter = new WallpaperAdapter(getContext(),wp_list);
        fav_recycler.setHasFixedSize(true);
        fav_recycler.setAdapter(wp_adapter);



    }
}