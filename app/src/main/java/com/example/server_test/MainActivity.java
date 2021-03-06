package com.example.server_test;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = "MainActivity";

    //floats for swipe motion
    float x1,x2,y1,y2;

    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<food_item> foodList;
    String url = "http://142.93.17.92:8080/getmyfridge/";


    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.RecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        foodList = new ArrayList<>();

        mAdapter = new foodAdapter(foodList);

        mRecyclerView.setAdapter(mAdapter);

        mQueue = Volley.newRequestQueue(this);

        Button buttonParse = findViewById(R.id.button_parse);
        Button right = findViewById(R.id.right);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, about_to_expire.class));
                finish();
            }
        });

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Button Click: ", "we made it");
                JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("onResponse: ", response.toString());
                        try {
                            foodList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject list = response.getJSONObject(i);

                                String food = list.getString("food_item");
                                Log.d("onResponse: ", food);
                                String date = list.getString("expiration_date");

                                foodList.add(new food_item(food, date));
                            }

                            mAdapter = new foodAdapter(foodList);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );
                jsonArray.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mQueue.add(jsonArray);




            }
        });
    }








}

//should delete this after comments

    /* private ArrayList<food_item> jsonParse(){
        String url = "https://142.93.17.92:8080/getmyfridge/";

        final ArrayList<food_item> foodList = new ArrayList<>();


        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject list = response.getJSONObject(i);

                        String food = list.getString("food_item");
                        Log.d("onResponse: ", food);
                        String date = list.getString("expiration_date");

                        foodList.add(new food_item(food, date));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        mQueue.add(jsonArray);



    return foodList;
    }
} */



  /*  JSONObject hero = response.getJSONObject(i);

    String item = hero.getString("food_item");
    String expiration = hero.getString("expiration_date");

                                foodList.add(new food_item(item,expiration)); */

