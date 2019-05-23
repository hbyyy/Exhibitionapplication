package com.lloasd33cafe24.exhibitionapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class QRcodeGeneratorActivity extends AppCompatActivity {
   // EditText text;
   // Button gen_btn;
    Button createWorkFinishButton;
    Button saveQRButton;
    ImageView image;
    TextView showworkname;
    private  String adminID;
    String text2Qr;
    private String workName;
    private  String name;
    private LinearLayout qrcodelayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        //text = findViewById(R.id.urlInput);
      //  gen_btn = findViewById(R.id.gen_btn);
        image = findViewById(R.id.codeImage);
        createWorkFinishButton = (Button)findViewById(R.id.createWorkFinishButton);
        saveQRButton = (Button)findViewById(R.id.saveQRButton);
        showworkname = (TextView)findViewById(R.id.showWorkName);
        qrcodelayout = (LinearLayout)findViewById(R.id.QRcodelayout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if( bundle != null ) {
            adminID = bundle.getString("adminID");
            workName = bundle.getString("workName");
            name = bundle.getString("name");
        }

        showworkname.setText(workName);
        qrcodelayout.setDrawingCacheEnabled(true);



                //코드 생성

                text2Qr = workName.trim();
        try {
            text2Qr = new String(text2Qr.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
                 qrcodelayout.buildDrawingCache();

    createWorkFinishButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(QRcodeGeneratorActivity.this, ManagementWorkActiyity.class);
            intent.putExtra("adminID", adminID);
            intent.putExtra("name", name);
            ManagementWorkActiyity ma = (ManagementWorkActiyity) ManagementWorkActiyity._Management_Activity;
            ma.finish();
            QRcodeGeneratorActivity.this.startActivity(intent);
            finish();
             }
        });

     saveQRButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             qrcodelayout = findViewById(R.id.QRcodelayout);

             String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/ExhibitionQRcode";

             File file = new File(path);

             if(!file.exists()){
                 file.mkdir();
             }

             Bitmap capture = qrcodelayout.getDrawingCache();
             try {
                String str = path + "/QRcode_" + workName + ".jpeg";
                 FileOutputStream fos = new FileOutputStream(str);
                 capture.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                 sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + str)));
                 Toast.makeText(QRcodeGeneratorActivity.this, "저장 완료", Toast.LENGTH_LONG).show();
                 fos.flush();
                 fos.close();
                 qrcodelayout.destroyDrawingCache();

             } catch (FileNotFoundException e) {
                 e.printStackTrace();

             } catch (IOException e){
                 e.printStackTrace();
             }

         }
     });

    }


}

