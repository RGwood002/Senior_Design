package com.example.server_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult =findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){
        String url = "http://ec2-54-219-185-106.us-west-1.compute.amazonaws.com/fridgemateapi/v1/api.php?apicall=getfridge";
        mTextViewResult.setText("");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() { //called when request was a success
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("heroes"); //getting JSON array
                            Log.d("onResponse: ", response.toString());
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject hero = jsonArray.getJSONObject(i);

                                String item = hero.getString("item");
                                String expiration = hero.getString("exipiration");

                                mTextViewResult.append(item + " has an expiration date of " + expiration +"\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() { //called when an error occurs
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
