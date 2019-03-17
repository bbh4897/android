package com.example.burcu.bpfirstlevel_v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityKonumDetay extends AppCompatActivity {

    ImageView imgIcon2;
    String s_name;
    Bitmap bmp;
    Button btn_wifiListe, btn_konumBul;
    public Intent intent;
    public static Veritabani veritabani;
    ArrayList<Model3> mList;
    ArrayList<String> mList2;
    ArrayList<String> arrayList= new ArrayList<>();


    public WifiManager wifiManager;
    public List<ScanResult> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_detay);

        ActionBar actionBar = getSupportActionBar();

        veritabani = new Veritabani(this, "Bitirmedb.sqlite", null, 1 );
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
        btn_konumBul = (Button)findViewById(R.id.konumBul);

        mList = new ArrayList<>();
        mList2 = new ArrayList<>();


        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(!wifiManager.isWifiEnabled()){
            Toast.makeText(ActivityKonumDetay.this,"wifiye baglan",Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        scanWifi();

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

    private void scanWifi(){
        arrayList.clear();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(ActivityKonumDetay.this, "Wifi Erişim Noktaları Listeleniyor", Toast.LENGTH_LONG).show();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            for(ScanResult scanResult : results){
                arrayList.add(scanResult.SSID + " - " + scanResult.BSSID + " ** " + scanResult.level + " - " + scanResult.frequency);
//                adapter.notifyDataSetChanged();

            }
            btn_konumBul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLEDBS" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, wifis VARCHAR)");

                    Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM TABLEDBS");
                    mList.clear();
                    while(cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String wifis = cursor.getString(1);
                        mList.add(new Model3(id, wifis)); // toplu olarak hedef konum verılerı
                    }

                for(int i=0; i<mList.size();i++){
                   System.out.println(" KONUM BUL : " + mList.get(i).getWifis());
                   // mList2.add(mList.get(i).getWifis().substring(20,36));
                    //System.out.println("mlist2 :    " + mList2.get(i));
                }

                    for(int y=0; y<arrayList.size();y++) {
                        System.out.println("SCAN WİFİ : " + arrayList.get(y));
                    }
                }
            });


        }
    };

}
