package com.example.capstone2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button CreateNewPost, OpenForum, Login, Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenForum = (Button) findViewById(R.id.buttonForum);
        CreateNewPost = (Button) findViewById(R.id.buttonCreatePost);
        Login = (Button) findViewById(R.id.buttonLoginAct);
        Register =  (Button) findViewById(R.id.buttonRegisteract);

        OpenForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ForumActivity.class);

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

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(in);
            }
        });
    }
}