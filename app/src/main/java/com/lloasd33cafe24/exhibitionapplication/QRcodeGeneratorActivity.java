package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRcodeGeneratorActivity extends AppCompatActivity {
   // EditText text;
   // Button gen_btn;
    Button createWorkFinishButton;
    ImageView image;
    private  String adminID;
    String text2Qr;
    private String workName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        //text = findViewById(R.id.urlInput);
      //  gen_btn = findViewById(R.id.gen_btn);
        image = findViewById(R.id.codeImage);
        createWorkFinishButton = (Button)findViewById(R.id.createWorkFinishButton);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if( bundle != null ) {
            adminID = bundle.getString(adminID);
            workName = bundle.getString(workName);
        }

        //코드 생성

                text2Qr = workName.trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }




    createWorkFinishButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(QRcodeGeneratorActivity.this, MainActivity.class);
            intent.putExtra("adminID", adminID);
            QRcodeGeneratorActivity.this.startActivity(intent);
        }
    });

    }
}

