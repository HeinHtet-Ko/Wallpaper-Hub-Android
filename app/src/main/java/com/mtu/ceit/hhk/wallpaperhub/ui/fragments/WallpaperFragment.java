package com.mtu.ceit.hhk.wallpaperhub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.CategoryAdapter;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Category;

import java.util.ArrayList;

public class WallpaperFragment extends Fragment{


    private DatabaseReference myRef;
    private RecyclerView category_recycler ;
    private CategoryAdapter cate_adapter;
    private ArrayList<Category> cate_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.wallpaper_fragment,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myRef = FirebaseDatabase.getInstance().getReference("categories");
        category_recycler = view.findViewById(R.id.category_recycler);

        GridLayoutManager gl = (GridLayoutManager) category_recycler.getLayoutManager();
        gl.setSpanCount(2);

        readCategories();


    }

    private void readCategories()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String cate_name = ds.getKey();
                    String cate_desc = ds.child("desc").getValue(String.class);
                    String cate_thumb = ds.child("thumbnail").getValue(String.class);
                    Category category = new Category(cate_name,cate_desc,cate_thumb);
                    cate_list.add(category);



                }
                cate_adapter = new CategoryAdapter(getContext(),cate_list);
                category_recycler.setAdapter(cate_adapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}