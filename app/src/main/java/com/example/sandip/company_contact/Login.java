package com.example.sandip.company_contact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandip on 10/20/2016.
 */

public class Login extends AppCompatActivity{
    public static final String LOGIN_URL = "http://democontact.esy.es/login.php";

    public static final String KEY_EMAIL="username";
    public static final String KEY_PASSWORD="password";
    private boolean loggedIn = false;


    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin,buttonRegister;
    private ProgressDialog loading;

    private String username;
    private String password;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editTextEmail = (EditText) findViewById(R.id.email_txt);
        editTextPassword = (EditText) findViewById(R.id.pass_txt);

        buttonLogin = (Button) findViewById(R.id.log_button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        buttonRegister= (Button) findViewById(R.id.register_btn);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(Login.this,Register.class);
                startActivity(next);
            }
        });
    }


    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences("myloginapp", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean("loggedin", false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void login(){
        //Getting values from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.equalsIgnoreCase("success")){
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences("myloginapp", Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean("loggedin", true);
                            editor.putString("email", email);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
