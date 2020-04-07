package com.example.server_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class about_to_expire extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public RequestQueue mQueue;
    public ArrayList<food_item> foodList;
    String url = "http://142.93.17.92:8080/getmyfridge/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_to_expire);

        mRecyclerView = findViewById(R.id.RecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(about_to_expire.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        foodList = new ArrayList<>();

        mAdapter = new foodAdapter(foodList);

        mRecyclerView.setAdapter(mAdapter);

        mQueue = Volley.newRequestQueue(this);

        Button buttonParse = findViewById(R.id.button_parse);
        Button shoppingList = findViewById(R.id.shoppingList);
        Button fridge = findViewById(R.id.fridge);

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(about_to_expire.this, shopping_list.class));
                finish();
            }
        });

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(about_to_expire.this, MainActivity.class));
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
