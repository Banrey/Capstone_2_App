package com.example.capstone2app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static Button btnLogin,Register;
    private static EditText etEmail, etPass;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "https://superphisal.000webhostapp.com/loginApi.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    public  String email = "";
    public  String pass = "";
    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        sp = getSharedPreferences("AccountData", Context.MODE_PRIVATE);
        btnLogin = findViewById(R.id.buttonLogin);
        Register = findViewById(R.id.buttonLoginToReg);
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPass = findViewById(R.id.editTextPassword);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(in);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= etEmail.getText().toString();
                pass = etPass.getText().toString();
                pass = md5Encryption.MD5(pass);


                new uploadDataToURL().execute();


            }
        });
    }

    //POST starts here

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);



        @Override
        //showing Message before executing POST
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setMessage(cMessage);
            pDialog.show();
        }
        //execute POST
        protected String doInBackground(String... params) {
            int nSuccess;
            try {
                ContentValues cv = new ContentValues();
                //insert anything in this "code" to be read by file linked using url
                cPostSQL = "email = '"+ email+"' AND password='"+pass+"'";
                cv.put("code", cPostSQL);
                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST" , cv);
                if (json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1) {
                        online_dataset = json.getString("username");

                        SharedPreferences.Editor spEdit = sp.edit();
                        spEdit.putString("accID", json.getString("accID"));
                        spEdit.putString("email", json.getString("email"));
                        spEdit.putString("username", json.getString("username"));
                        spEdit.putString("accType", json.getString("accType"));
                        spEdit.putString("dateCreated", json.getString("dateCreated"));
                        spEdit.commit();

                        Intent in = new Intent(LoginActivity.this, dashboardActivity.class);

                        startActivity(in);

                        //zToast.makeText(LoginActivity.this,json.toString(),Toast.LENGTH_SHORT);
                        return "Logged in as "+online_dataset;
                    } else {
                        return json.getString(TAG_MESSAGE);
                    }
                } else {
                    //error handling
                    return "HTTPSERVER_ERROR";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        //execute after POST
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            pDialog.dismiss();
            String isEmpty = "";
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            if (s != null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR, NULL JSON returned")) { }
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }


    }

    //POST ends Here
}