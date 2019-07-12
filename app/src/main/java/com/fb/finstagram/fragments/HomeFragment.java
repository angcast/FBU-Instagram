package com.fb.finstagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fb.finstagram.EndlessRecyclerViewScrollListener;
import com.fb.finstagram.HomeAdapter;
import com.fb.finstagram.R;
import com.fb.finstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    protected ArrayList<Post> arraylist_posts;
    protected HomeAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        arraylist_posts = new ArrayList<>();
        adapter = new HomeAdapter(arraylist_posts);
        rvPosts.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);


        setUpSwipeContainer();

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);

        showPosts();
    }
    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void setUpSwipeContainer(){
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Make sure you call swipeContainer.setRefreshing(false) once the network request has completed successfully.
                arraylist_posts.clear(); // TODO: test: if u don't clear this, will it repopulate images twice?
                adapter.clear();
                showPosts();
                swipeContainer.setRefreshing(false);

            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    protected void showPosts(){
        Post.Query posts = new Post.Query();
        posts.getTop().withUser();
        posts.addDescendingOrder(Post.KEY_CREATED_AT);
        posts.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for (int i = 0 ; i<objects.size(); i++){
                        arraylist_posts.add(objects.get(i));
                        adapter.notifyItemInserted(objects.size()-1);
                    }
                }
                else{
                    Log.d("Home Fragment","Loading pictures failed");
                }
            }
        });

    }

}
