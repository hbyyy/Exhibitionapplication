package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateWorkActivity extends AppCompatActivity {
    private String adminID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_work);

        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");

        Button createWorkNextButton = (Button)findViewById(R.id.createWorkNextButton);

        createWorkNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateWorkActivity.this, QRcodeGeneratorActivity. class);
                intent.putExtra("adminID", adminID);
                CreateWorkActivity.this.startActivity(intent);
            }
        });

    }
}
