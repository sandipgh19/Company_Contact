package com.example.sandip.company_contact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandip on 10/19/2016.
 */

public class View_company extends AppCompatActivity {
    private TextView name_txt,email_txt,contact_txt,add_txt;
    Button close_btn;
    private ProgressBar progressBar;
    public static final String KEY_ID = "id";
    public static final String KEY_USER_EMAIL = "User_Email";
    String id,user_email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_company);
        name_txt= (TextView) findViewById(R.id.textView7);
        email_txt= (TextView) findViewById(R.id.textView11);
        contact_txt= (TextView) findViewById(R.id.textView13);
        add_txt= (TextView) findViewById(R.id.textView9);
        close_btn= (Button) findViewById(R.id.button4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "Not Available");
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pre=new Intent(View_company.this,MainActivity.class);
                pre.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pre);
            }
        });
        Intent pre=getIntent();
        id=pre.getStringExtra("value");
        progressBar.setVisibility(View.VISIBLE);
        show_user();
    }
    public void show_user(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Test",response);
                        //loading.dismiss();
                        showJSON(response);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View_company.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_ID,id);
                params.put(KEY_USER_EMAIL,user_email);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject Data = result.getJSONObject(0);
            email_txt.setText(Data.getString(Config.KEY_Email));
            name_txt.setText(Data.getString(Config.KEY_Name));
            contact_txt.setText(Data.getString(Config.KEY_Phn));
            add_txt.setText(Data.getString(Config.KEY_Add));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
