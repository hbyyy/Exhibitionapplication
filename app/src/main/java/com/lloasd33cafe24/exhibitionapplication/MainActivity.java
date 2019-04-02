package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
        Intent intent = getIntent();
        String adminID = intent.getStringExtra("adminID");
        String message = adminID + "님 환영합니다";

        welcomeText.setText(message);
    }
}
