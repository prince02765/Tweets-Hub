package com.sgp.tweetshub;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TWEET_TABLE = "TWEET_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_TWEET_TEXT = "TWEET_TEXT";
    public static final String COLUMN_ID = "ID";

    public SQLiteHelper(@Nullable Context context) {
        super(context, "twitterDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "CREATE TABLE " + TWEET_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_ID + " TEXT, " + COLUMN_TWEET_TEXT + " TEXT)";

        db.execSQL(createStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(SQLiteModel sqLiteModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, sqLiteModel.getUser_name());
        cv.put(COLUMN_USER_ID, sqLiteModel.getUser_id());
        cv.put(COLUMN_TWEET_TEXT, sqLiteModel.getTweet_text());

        long insert = db.insert(TWEET_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

}
