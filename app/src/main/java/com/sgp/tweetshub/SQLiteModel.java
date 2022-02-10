package com.sgp.tweetshub;

public class SQLiteModel {

    private int id;
    private String user_name, user_id, tweet_text;


    public SQLiteModel(int id, String user_name, String user_id, String tweet_text) {
        this.id = id;
        this.user_name = user_name;
        this.user_id = user_id;
        this.tweet_text = tweet_text;
    }

    public SQLiteModel() {
    }

    @Override
    public String toString() {
        return "SQLiteModel{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tweet_text='" + tweet_text + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
