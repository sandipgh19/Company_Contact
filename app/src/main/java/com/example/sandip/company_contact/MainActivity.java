package com.example.sandip.company_contact;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button next_btn,log_out;
    private ProgressBar progressBar;
    public static final String KEY_USER_EMAIL = "User_Email";
    private static final String USER_URL = "http://democontact.esy.es/show_details.php";
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Test", String.valueOf(R.layout.activity_main));
        listView = (ListView) findViewById(R.id.list);
        next_btn= (Button) findViewById(R.id.button);
        final SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "Not Available");
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(MainActivity.this,Add_Company.class);
                startActivity(next);
            }
        });
        log_out= (Button) findViewById(R.id.logout);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(MainActivity.this,Login.class);
                next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(next);
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }
    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,USER_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        showJSON(response);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USER_EMAIL, user_email);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String json){
        parseJSON pj = new parseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this, parseJSON.name);
        listView.setAdapter(cl);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent next= new Intent(getApplicationContext(), View_company.class);
                next.putExtra("value",Integer.toString(i+1));
                startActivity(next);
            }
        });
    }
}
