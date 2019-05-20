package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateExhibitionActivity extends AppCompatActivity implements AddsectorDialog.AddsectorDialogListener {

    private int nDatcnt = 0;
    private ArrayList<SectorListItem> sectorListData = new ArrayList<SectorListItem>();
    private SectorRecyclerAdapter adapter;
    private RecyclerView sectorlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createexhibition);

        final Button createExhibitionNext = (Button)findViewById(R.id.createExhibitionNext);
        final TextView addsector = (TextView) findViewById(R.id.addsector);
        final EditText exhibitionText = (EditText)findViewById(R.id.exhibitionText);

        adapter = new SectorRecyclerAdapter(this, sectorListData);
        sectorlist = (RecyclerView)findViewById(R.id.secterListRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        sectorlist.setLayoutManager(mLayoutManager);
        sectorlist.setAdapter(adapter);

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
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void openDialog(){
        AddsectorDialog addsectorDialog = new AddsectorDialog();
        addsectorDialog.show(getSupportFragmentManager(), "addsector dialog");
    }

    @Override
    public void applyTexts(String sectorname, int number) {
        SectorListItem secItem = new SectorListItem();
        secItem.setSectorname(sectorname);
        secItem.setNumberofex(number);
        sectorListData.add(secItem);




    }
}
