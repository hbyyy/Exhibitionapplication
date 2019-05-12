package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
        final Button createExbutton = (Button)findViewById(R.id.createExbutton);
        final TextView selectEx = (TextView)findViewById(R.id.selectEx);

        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");
        String message = adminID + "님 환영합니다";
        welcomeText.setText(message);

        createExbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createExhibitionintent = new Intent(MainActivity.this, CreateExhibitionActivity.class);
                createExhibitionintent.putExtra("adminID", adminID);
                MainActivity.this.startActivity(createExhibitionintent);
            }
        });


        selectEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showlistintent = new Intent(MainActivity.this, Showexlist.class);
                showlistintent.putExtra("adminID", adminID);
                MainActivity.this.startActivity(showlistintent);
            }
        });




    }


}


