package com.example.capstone2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button CreateNewPost, OpenForum, Login, Register, Chat;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("AccountData", Context.MODE_PRIVATE);
        username = sp.getString("username","");


        Login = (Button) findViewById(R.id.buttonLoginAct);
        Register =  (Button) findViewById(R.id.buttonRegisteract);

        Register.setText(username);




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
                Intent in = new Intent(MainActivity.this,AccountsActivity.class);
                startActivity(in);
            }
        });


    }
}