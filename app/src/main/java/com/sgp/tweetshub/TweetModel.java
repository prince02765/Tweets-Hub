package com.sgp.tweetshub;

public class TweetModel {

    private String profile, user_name, user_id, tweet_text;
//    private TextView ;

    public TweetModel(String profile, String user_name, String user_id, String tweet_text) {
        this.profile = profile;
        this.user_name = user_name;
        this.user_id = user_id;
        this.tweet_text = tweet_text;
    }

    @Override
    public String toString() {
        return "TweetModel{" +
                "profile=" + profile +
                ", user_name=" + user_name +
                ", user_id=" + user_id +
                ", tweet_text=" + tweet_text +
                '}';
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTweet_text() {
        return tweet_text;
    }

    public void setTweet_text(String tweet_text) {
        this.tweet_text = tweet_text;
    }
}
