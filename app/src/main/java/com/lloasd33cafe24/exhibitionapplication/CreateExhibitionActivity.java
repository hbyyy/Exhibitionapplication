package com.lloasd33cafe24.exhibitionapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CreateExhibitionActivity extends AppCompatActivity implements AddsectorDialog.AddsectorDialogListener {


    private ArrayList<SectorListItem> sectorListData = new ArrayList<SectorListItem>();
    private SectorRecyclerAdapter adapter;
    private RecyclerView sectorlist;
    private String name;
    private String adminID;
    private EditText exhibitionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createexhibition);


        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");
        final Button createExhibitionNext = (Button)findViewById(R.id.createExhibitionNext);
        final TextView addsector = (TextView) findViewById(R.id.addsector);
        final Button checkDuplicationButton = findViewById(R.id.CheckDuplication);
        final TextView textViewSector = findViewById(R.id.textViewsector);
        exhibitionText = (EditText)findViewById(R.id.exhibitionText);


        adapter = new SectorRecyclerAdapter(this, sectorListData, adminID);
        sectorlist = (RecyclerView)findViewById(R.id.secterListRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        sectorlist.setLayoutManager(mLayoutManager);
        sectorlist.setAdapter(adapter);


        checkDuplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewSector.setVisibility(View.VISIBLE);
                sectorlist.setVisibility(View.VISIBLE);
                addsector.setVisibility(View.VISIBLE);
                createExhibitionNext.setVisibility(View.VISIBLE);
                name = exhibitionText.getText().toString();

            }
        });

        createExhibitionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(CreateExhibitionActivity.this, CreateExhibition2Activity.class);
                                intent.putExtra("adminID", adminID);
                                CreateExhibitionActivity.this.startActivity(intent);
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateExhibitionActivity.this);
                                builder.setMessage("입력에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null).create().show();
                            }
                        }

                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                CreateExhibitionRequest createExhibitionRequest = new CreateExhibitionRequest(adminID, name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateExhibitionActivity.this);
                queue.add(createExhibitionRequest);

            }
        });

        addsector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
    }

    public void openDialog(){
        AddsectorDialog addsectorDialog = new AddsectorDialog();
        Bundle bundle = new Bundle();
        bundle.putString("adminID", adminID);
        bundle.putString("name", name);
        addsectorDialog.setArguments(bundle);
        addsectorDialog.show(getSupportFragmentManager(), "addsector dialog");


    }

    @Override
    public void applyTexts(String sectorname, int number) {
        String postdata = null;
        SectorListItem secItem = new SectorListItem();
        secItem.setSectorname(sectorname);
        secItem.setNumberofex(number);
        sectorListData.add(secItem);
        adapter.notifyDataSetChanged();
        exhibitionText.clearFocus();

        SetSectorData setSectorData = new SetSectorData();
        try {
            postdata = setSectorData.execute("http://lloasd33.cafe24.com/sectoradd.php", adminID, name, secItem.getSectorname(), String.valueOf(secItem.getNumberofex())).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private class SetSectorData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                String url = strings[0];
                String adminID = strings[1];
                String name = strings[2];
                String worksector = strings[3];
                String numberofwork = strings[4];
                URL URLObject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) URLObject.openConnection();

                String postparam = "adminID=" + adminID + "&name=" + name + "&worksector=" + worksector + "&numberofwork=" + numberofwork;

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(postparam.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = con.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                result =  sb.toString();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }

        }
    }


}
