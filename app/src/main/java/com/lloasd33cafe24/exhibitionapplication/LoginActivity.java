package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private BackClickFinishHandler backClickFinishHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText passwordText = (EditText)findViewById(R.id.passwordText);
        final Button registerButton = (Button)findViewById(R.id.registerButton);
        final Button loginButton = (Button)findViewById(R.id.loginButton);
        backClickFinishHandler = new BackClickFinishHandler(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String adminID = idText.getText().toString();
                final String adminPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       try{


                           JSONObject jsonResponse = new JSONObject(response);
                           boolean success = jsonResponse.getBoolean("success");
                           if(success){
                               String adminID = jsonResponse.getString("adminID");
                               String adminEmail = jsonResponse.getString("adminEmail");
                               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                               intent.putExtra("adminID", adminID);
                               intent.putExtra("adminEmail", adminEmail);
                               idText.setText(null);
                               passwordText.setText(null);
                               LoginActivity.this.startActivity(intent);

                           }
                           else{
                               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                               builder.setMessage("로그인에 실패했습니다")
                                       .setNegativeButton("다시 시도", null)
                                       .create()
                                       .show();
                           }
                       }
                       catch (Exception e){
                           e.printStackTrace();
                       }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(adminID, adminPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backClickFinishHandler.onBackPressed();
    }
}
