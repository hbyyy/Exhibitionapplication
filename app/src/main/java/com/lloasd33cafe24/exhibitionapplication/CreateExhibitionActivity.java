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

public class CreateExhibitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createexhibition);

        Button createExhibitionNext = (Button)findViewById(R.id.createExhibitionNext);
        final EditText exhibitionText = (EditText)findViewById(R.id.exhibitionText);
        Intent intent = getIntent();
        final String adminID = intent.getStringExtra("adminID");

        createExhibitionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exhibitionText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(CreateExhibitionActivity.this, CreateExhibition2Activity.class);
                                CreateExhibitionActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateExhibitionActivity.this);
                                builder.setMessage("입력에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null).create().show();
                            }
                        }

                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                CreateExhibitionRequest createExhibitionRequest = new CreateExhibitionRequest(adminID, name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateExhibitionActivity.this);
                queue.add(createExhibitionRequest);

            }
        });

    }
}
