package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String adminID;
    private String selectex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
        final Button createExbutton = (Button)findViewById(R.id.createExbutton);
        final TextView selectEx = (TextView)findViewById(R.id.selectEx);
        final Button deleteExbutton = (Button)findViewById(R.id.deleteExbutton);
        final Button manageExbutton = (Button)findViewById(R.id.manageExbutton);

        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");
        selectex = intent.getStringExtra("name");
        String message = adminID + "님 환영합니다";
        welcomeText.setText(message);
        selectEx.setText(selectex);

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

        deleteExbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectex == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("전시를 선택해주세요")
                            .setPositiveButton("확인", null).create();
                }
                else
                {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{


                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("삭제되었습니다")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    selectEx.setText(null);
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("삭제되지 않았습니다")
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

                    DeleteExRequest deleteExRequest = new DeleteExRequest(adminID, selectex, listener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(deleteExRequest);
                }


            }
        });

        manageExbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagementWorkActiyity.class);
                intent.putExtra("adminID", adminID);
                MainActivity.this.startActivity(intent);

            }
        });

    }


}


