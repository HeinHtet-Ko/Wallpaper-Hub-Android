package com.mtu.ceit.hhk.wallpaperhub.ui.favouriteDb;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;

import java.util.List;

@Dao
public interface FavouriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertWallpaper(Wallpaper wp);

    @Query("Select * from wallpaper")
    List<Wallpaper> getAllWallpapers();

    @Delete
    void deleteWallpaper(Wallpaper wp);
}
