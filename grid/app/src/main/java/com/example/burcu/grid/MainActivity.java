package com.example.burcu.grid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void btnIzgara(View view){

        Button b = (Button)view;
        String buttonText = b.getText().toString();
            Toast.makeText(MainActivity.this, buttonText, Toast.LENGTH_LONG).show();
        }

    }



