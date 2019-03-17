package com.example.burcu.bpfirstlevel_v1;

import android.app.admin.SecurityLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.TypedArrayUtils;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ActivityWifiLevel extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView listView;
    private EditText hedefKonum;
    private Button btn, btn2, btn3, btn4;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2;
    private ArrayAdapter adapter;
    public static Veritabani veritabani;
    private Bundle extras;
    private String BUTTONID, s_level, KONUMAD, s_bssid, bssid, level, buttonId;
    private String dizi[][], dizi2[][];
    HashSet<String> set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_level);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Wifi Level");

        btn = findViewById(R.id.btn_wifiscan);
        btn2 = findViewById(R.id.btn_wifilevel);
        btn3 = findViewById(R.id.btn_wifilevelListe);
        btn4 = findViewById(R.id.btn_bitir);
        hedefKonum = (EditText)findViewById(R.id.hedefKonum);

        veritabani = new Veritabani(this, "Bitirmedb.sqlite", null, 1 );

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extras = getIntent().getExtras();
                KONUMAD = extras.getString("KonumAd");
                BUTTONID = extras.getString("ButtonId");
                veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLE" + KONUMAD + hedefKonum.getText().toString().trim() + "(id INTEGER PRIMARY KEY AUTOINCREMENT, array VARCHAR, hedefKonum VARCHAR)");

                Intent intent_levet = new Intent(ActivityWifiLevel.this, WLevelList.class);
                intent_levet.putExtra("ButtonId2", BUTTONID);
                intent_levet.putExtra("KonumAd2", KONUMAD);
                intent_levet.putExtra("hedefKonum", hedefKonum.getText().toString());
                startActivity(intent_levet);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Model2> mList = new ArrayList<>();

                extras = getIntent().getExtras();
                KONUMAD = extras.getString("KonumAd");

                arrayList2 = new ArrayList<>();
                set = new HashSet<String>();

                Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM TABLE" + KONUMAD + hedefKonum.getText().toString());
                mList.clear();
                while(cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String array = cursor.getString(1);
//            String hedefKonum2 = cursor.getString(2);
                    mList.add(new Model2(id, array, hedefKonum.getText().toString().trim()));
                }

                dizi = new String[mList.size()][3];

                   for (int i = 0; i < mList.size(); i++) {

                       Model2 m = mList.get(i);

                       bssid = m.getArray().substring(0, 17);
                       level = (m.getArray().substring(20, 22));
                       buttonId = (m.getArray().substring(25, 35));

                       for (int j = 0; j <mList.size(); j++) {

                           dizi[i][0] = bssid;
                           dizi[i][1] = level;
                           dizi[i][2] = buttonId;

                       }
                   }

                   System.out.println("Liste Boyutu : " + mList.size() + " Dizi Boyutu : " + dizi.length);
                   System.out.println("Dizi BSSID : " + dizi[2][0]); // 5. elemanı almak ıcın 4. ındıs kullanılır
                   System.out.println("Dizi LEVEL : " + dizi[2][1]);
                   System.out.println("Dizi BUTONID : " + dizi[2][2]);

                   System.out.println("Hedef Konum : " + hedefKonum.getText().toString());


                   int a=0;
//                           System.out.println(k+1 + ". Eleman : \n" + "BSSID : " + dizi[k][0] + " LEVEL : " + dizi[k][1] + " BUTONID : " + dizi[k][2] + " Hedefffff : " + hedefKonum.getText().toString());
                   while (a != dizi.length -1 ){

                      for(int k=1; k<dizi.length; k++){

                         if (dizi[a][0].equals(dizi[k][0])){

                           System.out.println("AYNI : " + " Bırıncı : " + dizi[a][0] + " AYNI OLAN : " + dizi[k][0]);

                           int level1 = Integer.parseInt(dizi[a][1]);
                           int level2 = Integer.parseInt(dizi[k][1]);

                           if( level1 < level2){

                               dizi[k][1] = dizi[a][1];
                           }

                           else if (level2 < level1){
                               dizi[a][1] = dizi[k][1];
                           }
                           else if (level2 == level1){
                               dizi[a][1] = dizi[k][1];
                           }

                         }else{

                           System.out.println("FARKLI : " + " Bırıncı : " + dizi[a][0] + " FARKLI OLAN : " + dizi[k][0]);
                          }
                      }
                      a++;
                   }

                   for(int y=0; y<dizi.length; y++){
                       System.out.println("\nDİZİ : \n" + "BSSID :  " + dizi[y][0]+ " LEVEL : " + dizi[y][1] + " BUTON ID : " + dizi[y][2] +
                       " HEdef KONUM : " + hedefKonum.getText().toString());

                       set.add(dizi[y][0] + " - " + dizi[y][1] + " - " + dizi[y][2] );
                   }

                Iterator<String> itr=set.iterator();
                while(itr.hasNext()){
//                    System.out.println(" HASHSET : " + itr.next());
                    arrayList2.add(itr.next());
                }

                veritabani.queryData("DELETE FROM TABLE" + KONUMAD + hedefKonum.getText().toString());
                for (int i = 0; i < arrayList2.size(); i++) {
                    veritabani.insertWLevel(arrayList2.get(i),KONUMAD, hedefKonum.getText().toString().trim());
                    System.out.println("testttttt : " + arrayList2.get(i).toString());
                }




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
                arrayList.add(s_bssid + " - " + s_level + " - " + BUTTONID + " - ");
                adapter.notifyDataSetChanged();

            }

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        extras = getIntent().getExtras();
                        BUTTONID = extras.getString("ButtonId");
                        KONUMAD = extras.getString("KonumAd");
                        hedefKonum = (EditText)findViewById(R.id.hedefKonum);

                        veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLE" + KONUMAD + hedefKonum.getText().toString().trim() + "(id INTEGER PRIMARY KEY AUTOINCREMENT, array VARCHAR, hedefKonum VARCHAR)");

                        for (int i = 0; i < arrayList.size(); i++) {
                            veritabani.insertWLevel(arrayList.get(i),KONUMAD, hedefKonum.getText().toString().trim());

                            Log.i("ARRAY VE BUTONID  " , arrayList.get(i) + " " + BUTTONID + " HEDEF KONUMMM " + hedefKonum.getText().toString().trim());

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
