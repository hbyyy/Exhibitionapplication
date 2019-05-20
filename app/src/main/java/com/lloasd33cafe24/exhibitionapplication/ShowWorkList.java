package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class ShowWorkList extends AppCompatActivity {

    private String TAG_WORKJSON = "showworklist";
    private String postdata;
    private ArrayList<String> mArrayList;
    private String name;
    private String adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_work_list);

        ListView listview = (ListView)findViewById(R.id.worklist);
        mArrayList = new ArrayList<>();

        Button cancleButton = findViewById(R.id.cancleButton2);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        adminID = intent.getStringExtra("adminID");

        try {
            Getworklist getworklist = new Getworklist();
            postdata = getworklist.execute("http://lloasd33.cafe24.com/showworklist.php", name).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(postdata);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_WORKJSON);
            for(int i = 0; i < jsonArray.length(); i++){
                mArrayList.add(jsonArray.getJSONObject(i).getString("workname"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, mArrayList);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ManagementWorkActiyity.class);

                intent.putExtra("adminID", adminID);
                intent.putExtra("workname", mArrayList.get(position));
                intent.putExtra("name", name);
                startActivity(intent);
                finish();

            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class Getworklist extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                String url = strings[0];
                String workname = strings[1];
                URL URLObject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) URLObject.openConnection();

                String postparam = "name=" + workname;

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
