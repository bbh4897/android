package com.example.burcu.bpfirstlevel_v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        public void onReceive(final Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            for(ScanResult scanResult : results){
                arrayList.add(scanResult.BSSID + " - " + scanResult.level);
//                adapter.notifyDataSetChanged();

            }
            btn_konumBul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = getIntent();
                    if(intent!=null){
                        s_name = intent.getStringExtra("name");
                    }

                    veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLEDBS" + s_name + "(id INTEGER PRIMARY KEY AUTOINCREMENT, wifis VARCHAR)");

                    Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM TABLEDBS" + s_name);
                    mList.clear();
                    while(cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String wifis = cursor.getString(1);
                        mList.add(new Model3(id, wifis)); // toplu olarak hedef konum verılerı
                    }
                    if(mList.size()==0){
                        Snackbar.make(v, "Daha Önce Burada Bulunmadınız!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                else{


                for(int i=0; i<mList.size();i++){
//                    System.out.println(" KONUM BUL : " + mList.get(i).getWifis().substring(20,22));
                    mList2.add(mList.get(i).getWifis());
                    //System.out.println("DB'deki Wifi Izlerı :    " + mList2.get(i));
                }

                for(int y=0; y<arrayList.size();y++) {
                    //System.out.println("Anlık Wifi İzleri : " + arrayList.get(y));
                }

                anlik_dizi = new String[arrayList.size()][2];
                db_dizi = new String[mList2.size()][4];


                System.out.println("ANLIK DİZİ ELEMANLARI \n");
                for(int i=0;i<anlik_dizi.length;i++) {

                    anlik_dizi[i][0] = arrayList.get(i).substring(0,17);
                    anlik_dizi[i][1] = arrayList.get(i).substring(21,23);
                    System.out.println("BSSID : " + anlik_dizi[i][0] + " Level : " + anlik_dizi[i][1] );

                }

                System.out.println("DB DİZİ ELEMANLARI \n");

                for(int i=0;i<db_dizi.length;i++) {

                    db_dizi[i][0] = mList2.get(i).substring(0,17);
                    db_dizi[i][1] = mList2.get(i).substring(20,22);
                    db_dizi[i][2] = mList2.get(i).substring(25,35);
                    db_dizi[i][3] = mList2.get(i).substring(38);
                    System.out.println("BSSID : " + db_dizi[i][0] + " LEVEL : " +  db_dizi[i][1] + " BUTTONID : " + db_dizi[i][2] + " HEDEF KONUM : " + db_dizi[i][3]);

                }


                int count=1;
                oklit_dizi = new String[500][3];

                int oklit=0;
                for(int a=0; a<anlik_dizi.length;a++){

                    for(int b=0; b<db_dizi.length;b++) {

                        if (anlik_dizi[a][0].equals(db_dizi[b][0])) {


                            System.out.println("AYNI BSSID : " + db_dizi[b][0] + " LEVEL ANLIK :  " + anlik_dizi[a][1] +
                                    " LEVEL DB : " + db_dizi[b][1] + " BUTONID : " + db_dizi[b][2] + " HEDEF KONUM : " + db_dizi[b][3] + " BOYUT : " + count );


                            int level = Integer.parseInt(db_dizi[b][1]);
                            oklit = ( (Integer.parseInt(anlik_dizi[a][1])) - (Integer.parseInt(db_dizi[b][1])) );

                            //System.out.println("OKLIT SONUC : " + oklit);

                            if(oklit<0){
                                oklit = oklit * (-1);
                                String s_oklit = String.valueOf(oklit);
                                oklit_dizi[count][0] = s_oklit;
                                oklit_dizi[count][1] = db_dizi[b][2];
                                oklit_dizi[count][2] = db_dizi[b][3];
                                System.out.println("Oklit Sonuc \n");
                                System.out.println("LEVEL : " +  oklit_dizi[count][0] + " BUTONID : " + oklit_dizi[count][1]  + " HEDEF : " +oklit_dizi[count][2]);
                            }
                            else{
                                String s_oklit = String.valueOf(oklit);
                                oklit_dizi[count][0] = s_oklit;
                                oklit_dizi[count][1] = db_dizi[b][2];
                                oklit_dizi[count][2] = db_dizi[b][3];
                                System.out.println("Oklit Sonuc \n");
                                System.out.println("LEVEL : " +  oklit_dizi[count][0] + " BUTONID : " + oklit_dizi[count][1]  + " HEDEF : " +oklit_dizi[count][2]);
                            }


                            count++;

                        }



                    }

                    int t_level;
                    String t_buttonId, t_hedef;
                    boolean is_sorted;

                    //////////////////////////////////////////////////////////////// SIRLAMA
                    for (int i = 1; i <= count; i++) {

                        is_sorted = true;

                        for (int j = 2; j <= (count - i); j++) {

                            if (Integer.parseInt(oklit_dizi[j - 1][0]) > Integer.parseInt(oklit_dizi[j][0])) {

                                t_level = Integer.parseInt(oklit_dizi[j - 1][0]);
                                oklit_dizi[j - 1][0] = oklit_dizi[j][0];
                                oklit_dizi[j][0] = String.valueOf(t_level);

                                t_buttonId = oklit_dizi[j - 1][1];
                                oklit_dizi[j - 1][1] = oklit_dizi[j][1];
                                oklit_dizi[j][1] =t_buttonId;

                                t_hedef = oklit_dizi[j - 1][2];
                                oklit_dizi[j - 1][2] = oklit_dizi[j][2];
                                oklit_dizi[j][2] = t_hedef;


                                is_sorted = false;
                            }

                        }

                        // is sorted? then break it, avoid useless loop.
                        if (is_sorted) break;

                        System.out.println("\n");

                    }

                    oklit_dizi[a+1][0] = oklit_dizi[1][0];
                    oklit_dizi[a+1][1] = oklit_dizi[1][1];
                    oklit_dizi[a+1][2] = oklit_dizi[1][2];
                }


                System.out.print("********************************************************************** \n");

                System.out.println("SIRASIZ HAL \n");

                for(int i =1; i<count;i++){

//                   System.out.println("Oklıt Dızısı SONN : " + oklit_dizi[i][0] + " - " + oklit_dizi[i][1] + " - " + oklit_dizi[i][2]);
                   System.out.println(oklit_dizi[i][0] + " - " + oklit_dizi[i][1] + " - " + oklit_dizi[i][2]);

                }

                System.out.println("Count : " + count);

                    int t_level;
                    String t_buttonId, t_hedef;
                    boolean is_sorted;

    //////////////////////////////////////////////////////////////// SIRALAMA BALONCUK
                    for (int i = 1; i <= count; i++) {

                        is_sorted = true;

                        for (int j = 2; j <= (count - i); j++) {

                            if (Integer.parseInt(oklit_dizi[j - 1][0]) > Integer.parseInt(oklit_dizi[j][0])) {

                                t_level = Integer.parseInt(oklit_dizi[j - 1][0]);
                                oklit_dizi[j - 1][0] = oklit_dizi[j][0];
                                oklit_dizi[j][0] = String.valueOf(t_level);

                                t_buttonId = oklit_dizi[j - 1][1];
                                oklit_dizi[j - 1][1] = oklit_dizi[j][1];
                                oklit_dizi[j][1] =t_buttonId;

                                t_hedef = oklit_dizi[j - 1][2];
                                oklit_dizi[j - 1][2] = oklit_dizi[j][2];
                                oklit_dizi[j][2] = t_hedef;


                                is_sorted = false;
                            }

                        }

                        // is sorted? then break it, avoid useless loop.
                        if (is_sorted) break;

                        System.out.println("\n");

                    }

//////////////////////////////////////////////////////////////////////////

                    System.out.println("SIRALI HAL \n");

                    for(int i =1; i<count;i++){

//                   System.out.println("Oklıt Dızısı SONN : " + oklit_dizi[i][0] + " - " + oklit_dizi[i][1] + " - " + oklit_dizi[i][2]);
                        System.out.println(oklit_dizi[i][0] + " - " + oklit_dizi[i][1] + " - " + oklit_dizi[i][2]);

                    }

                    System.out.println("İLK K \n");

                    for(int i =1; i<=13;i++){

//                   System.out.println("Oklıt Dızısı SONN : " + oklit_dizi[i][0] + " - " + oklit_dizi[i][1] + " - " + oklit_dizi[i][2]);
                        System.out.println(oklit_dizi[i][0] + " - " + oklit_dizi[i][1] + " - " + oklit_dizi[i][2]);

                    }

                    ////////////// HEDEF KONUMU FAZLA OLANI BULMA

                    int k=13;
                    Map<String,Integer> tekrarMap= new HashMap<String,Integer>();
                    for(int i=1; i<=k ;i++){

                        if(tekrarMap.containsKey(oklit_dizi[i][2])) {
                            tekrarMap.put(oklit_dizi[i][2],tekrarMap.get(oklit_dizi[i][2]) + 1);
                        }
                        else {
                            tekrarMap.put(oklit_dizi[i][2], 1);
                        }
                    }

                    int c = 0;
                    for(int tekrarCount : tekrarMap.values()){
                        if(tekrarCount > 1) {
                            c++;
                        }
                    }
                    System.out.println("\nTekrar Eden Konum Sayısı : " + c + " Tekrar Eden Konumlar  : " + tekrarMap.toString());

                    Map.Entry<String, Integer> ilkVeri = tekrarMap.entrySet().iterator().next();

                    String encokKey = ilkVeri.getKey();
                    int encokValue = ilkVeri.getValue();

                    for (Map.Entry<String, Integer> map : tekrarMap.entrySet()) {

                        int value = map.getValue();
                        if (value > encokValue) {
                            encokValue = value;
                            encokKey = map.getKey();
                        }
                    }

                    System.out.println("\nEn çok bulunan Konum       : " + encokKey);
                    System.out.println("\nEn çok bulunan Konum Sayısı : " + encokValue);

                    //Toast.makeText(ActivityKonumDetay.this, "Şu an " + largestKey + " 'dasınız." , Toast.LENGTH_LONG).show();

                    Snackbar.make(v, "\nŞu an " + encokKey + " 'dasınız.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        for(int i=1; i<oklit_dizi.length;i++){
                        if(encokKey.equals(oklit_dizi[i][2])){
                            Button b = (Button)findViewById(Integer.parseInt(oklit_dizi[i][1]));
                            Drawable d = getResources().getDrawable(R.drawable.button_konum_bul);
                            //b.setBackgroundColor(b.getContext().getResources().getColor(R.color.green_konum));
                            b.setBackground(d);
                        }
                    }

                    }
                 }
            });


        }
    };



}
