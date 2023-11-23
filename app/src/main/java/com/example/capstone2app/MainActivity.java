package com.example.capstone2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button CreateNewPost, OpenForum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenForum = (Button) findViewById(R.id.buttonForum);
        CreateNewPost = (Button) findViewById(R.id.buttonCreatePost);

        OpenForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ForumActivity.class);

                startActivity(in);
                startActivity(in);

            }
        });

        CreateNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Create_Post.class);
                startActivity(in);
            }
        });
    }
}