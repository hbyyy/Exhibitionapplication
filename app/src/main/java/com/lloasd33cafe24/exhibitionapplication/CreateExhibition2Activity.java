package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class CreateExhibition2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createexhibition2);


        Button createExhibition = (Button)findViewById(R.id.createExhibition);
        TableLayout tableLayout = (TableLayout)findViewById(R.id.emailListTable);



        createExhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateExhibition2Activity.this, MainActivity.class);
                CreateExhibition2Activity.this.startActivity(intent);
            }
        });
    }
}
