package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementWorkActiyity extends AppCompatActivity {

    private String adminID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_work_actiyity);

        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");
        Button createWorkButton = (Button)findViewById(R.id.createWorkButton);


        createWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementWorkActiyity.this, CreateWorkActivity.class);
                intent.putExtra("adminID",adminID);
                ManagementWorkActiyity.this.startActivity(intent);
            }
        });
    }
}
