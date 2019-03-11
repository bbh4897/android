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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityWifiLevel extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView listView;
    private EditText hedefKonum;
    private Button btn, btn2, btn3;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter adapter;
    public static Veritabani veritabani;
    private Bundle extras;
    String BUTTONID, s_level, KONUMAD, s_bssid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_level);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Wifi Level");

        btn = findViewById(R.id.btn_wifiscan);
        btn2 = findViewById(R.id.btn_wifilevel);
        btn3 = findViewById(R.id.btn_wifilevelListe);
        hedefKonum = (EditText)findViewById(R.id.hedefKonum);

        veritabani = new Veritabani(this, "Bitirmedb.sqlite", null, 1 );

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extras = getIntent().getExtras();
                KONUMAD = extras.getString("KonumAd");
                BUTTONID = extras.getString("ButtonId");
                veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLE" + KONUMAD + hedefKonum.getText().toString().trim() + "(id INTEGER PRIMARY KEY AUTOINCREMENT, array VARCHAR)");



                Intent intent_levet = new Intent(ActivityWifiLevel.this, WLevelList.class);
                intent_levet.putExtra("ButtonId2", BUTTONID);
                intent_levet.putExtra("KonumAd2", KONUMAD);
                intent_levet.putExtra("hedefKonum", hedefKonum.getText().toString());
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

            extras = getIntent().getExtras();
            KONUMAD = extras.getString("KonumAd");
            BUTTONID = extras.getString("ButtonId");

            for(ScanResult scanResult : results){
                s_level = String.valueOf(scanResult.level * (-1));
                s_bssid = String.valueOf(scanResult.BSSID);
                arrayList.add(s_bssid + " - " + s_level + " - " + hedefKonum.getText().toString().trim() + " - " + BUTTONID);
                adapter.notifyDataSetChanged();

            }

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        extras = getIntent().getExtras();
                        BUTTONID = extras.getString("ButtonId");
                        KONUMAD = extras.getString("KonumAd");

                        veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLE" + KONUMAD + hedefKonum.getText().toString().trim() + "(id INTEGER PRIMARY KEY AUTOINCREMENT, array VARCHAR)");

                        for (int i = 0; i < arrayList.size(); i++) {
                            veritabani.insertWLevel(arrayList.get(i),KONUMAD, hedefKonum.getText().toString().trim());

                            Log.i("ARRAY VE BUTONID  " , arrayList.get(i) + " " + BUTTONID + "HEDEF KONUMMM " + hedefKonum.getText().toString().trim());


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
