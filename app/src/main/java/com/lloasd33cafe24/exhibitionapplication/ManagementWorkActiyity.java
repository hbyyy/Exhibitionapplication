package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ManagementWorkActiyity extends AppCompatActivity {

    private String adminID;
    private String workname = null;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_work_actiyity);

        final TextView selectwork = (TextView)findViewById(R.id.selectWork);
        Button createWorkButton = (Button)findViewById(R.id.createWorkButton);
        Button deleteWorkButton = (Button)findViewById(R.id.deleteWorkButton);
        Button qrcodeButton = (Button)findViewById(R.id.qrcodebutton);
        TextView nameText = findViewById(R.id.nameText);
        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");
        name = intent.getStringExtra("name");
        workname = intent.getStringExtra("workname");
        selectwork.setText(workname);
        nameText.setText(name);

        selectwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementWorkActiyity.this, ShowWorkList.class);
                intent.putExtra("name", name);
                intent.putExtra("adminID", adminID);
                ManagementWorkActiyity.this.startActivity(intent);
            }
        });


        createWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementWorkActiyity.this, CreateWorkActivity.class);
                intent.putExtra("adminID",adminID);
                intent.putExtra("name", name);
                ManagementWorkActiyity.this.startActivity(intent);
            }
        });

        deleteWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workname == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ManagementWorkActiyity.this);
                    builder.setMessage("작품을 선택해주세요")
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ManagementWorkActiyity.this);
                                    builder.setMessage("삭제되었습니다")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    selectwork.setText(null);
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ManagementWorkActiyity.this);
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

                    DeleteWorkRequest deleteworkRequest = new DeleteWorkRequest(adminID, workname, listener);
                    RequestQueue queue = Volley.newRequestQueue(ManagementWorkActiyity.this);
                    queue.add(deleteworkRequest);
                }

            }
        });

        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectwork.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ManagementWorkActiyity.this);
                    builder.setMessage("작품을 선택하세요")
                            .setNegativeButton("돌아가기", null)
                            .create()
                            .show();

                }
                else{
                    String workname = selectwork.getText().toString();
                    Intent intent = new Intent(ManagementWorkActiyity.this, QRcodeGeneratorActivity.class);
                    intent.putExtra("adminID", adminID);
                    intent.putExtra("workName", workname);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }
}
