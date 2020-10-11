package com.mtu.ceit.hhk.wallpaperhub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mtu.ceit.hhk.wallpaperhub.R;
import com.mtu.ceit.hhk.wallpaperhub.ui.adapters.CategoryAdapter;
import com.mtu.ceit.hhk.wallpaperhub.ui.models.Category;

import java.util.ArrayList;

public class WallpaperFragment extends Fragment{


    private DatabaseReference myRef;
    private RecyclerView category_recycler ;
    private CategoryAdapter cate_adapter;
    private ArrayList<Category> cate_list = new ArrayList<>();
    LottieAnimationView lv;

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
        lv = view.findViewById(R.id.ld_view);

        recyclerSet();
        readCategories();


    }

    private void recyclerSet()
    {
        cate_adapter = new CategoryAdapter(getContext(),cate_list);
        category_recycler.setAdapter(cate_adapter);
        category_recycler.setHasFixedSize(true);
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
                lv.setVisibility(View.GONE);
                cate_adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}