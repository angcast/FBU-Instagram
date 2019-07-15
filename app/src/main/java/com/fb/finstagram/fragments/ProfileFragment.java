package com.fb.finstagram.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fb.finstagram.R;

public class ProfileFragment extends HomeFragment  {
 Button uploadNewImage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvPosts.setLayoutManager(gridLayoutManager);
    }
}
