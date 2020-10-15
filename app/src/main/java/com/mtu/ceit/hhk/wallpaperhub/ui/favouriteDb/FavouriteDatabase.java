package com.mtu.ceit.hhk.wallpaperhub.ui.favouriteDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mtu.ceit.hhk.wallpaperhub.ui.models.Wallpaper;

@Database(entities = Wallpaper.class,version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {

//    private static Context context;
//
    private static FavouriteDatabase INSTANCE;
//    FavouriteDatabase(Context context){
//
//        this.context = context;
//
//    }

    public abstract FavouriteDAO getFavDao();

    public static FavouriteDatabase getDatabase(Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (FavouriteDatabase.class)
            {
                INSTANCE = Room.databaseBuilder(context,
                        FavouriteDatabase.class, "favourite-wallpapers").build();
            }



        }
        return INSTANCE;

    }
}
