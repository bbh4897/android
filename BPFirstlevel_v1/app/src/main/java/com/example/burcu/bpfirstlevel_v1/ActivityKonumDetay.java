package com.example.burcu.bpfirstlevel_v1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityKonumDetay extends AppCompatActivity {

    ImageView imgIcon2;
    String s_name;
    Bitmap bmp;
    Button btn_wifiListe;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_detay);

        ActionBar actionBar = getSupportActionBar();


        imgIcon2 = (ImageView)findViewById(R.id.imgIcon2);

        Intent intent = getIntent();
        if(intent!=null){
            s_name = intent.getStringExtra("name");
            byte[] bytes = getIntent().getByteArrayExtra("image");
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        actionBar.setTitle("Konum: " + s_name);
        imgIcon2.setImageBitmap(bmp);

        btn_wifiListe = (Button)findViewById(R.id.wifiListesi);

        btn_wifiListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(ActivityKonumDetay.this, ActivityWifiListe.class);
                startActivity(intent2);

            }
        });


    }
    public void btnIzgara(View view){

        Button b = (Button)view;

        b.setBackgroundColor(b.getContext().getResources().getColor(R.color.transparan));



           long id = b.getId();
            String s_id = String.valueOf(id);

          intent = new Intent(ActivityKonumDetay.this, ActivityWifiLevel.class);
          intent.putExtra("ButtonId", s_id);
          intent.putExtra("KonumAd", s_name);
          startActivity(intent);
    }

}
