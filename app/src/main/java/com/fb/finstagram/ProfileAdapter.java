package com.fb.finstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fb.finstagram.model.Post;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private List<Post> myPosts;
    Context context;

    public ProfileAdapter(List<Post> posts){
        myPosts=posts;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.activity_item_yourpost, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = myPosts.get(position);
        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.yourpost);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView yourpost;

        public ViewHolder(View itemView) {
            super(itemView);
            yourpost= itemView.findViewById(R.id.yourpost);
        }
    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }
}
