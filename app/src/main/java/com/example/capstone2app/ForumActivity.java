package com.example.capstone2app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    ArrayList<recyclerModel> recyclerModels = new ArrayList<>();

    public static final String URL_PRODUCTS = "https://superphisal.000webhostapp.com/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);


        loadProducts();


    }


    public void setRecyclerModels() {
        String[] postTitles = getResources().getStringArray(R.array.test_array1);
        for (String postTitle : postTitles) {
            recyclerModels.add(new recyclerModel(postTitle, postTitle));
        }
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type GET defined in first parameter
         * The URL defined in second parameter
         * Then we have Response Listener and Error Listener
         * In response listener gets the JSON response as String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<recyclerModel> recyclerModels = new ArrayList<>();

                        JSONArray array = null;

                        try {
                            array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting post object from json array
                                JSONObject post = array.getJSONObject(i);

                                //adding the product to list
                                recyclerModels.add(new recyclerModel(post.getString("title"), post.getString("content")));
                            }
                            RecyclerView recyclerView = findViewById(R.id.myRecycler);
                            adapter_recyclerview adapter = new adapter_recyclerview(ForumActivity.this, recyclerModels);

                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ForumActivity.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForumActivity.this, "Somethong went wrong", Toast.LENGTH_SHORT).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}