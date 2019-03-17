package com.example.burcu.bpfirstlevel_v1;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WLevelList extends AppCompatActivity {

    ListView listView;
    Button btn2, btn3;

    ArrayList<Model2> mList;
    ArrayList<String>  mList2;

    WLevelListAdapter adapter = null;

    private Bundle extras;

    public static Veritabani veritabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlevel_list);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Kayıtlı Wifi İzleri Listesi");

        btn2 = (Button)findViewById(R.id.btn_topluDb);
        btn3 = (Button)findViewById(R.id.btn_topluDbliste);

        veritabani = new Veritabani(this, "Bitirmedb.sqlite", null, 1 );

        mList2 = new ArrayList<>();
                btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLEDBS" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, wifis VARCHAR)");



                for (int i = 0; i < mList.size(); i++) {

                   mList2.add(mList.get(i).getArray());

                }


                for (int i = 0; i < mList2.size(); i++) {

                   veritabani.insertWifis(mList2.get(i));
                }

                for (int k = 0; k < mList2.size(); k++) {

                   System.out.println("MMMLIST 2  : " + mList2.get(k).toString());
                }
                Snackbar.make(v, "Veri Eklendi", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            veritabani.queryData("CREATE TABLE IF NOT EXISTS TABLEDBS" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, wifis VARCHAR)");

            Intent intent_levet = new Intent(WLevelList.this, ActivityTopluDbListe.class);
            startActivity(intent_levet);

        }
    });

        listView = (ListView)findViewById(R.id.list_view);
        mList = new ArrayList<>();
        adapter = new WLevelListAdapter(this, R.layout.row2, mList);
        listView.setAdapter(adapter);



        extras = getIntent().getExtras();
        String KONUMAD = extras.getString("KonumAd2");
        String hedefKonum = extras.getString("hedefKonum");

        Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM TABLE" + KONUMAD + hedefKonum);
        mList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String array = cursor.getString(1);
//            String hedefKonum2 = cursor.getString(2);
            mList.add(new Model2(id, array, hedefKonum));
        }
        adapter.notifyDataSetChanged();
    }
}
