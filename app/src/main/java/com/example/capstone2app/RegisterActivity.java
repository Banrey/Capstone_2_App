package com.example.capstone2app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.StatusBarManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private static Button btnRegister;
    private static EditText etEmail, etUsername, etPass;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "https://superphisal.000webhostapp.com/apiRegister.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    public static String regEmail = "";
    public static String regUsername = "";
    public static String RegPass = "";
    public static String accID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        getSupportActionBar().hide();


        //hide action bar too

        btnRegister = (Button) findViewById(R.id.buttonReg);
        etEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        etUsername = (EditText) findViewById(R.id.editTextTextPersonName);
        etPass = (EditText) findViewById(R.id.editTextTextPassword);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regEmail = etEmail.getText().toString();
                regUsername = etUsername.getText().toString();
                RegPass = etPass.getText().toString();
                RegPass = md5Encryption.MD5(RegPass);

                new uploadDataToURL().execute();

            }
        });


    }


    //POST starts here

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPOSTEmail = "", cPOSTUsername = "", cPOSTPassword = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(RegisterActivity.this);



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
                cPOSTEmail = regEmail;
                cPOSTUsername = regUsername;
                cPOSTPassword = RegPass;
                cv.put("email", cPOSTEmail);
                cv.put("username", cPOSTUsername);
                cv.put("password", cPOSTPassword);
                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST" , cv);
                if (json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1) {
                        online_dataset = json.getString(TAG_MESSAGE);
                        return online_dataset;
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
            AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
            if (s != null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) { }
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }


    }

    //POST ends Here

}