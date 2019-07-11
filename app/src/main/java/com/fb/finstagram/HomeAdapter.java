package com.fb.finstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fb.finstagram.model.Post;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View homeView = inflater.inflate(R.layout.activity_item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(homeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.tvDescriptionPost.setText(post.getDescription());
        TextView textView = holder.tvDescriptionPost;
        textView.setText(post.getDescription());
        ImageView imageView = holder.ivImagePost;

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(imageView);

    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescriptionPost;
        public ImageView ivImagePost;

        public ViewHolder(View itemView){
            super(itemView);
            tvDescriptionPost = (TextView) itemView.findViewById(R.id.tvDescriptionPost);
            ivImagePost = (ImageView) itemView.findViewById(R.id.ivImagePost);
        }
    }

    public HomeAdapter (List <Post> posts){
        mPosts = posts ;
    }

    public int getItemCount(){
        return mPosts.size();
    }
}
