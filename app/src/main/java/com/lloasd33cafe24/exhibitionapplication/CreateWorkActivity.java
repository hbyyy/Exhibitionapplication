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
import android.widget.Spinner;

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

public class CreateWorkActivity extends AppCompatActivity {
    private String TAG_EXJSON = "showexlist";
    private String TAG_SECTORJSON = "showsectorlist";

    private ArrayList<String> exArrayList;
    private ArrayList<String> sectorArrayList;
    private String postexData;
    private String postsectorData;

    private String adminID;
    private String workname;
    private String authorname;
    private String workdescription;
    private EditText editworkname;
    private EditText editauthorname;
    private EditText editworkdescription;

    private Spinner exspinner;
    private Spinner sectorspinner;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_work);

        editworkname = (EditText)findViewById(R.id.editworkname);
        editauthorname = (EditText)findViewById(R.id.editauthorname);
        editworkdescription = (EditText)findViewById(R.id.editworkdescription);


        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");
        name = intent.getStringExtra("name");



        exspinner = (Spinner)findViewById(R.id.exspinner);
        sectorspinner = (Spinner)findViewById(R.id.sectorspinner);

        exArrayList = new ArrayList<>();
        sectorArrayList = new ArrayList<>();

        Button createWorkNextButton = (Button)findViewById(R.id.createWorkNextButton);

        if(exArrayList.size() <1){
            try {
                GetExList getExList = new GetExList();
                postexData = getExList.execute("http://lloasd33.cafe24.com/showexhibitionlist2.php", adminID).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(postexData);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_EXJSON);
                for(int i = 0; i <jsonArray.length(); i++){
                    exArrayList.add(jsonArray.getJSONObject(i).getString("name"));
                }


            }catch (JSONException e){ e.printStackTrace();}

        }

        ArrayAdapter<String> exadapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item,exArrayList);

        exspinner.setAdapter(exadapter);


        if(sectorArrayList.size() <1){
            try {
                GetSectorList getSectorList = new GetSectorList();
                postsectorData = getSectorList.execute("http://lloasd33.cafe24.com/showsectorlist.php", name).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(postsectorData);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_SECTORJSON);
                for(int i = 0; i <jsonArray.length(); i++){
                    sectorArrayList.add(jsonArray.getJSONObject(i).getString("worksector"));
                }


            }catch (JSONException e){ e.printStackTrace();}
        }

        ArrayAdapter<String> sectoradapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item,sectorArrayList);

        sectorspinner.setAdapter(sectoradapter);

        createWorkNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String workname = editworkname.getText().toString();
                String authorname = editauthorname.getText().toString();
                String workdescription = editworkdescription.getText().toString();
                String exname = exspinner.getSelectedItem().toString();
                String sectorname = sectorspinner.getSelectedItem().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                                            }
                            else{
                                                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                CreateWorkRequest createWorkRequest = new CreateWorkRequest(adminID, workname, authorname, workdescription, exname, sectorname, listener);
                RequestQueue queue = Volley.newRequestQueue(CreateWorkActivity.this);
                queue.add(createWorkRequest);

                Intent intent = new Intent(CreateWorkActivity.this, QRcodeGeneratorActivity. class);
                intent.putExtra("adminID", adminID);
                CreateWorkActivity.this.startActivity(intent);
            }
        });




    }

    private class GetExList extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                String url = strings[0];
                String id = strings[1];
                URL URLObject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) URLObject.openConnection();

                String postparam = "adminID=" + id;

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

    private class GetSectorList extends AsyncTask<String, Void, String>{



        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                String url = strings[0];
                String exname = strings[1];
                URL URLObject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) URLObject.openConnection();

                String postparam = "name=" + exname;

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

