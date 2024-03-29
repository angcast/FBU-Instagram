package com.fb.finstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fb.finstagram.model.Post;
import com.parse.ParseFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        holder.tvTimeStampPost.setText(formatDate(post.getCreatedAt()));
        holder.tvDescriptionPost.setText(post.getDescription());
        holder.tvUsernamePost.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image!=null){
            Glide.with(context)
                    .load(image.getUrl())
                    .into(holder.ivImagePost);
        }
    }

    public static String formatDate (Date date){
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = date;
        return df.format(today);

    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescriptionPost;
        public ImageView ivImagePost;
        public TextView tvTimeStampPost;
        public TextView tvUsernamePost;
        public ImageView ivProfilePicture;
        public ImageButton heart;

        public ViewHolder(View itemView){
            super(itemView);
            tvDescriptionPost = (TextView) itemView.findViewById(R.id.tvDescriptionPost);
            ivImagePost = (ImageView) itemView.findViewById(R.id.ivImagePost);
            tvTimeStampPost = (TextView) itemView.findViewById(R.id.tvTimeStampPost);
            tvUsernamePost = (TextView) itemView.findViewById(R.id.tvUsernamePost);
            ivProfilePicture = (ImageView) itemView.findViewById(R.id.profilePicture);
            heart = (ImageButton) itemView.findViewById(R.id.heart);

            clickAPost();
            onClickProfilePicture();
        }


        public void clickAPost(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent (context, PostDetails.class);
                    i.putExtra("description",mPosts.get(position).getDescription());
                    i.putExtra("image",mPosts.get(position).getImage());
                    i.putExtra("timestamp",mPosts.get(position).getCreatedAt());
                    i.putExtra("username",mPosts.get(position).getUser().getUsername());
                    context.startActivity(i);
                }
            });
        }

        public void onClickProfilePicture(){
            ivProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent i = new Intent(context, ProfileFragment.class);
                    //context.startActivity(i);
                }
            });

        }
        public void onClickHeart(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

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
