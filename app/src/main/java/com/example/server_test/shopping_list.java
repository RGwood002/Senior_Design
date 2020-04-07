package com.example.server_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class shopping_list extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<food_item> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Button fridge = (Button) findViewById(R.id.fridge);
        Button add = (Button) findViewById(R.id.add_button);
        Button remove = (Button) findViewById(R.id.remove_button);
        final EditText add_text = (EditText) findViewById(R.id.add_text);

        mRecyclerView = findViewById(R.id.RecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(shopping_list.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        foodList = new ArrayList<>();

        mAdapter = new foodAdapter(foodList);

        mRecyclerView.setAdapter(mAdapter);

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(shopping_list.this, MainActivity.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
