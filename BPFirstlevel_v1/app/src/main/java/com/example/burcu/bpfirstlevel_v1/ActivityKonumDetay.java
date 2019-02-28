package com.example.burcu.bpfirstlevel_v1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityKonumDetay extends AppCompatActivity {

    ImageView imgIcon2;
    TextView txtName2;
    String s_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_detay);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Konum Detay");

        imgIcon2 = (ImageView)findViewById(R.id.imgIcon2);
        txtName2 = (TextView)findViewById(R.id.txtName2);

        Intent intent = getIntent();
        if(intent!=null){
            s_name = intent.getStringExtra("name");
        }

        txtName2.setText(s_name);
    }
}
