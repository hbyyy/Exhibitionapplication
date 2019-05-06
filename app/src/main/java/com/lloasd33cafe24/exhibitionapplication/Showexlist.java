package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.lloasd33cafe24.exhibitionapplication.R;

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

public class Showexlist extends AppCompatActivity {

    private String TAG_JSON = "showlist";
    private String postdata;
    private ArrayList<String> mArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showexlist);

        final String adminID;
        final Button button = (Button) findViewById(R.id.selectbutton);
        ListView listview = (ListView) findViewById(R.id.exlist);
        mArrayList = new ArrayList<String>();

        Intent intent = getIntent();
        adminID = intent.getStringExtra("adminID");



        try {
            Getlist getlist = new Getlist();
            postdata = getlist.execute("http://lloasd33.cafe24.com/showexhibitionlist.php", adminID).get();
        } catch (Exception e) { e.printStackTrace();}

        try {
            JSONObject jsonObject = new JSONObject(postdata);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for(int i = 0; i <jsonArray.length(); i++){
                mArrayList.add(jsonArray.getJSONObject(i).getString("name"));
            }


        }catch (JSONException e){ e.printStackTrace();}


            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, mArrayList);

            listview.setAdapter(adapter);


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    intent.putExtra("name", mArrayList.get(position));
                    intent.putExtra("adminID", adminID);
                    startActivity(intent);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }



    private class Getlist extends AsyncTask<String, Void, String>{



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

}






