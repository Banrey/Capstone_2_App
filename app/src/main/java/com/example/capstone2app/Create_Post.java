package com.example.capstone2app;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;
public class Create_Post extends AppCompatActivity {
    private static Button btnQuery;
    private static EditText etPostTitle, etPostContent;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://superphisal.000webhostapp.com/InsertTrans.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    public static String postTitle = "";
    public static String postContent = "";
    public static String accID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        etPostTitle = (EditText) findViewById(R.id.editTextPostTitle);
        etPostContent = (EditText) findViewById(R.id.editTextPostContent);
        //btn
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTitle = etPostTitle.getText().toString();
                postContent = etPostContent.getText().toString();
                new uploadDataToURL().execute();
            }
        });

    }

    //POST starts here

    private class uploadDataToURL extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(Create_Post.this);



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
                cPostSQL = "NULL" + " , '" + postTitle + " ' ,  '" + postContent + " ' ";
                cv.put("code", cPostSQL);
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
            AlertDialog.Builder alert = new AlertDialog.Builder(Create_Post.this);
            if (s != null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) { }
                Toast.makeText(Create_Post.this, s, Toast.LENGTH_SHORT).show();
            } else {
                alert.setMessage("Query Interrupted... \nPlease Check Internet connection");
                alert.setTitle("Error");
                alert.show();
            }
        }


    }

    //POST ends Here

}