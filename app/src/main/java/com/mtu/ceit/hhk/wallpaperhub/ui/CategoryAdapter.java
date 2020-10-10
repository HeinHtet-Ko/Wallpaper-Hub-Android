package com.mtu.ceit.hhk.wallpaperhub.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Category;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<Category> categoryArrayList ;
    private Context mContext;
    public CategoryAdapter(Context mContext, ArrayList<Category> list)
    {
        this.mContext = mContext;
        categoryArrayList = list ;
    }


    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {

        holder.bindViews();

        final Category category = categoryArrayList.get(position);
        holder.cate_name.setText(category.name);
        Glide.with(mContext).load(category.thumb).into( holder.cate_photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,category.name,Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder
{

    private TextView cate_name;
    private ImageView cate_photo;
    private View itemView;
    public CategoryViewHolder(@NonNull View itemView) {

        super(itemView);
        this.itemView = itemView;
    }
    public void bindViews()
    {
        cate_name = itemView.findViewById(R.id.category_item_name);
        cate_photo = itemView.findViewById(R.id.category_item_photo);
    }
}
}
