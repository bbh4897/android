package com.example.burcu.bpfirstlevel_v1;

import android.app.admin.SecurityLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityWifiLevel extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView listView;
    private Button btn, btn2, btn3;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter adapter;
    public static Veritabani veritabani;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_level);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Wifi Level");

        btn = findViewById(R.id.btn_wifiscan);
        btn2 = findViewById(R.id.btn_wifilevel);
        btn3 = findViewById(R.id.btn_wifilevelListe);

        veritabani = new Veritabani(this, "Bitirmedb.sqlite", null, 1 );

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extras = getIntent().getExtras();
                String BUTTONID = extras.getString("ButtonId");
                String KONUMAD = extras.getString("KonumAd");
                veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLE" + BUTTONID + KONUMAD + "(id INTEGER PRIMARY KEY AUTOINCREMENT, level VARCHAR, frekans VARCHAR)");



                Intent intent_levet = new Intent(ActivityWifiLevel.this, WLevelList.class);
                intent_levet.putExtra("ButtonId2", BUTTONID);
                intent_levet.putExtra("KonumAd2", KONUMAD);
                startActivity(intent_levet);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });


        listView = findViewById(R.id.listViewWifi);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(!wifiManager.isWifiEnabled()){
            Toast.makeText(ActivityWifiLevel.this,"wifiye baglan",Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        scanWifi();
    }

    private void scanWifi(){
        arrayList.clear();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this, "Wifi Erişim Noktaları Listeleniyor", Toast.LENGTH_LONG).show();
    }



    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            for(ScanResult scanResult : results){
                String s_level = String.valueOf(scanResult.level * (-1));
                String s_frekans = String.valueOf(scanResult.frequency);
                arrayList.add(s_level + " - " + s_frekans);
                adapter.notifyDataSetChanged();

            }
//            for(int i = 0; i<arrayList.size();i++){
//                Log.i("hkjh" , arrayList.get(i));
//            }

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        extras = getIntent().getExtras();
                        String BUTTONID = extras.getString("ButtonId");
                        String KONUMAD = extras.getString("KonumAd");

                        veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLE" + BUTTONID + KONUMAD + "(id INTEGER PRIMARY KEY AUTOINCREMENT, level VARCHAR, frekans VARCHAR)");

                        for (int i = 0; i < arrayList.size(); i++) {
                            veritabani.insertWLevel(arrayList.get(i),arrayList.get(i), BUTTONID, KONUMAD);

                            Log.i("hkjh" , arrayList.get(i) + " " + BUTTONID);


                        }

                        Snackbar.make(v, "Wifi Bilgileri Eklendi.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });



        }
    };
}
