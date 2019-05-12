package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CreateExhibition2Activity extends AppCompatActivity {

    private ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createexhibition2);

        final EditText editEmail = (EditText)findViewById(R.id.sectorName);
        TextView addEmail = (TextView)findViewById(R.id.addEmail);

        Button createExhibition = (Button)findViewById(R.id.createExhibition);
        Intent intent = getIntent();
        final String adminID = intent.getStringExtra("adminID");

        ListView emailList = (ListView)findViewById(R.id.showEmailList);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,mArrayList);

        emailList.setAdapter(adapter);


        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                mArrayList.add(email);
                adapter.notifyDataSetChanged();
                editEmail.setText(null);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){

                            }
                            else{

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                };

                AuthorEmailRequest authorEmailRequest = new AuthorEmailRequest(adminID, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateExhibition2Activity.this);
                queue.add(authorEmailRequest);

            }
        });


        createExhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
                Intent intent = new Intent(CreateExhibition2Activity.this, MainActivity.class);
                intent.putExtra("adminID", adminID);
                CreateExhibition2Activity.this.startActivity(intent);
            }
        });


    }




}


