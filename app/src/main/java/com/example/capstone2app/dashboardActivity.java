package com.example.capstone2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class dashboardActivity extends AppCompatActivity {
TextView welcome;
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button OpenForum = (Button) findViewById(R.id.buttonForum);
        Button CreateNewPost = (Button) findViewById(R.id.buttonCreatePost);
        welcome = findViewById(R.id.textViewWelcomeUser);

        SharedPreferences sp = getSharedPreferences("AccountData", Context.MODE_PRIVATE);
        username = sp.getString("username","");
        welcome.setText("Welcome, "+username+"!");

        OpenForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(dashboardActivity.this, ForumActivity.class);

                startActivity(in);

            }
        });

        CreateNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(dashboardActivity.this,Create_Post.class);
                startActivity(in);
            }
        });



    }
}