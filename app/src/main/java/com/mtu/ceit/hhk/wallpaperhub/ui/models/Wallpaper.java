package com.mtu.ceit.hhk.wallpaperhub.ui.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Wallpaper implements Serializable {


    //public Integer id;

    @ColumnInfo(name = "Desc")
    public String desc;

    @ColumnInfo(name = "Title")
    public String title;

    @PrimaryKey
    @ColumnInfo(name="Url")
    @NonNull
    public String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Wallpaper(String desc, String title, String url)
    {

    }
    public Wallpaper()
    {

    }
}
