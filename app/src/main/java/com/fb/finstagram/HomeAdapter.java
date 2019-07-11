package com.fb.finstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fb.finstagram.model.Post;
import com.parse.ParseFile;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Post> mPosts;
    private Context context;

    public HomeAdapter(Context context, List<Post>posts){
        this.context = context;
        mPosts = posts;
    }

    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View homeView = inflater.inflate(R.layout.activity_item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(homeView);
        return viewHolder;
    }

    // populates data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.tvDescriptionPost.setText(post.getDescription());
        holder.tvUsernamePost.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image!=null){
            Glide.with(context)
                    .load(image.getUrl())
                    .into(holder.ivImagePost);
        }
       // TextView tvTimeStamp = holder.tvTimeStampPost;
        //TODO //tvTimeStamp.setText(post.getCreatedAt().toString()); //WHY NOT GET TIME EITHER

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

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescriptionPost;
        public ImageView ivImagePost;
        public TextView tvTimeStampPost;
        public TextView tvUsernamePost;

        public ViewHolder(View itemView){
            super(itemView);
            tvDescriptionPost = (TextView) itemView.findViewById(R.id.tvDescriptionPost);
            ivImagePost = (ImageView) itemView.findViewById(R.id.ivImagePost);
            tvTimeStampPost = (TextView) itemView.findViewById(R.id.tvTimeStampPost);
            tvUsernamePost = (TextView) itemView.findViewById(R.id.tvUsernamePost);
            clickAPost();
        }


        public void clickAPost(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent (context, PostDetails.class);
                    i.putExtra("description",mPosts.get(position).getDescription());
                    i.putExtra("image",mPosts.get(position).getImage());
                    context.startActivity(i);
                }
            });
        }

    }

    public HomeAdapter (List <Post> posts){
        mPosts = posts ;
    }

    public int getItemCount(){
        return mPosts.size();
    }
}
