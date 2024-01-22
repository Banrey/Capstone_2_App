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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountsActivity extends AppCompatActivity {

    private static Button btnLoginCode;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://192.168.254.116/Activities/caps2/mobileLoginApi.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    public  String email = "";
    public  String password = "";
    public  int loginCode;
    SharedPreferences sp ;

    String rawPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        SharedPreferences sp = getSharedPreferences("AccountData", Context.MODE_PRIVATE);
        SharedPreferences spArray = getSharedPreferences("Accounts", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEdit = spArray.edit();




        TextView jsontxt = findViewById(R.id.tvWelcomeAcc);
        TextView tvAccID = findViewById(R.id.tvAccID);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvAccType = findViewById(R.id.tvAccType);
        TextView tvDateCreated = findViewById(R.id.tvDateCreated);
        btnLoginCode = findViewById(R.id.btnDecrypt);
        btnLoginCode.setText("Get Login Code");


        String jsonData = sp.getString("accJSON","0");

        if (jsonData != null) {
            try {
                JSONObject jObj = new JSONObject(jsonData);
                String tvAID = "Account ID: " + jObj.getString("accID");
                String tvU = "Username: " + jObj.getString("username");
                String tvE = "E-mail: " + jObj.getString("email");
                String tvAT = "Account Type: " + jObj.getString("accType");
                String tvDC = "Date Created: " + jObj.getString("dateCreated");
                String tvPass = "Welcome " + jObj.getString("username") + "!";
                rawPass = jObj.getString("password");


                email = jObj.getString("email");
                password = jObj.getString("password");

                tvAccID.setText(tvAID);
                tvUsername.setText(tvU);
                tvEmail.setText(tvE);
                tvAccType.setText(tvAT);
                tvDateCreated.setText(tvDC);
                jsontxt.setText(tvPass);

            } catch (JSONException e) {

            }

        }
        btnLoginCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new uploadDataToURL().execute();


                /* Check Password code
                String password = md5Encryption.MD5(pass.getText().toString());
                if(password.equals(rawPass)) {
                    Toast.makeText(AccountsActivity.this, "Correct Password", Toast.LENGTH_SHORT).show();



                }else {
                    Toast.makeText(AccountsActivity.this, "Inorrect Password", Toast.LENGTH_SHORT).show();

                }*/



            }
        });



    }

    //POST starts here

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPostEmail = "", cPostPass = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(AccountsActivity.this);



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
                cPostEmail =  email ;
                cPostPass = password ;
                cv.put("email", cPostEmail);
                cv.put("password", cPostPass);

                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST" , cv);
                if (json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1) {
                        online_dataset = json.getString("loginCode");
                        loginCode = json.getInt("loginCode");
                        ChangeLoginCodeText(String.valueOf(loginCode));






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
            AlertDialog.Builder alert = new AlertDialog.Builder(AccountsActivity.this);
            if (s != null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR, NULL JSON returned")) { }
                Toast.makeText(AccountsActivity.this, s, Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }


    }

    public void ChangeLoginCodeText(String loginCode){
        EditText pass = findViewById(R.id.etTestPass);
        pass.setText(loginCode);

    }

    //POST ends Here
}