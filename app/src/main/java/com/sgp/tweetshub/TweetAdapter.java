package com.sgp.tweetshub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<TweetModel> tweets;
    Context context;

    TweetAdapter(Context context, List<TweetModel> tweets){
        this.tweets = tweets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.userId.setText("@" + tweets.get(position).getUser_id());
        holder.userName.setText(tweets.get(position).getUser_name());
        holder.userTweet.setText(tweets.get(position).getTweet_text());

        int result = getRandomBG();

        switch (result) {
            case 1:
                holder.cardImage.setBackgroundResource(R.drawable.bg_1);
                break;

            case 2:
                holder.cardImage.setBackgroundResource(R.drawable.bg_2);
                break;


            case 3:
                holder.cardImage.setBackgroundResource(R.drawable.bg_3);
                break;

            case 4:
                holder.cardImage.setBackgroundResource(R.drawable.bg_4);
                break;

            case 5:
                holder.cardImage.setBackgroundResource(R.drawable.bg_5);
                break;

            case 6:
                holder.cardImage.setBackgroundResource(R.drawable.bg_6);
                break;

            case 7:
                holder.cardImage.setBackgroundResource(R.drawable.bg_7);
                break;

            case 8:
                holder.cardImage.setBackgroundResource(R.drawable.bg_8);
                break;

            default:
                holder.cardImage.setBackgroundResource(R.drawable.bg_1);
        }

        Log.e("TAG", "onResponse: user id on binding "+ (holder.userId).getText().toString());

        String pUrl = String.valueOf(tweets.get(position).getProfile());
        Glide.with(context).load(pUrl).into(holder.userProfile);
    }

    private int getRandomBG() {

        Random r = new Random();
        return r.nextInt(9);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userProfile, cardImage;
        TextView userId, userName, userTweet;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userProfile = itemView.findViewById(R.id.profile_url);
            userId = itemView.findViewById(R.id.userid_tv);
            userName = itemView.findViewById(R.id.username_tv);
            userTweet = itemView.findViewById(R.id.user_tweet_tv);
            cardImage = itemView.findViewById(R.id.card_img);
        }
    }
}
