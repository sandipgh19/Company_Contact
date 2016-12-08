package com.example.sandip.company_contact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandip on 10/18/2016.
 */

public class Add_Company extends AppCompatActivity{
    private EditText name_txt,email_txt,contact_txt,add_txt;
    private Button save_btn,cancel_btn;
    private static final String ADD_Company_URL = "http://democontact.esy.es/add_user.php";
    public static final String KEY_NAME = "Name";
    public static final String KEY_ADD = "Address";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_USER_EMAIL = "User_Email";
    public static final String KEY_Contact = "Contact";
    String user_email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_company);
        name_txt= (EditText) findViewById(R.id.editText);
        add_txt= (EditText) findViewById(R.id.editText2);
        contact_txt= (EditText) findViewById(R.id.editText4);
        email_txt= (EditText) findViewById(R.id.editText3);
        save_btn= (Button) findViewById(R.id.button3);
        cancel_btn= (Button) findViewById(R.id.button2);
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "Not Available");
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_user();
            }
        });
       cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pre= new Intent(Add_Company.this,MainActivity.class);
                pre.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pre);
            }
        });
    }
    public void save_user(){
        final String name=name_txt.getText().toString().trim().toLowerCase();
        final String email=email_txt.getText().toString().trim().toLowerCase();
        final String add=add_txt.getText().toString().trim().toLowerCase();
        final String contact=contact_txt.getText().toString().trim().toLowerCase();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ADD_Company_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("My App: ",s);
                int code = Integer.parseInt(s);
                if (code==3){
                    Toast.makeText(Add_Company.this,"You have Added Successfully !",Toast.LENGTH_LONG).show();
                    //Use intent to go to a activity
                    Intent next=new Intent(Add_Company.this,MainActivity.class);
                    next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(next);
                }
                if(code==2){
                    Toast.makeText(Add_Company.this,"Organization Already Exists !",Toast.LENGTH_LONG).show();
                }
                if (code==1){
                    Toast.makeText(Add_Company.this,"Please fill all the value !",Toast.LENGTH_LONG).show();
                }
                if(code==4){
                    Toast.makeText(Add_Company.this,"Please Try Again Later !",Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_Company.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_NAME,name);
                params.put(KEY_ADD,add);
                params.put(KEY_Contact,contact);
                params.put(KEY_USER_EMAIL,user_email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
