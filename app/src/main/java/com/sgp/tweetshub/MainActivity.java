package com.sgp.tweetshub;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    LinearLayout keywordLiner;
    EditText userId, keyword;
    Button profileValid, finalSearch;
    ImageView userImage;
    String id = "", textTweet = "", userName = "", profileImg = "";
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TweetAdapter adapter;
    ProgressBar progressBar;
    List<TweetModel> list = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customTitleBar();

        keywordLiner = findViewById(R.id.keyword_liner);
        keywordLiner.setVisibility(View.GONE);
        userId = findViewById(R.id.user_id_etx);
        keyword = findViewById(R.id.keyword_etx);
        profileValid = findViewById(R.id.profile_valid);
        finalSearch = findViewById(R.id.final_search);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);

        userId.setOnFocusChangeListener(myEditTextFocus);

        profileValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                keywordLiner.setVisibility(View.GONE);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://api.twitter.com/2/users/by/username/";

                url = url + userId.getText();
                url = url + "?user.fields=profile_image_url";

                StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                progressBar.setVisibility(View.GONE);
                                JSONObject jsonObject = null;

                                try {
                                    jsonObject = new JSONObject(response);
                                    Log.d("Response", " str val " + String.valueOf(jsonObject));

                                    try {
                                        id = jsonObject.getJSONObject("data").getString("id");
                                        userName = jsonObject.getJSONObject("data").getString("name");
                                        profileImg = jsonObject.getJSONObject("data").getString("profile_image_url");
                                        profileImg = profileImg.replace("_normal", "_400x400");

                                        Glide.with(getApplicationContext()).load(profileImg).into(userImage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (String.valueOf(jsonObject).contains("data")) {
                                        keywordLiner.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, "Valid User", Toast.LENGTH_SHORT).show();

                                    } else if (String.valueOf(jsonObject).contains("error")) {
                                        Toast.makeText(MainActivity.this, "InValid User", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("ERROR", "error => " + error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAOjGXQEAAAAAdEyhgSVOZ1SmI9LhWPudXeL8kRQ%3DaB8EhEmCEWohS1MEyQx8G9981XporTDfL1Qzt0k9LyxNxrhIXK");
                        return params;
                    }
                };
                queue.add(getRequest);
            }
        });

        finalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                list.clear();

                RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
                String url1 = "https://api.twitter.com/2/users/" + id + "/tweets?expansions=author_id&tweet.fields=created_at,public_metrics,context_annotations&user.fields=username&max_results=100";

                Log.e("TAG", "onClick: id: " + id);

                StringRequest getRequest1 = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);

                                progressBar.setVisibility(View.GONE);

                                JSONObject jsonObject = null;
                                JSONArray jsonArray = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    Log.d("Response", " str val " + String.valueOf(jsonObject));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    jsonArray = jsonObject.getJSONArray("data");

                                    Log.e("TAG", "onResponse: Array Length-> " + jsonArray);
                                    System.out.println("Array Length-> " + jsonArray.length());

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject main = null;
                                        try {
                                            main = jsonArray.getJSONObject(i);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String text = null;
                                        try {
                                            assert main != null;
                                            text = main.getString("text");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        assert text != null;
                                        if (text.contains(keyword.getText())) {
                                            list.add(new TweetModel(profileImg, userName, userId.getText().toString(), text));

                                            SQLiteModel sqLiteModel;
                                            try {
                                                sqLiteModel = new SQLiteModel(-1, userName, userId.getText().toString(), text);
                                            } catch (Exception e) {
                                                Log.e("TAG", "onResponse: SQLite Database Not Inserted");
                                                sqLiteModel = new SQLiteModel(-1, "error", "error", "error");
                                            }

                                            SQLiteHelper sqLiteHelper = new SQLiteHelper(MainActivity.this);

                                            boolean success = sqLiteHelper.addOne(sqLiteModel);
                                        }
                                    }

                                    profileValid.setVisibility(View.GONE);

                                    recyclerView = findViewById(R.id.recycler_view);
                                    layoutManager = new LinearLayoutManager(MainActivity.this);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView.setLayoutManager(layoutManager);
                                    adapter = new TweetAdapter(MainActivity.this, list);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("ERROR", "error => " + error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAOjGXQEAAAAAdEyhgSVOZ1SmI9LhWPudXeL8kRQ%3DaB8EhEmCEWohS1MEyQx8G9981XporTDfL1Qzt0k9LyxNxrhIXK");

                        return params;
                    }
                };
                queue1.add(getRequest1);
            }
        });
    }


    private void customTitleBar() {
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        int nightModeFlags = MainActivity.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Tweets Hub</font>"));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Tweets Hub</font>"));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private View.OnFocusChangeListener myEditTextFocus = new View.OnFocusChangeListener() {
        public void onFocusChange(View view, boolean gainFocus) {
            //onFocus
            if (gainFocus) {
                //set the text
                keyword.setText("");
                keywordLiner.setVisibility(View.GONE);
                profileValid.setVisibility(View.VISIBLE);
            }
        }

        ;
    };

}