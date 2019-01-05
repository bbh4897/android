package com.example.burcu.asynctask_v1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.print("yyyyyyyyyyyyyy");

       // AsyncTask a = new Asenkron();
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Asenkron nesne = new Asenkron();
                System.out.print("NESNE OLUSTU");
                nesne.execute();

            }
        });

    }


    public class Asenkron extends AsyncTask<String, String, List<String>>
    {

        private ProgressDialog progressDialog;
       // private AcyncTaskClass a = null;


        @Override
        protected void onPreExecute(){

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Yüklenıyor....");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            System.out.print("PREe");
            progressDialog.show();

        }

        @Override
        protected List<String> doInBackground(String... arg) {

            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("12");
            arrayList.add("78");
            arrayList.add("5");
            arrayList.add("99");
            arrayList.add("1");

            System.out.print("DOINBACK");

            return arrayList;
        }

        @Override
        protected void onPostExecute(List<String> str) {

            super.onPostExecute(str);
            System.out.print("POST");
            progressDialog.dismiss();

        }
    }

}
