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

    String anlik_dizi[][];
    String db_dizi[][];
    String oklit_dizi[][];


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
                arrayList.add(scanResult.BSSID + " - " + scanResult.level);
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
//                    System.out.println(" KONUM BUL : " + mList.get(i).getWifis().substring(20,22));
                    mList2.add(mList.get(i).getWifis());
                    System.out.println("DB'deki Wifi Izlerı :    " + mList2.get(i));
                }

                for(int y=0; y<arrayList.size();y++) {
                    System.out.println("Anlık Wifi İzleri : " + arrayList.get(y));
                }

                anlik_dizi = new String[arrayList.size()][2];
                db_dizi = new String[mList2.size()][4];


                for(int i=0;i<anlik_dizi.length;i++) {

                    anlik_dizi[i][0] = arrayList.get(i).substring(0,17);
                    anlik_dizi[i][1] = arrayList.get(i).substring(21,23);
                    System.out.println("ANLIK DİZİ ELEMANLARI BSSID : " + anlik_dizi[i][0]);
                    System.out.println("ANLIK DİZİ ELEMANLARI LEVEL : " + anlik_dizi[i][1]);

                }

                for(int i=0;i<db_dizi.length;i++) {

                    db_dizi[i][0] = mList2.get(i).substring(0,17);
                    db_dizi[i][1] = mList2.get(i).substring(20,22);
                    db_dizi[i][2] = mList2.get(i).substring(25,35);
                    System.out.println("DB DİZİ ELEMANLARI BSSID : " + db_dizi[i][0]);
                    System.out.println("DB DİZİ ELEMANLARI LEVEL : " + db_dizi[i][1]);
                    System.out.println("DB DİZİ ELEMANLARI BUTONID : " + db_dizi[i][2]);

                }


                int count=1;

                for(int a=0; a<anlik_dizi.length;a++){
                    for(int b=0; b<db_dizi.length;b++) {

                        if (anlik_dizi[a][0].equals(db_dizi[b][0])) {


                            System.out.println("AYNI BSSID : " + db_dizi[b][0] + " - " + db_dizi[b][2] + " BOYUT : " + count );
                            oklit_dizi = new String[count][2];

                            int level = Integer.parseInt(db_dizi[b][1]);
                            int oklit = ( (Integer.parseInt(anlik_dizi[a][1])) - (Integer.parseInt(db_dizi[b][1])) );

                            System.out.println("OKLIT SONUC : " + oklit);

                            if(oklit<0){
                                oklit = oklit * (-1);
                                String s_oklit = String.valueOf(oklit);
                                oklit_dizi[count][0] = s_oklit;
                                oklit_dizi[count][1] = db_dizi[b][2];
                                System.out.println("OKLIT SONUC EKSİ : \n LEVEL : " +  oklit_dizi[count][0] + " BUTONID : " + oklit_dizi[count][1] );
                            }
                            else{
                                String s_oklit = String.valueOf(oklit);
                                oklit_dizi[count][0] = s_oklit;
                                oklit_dizi[count][1] = db_dizi[b][2];
                                System.out.println("OKLIT SONUC ARTI : \n LEVEL : " +  oklit_dizi[count][0] + " BUTONID : " + oklit_dizi[count][1] );
                            }


                            count++;






                        }
                    }
                }

             }
            });


        }
    };

}
