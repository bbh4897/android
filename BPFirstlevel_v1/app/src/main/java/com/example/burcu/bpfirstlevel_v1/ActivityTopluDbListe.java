package com.example.burcu.bpfirstlevel_v1;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityTopluDbListe extends AppCompatActivity {

    ListView listView;
    Button btn2, btn3;

    ArrayList<Model3> mList;
    WTopluListAdapter adapter = null;
    private Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toplu_db_liste);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Kayıtlı Tüm Wifi İzleri Listesi");

//        listView = (ListView)findViewById(R.id.list_view);
//        mList = new ArrayList<>();
//        adapter = new WTopluListAdapter(this, R.layout.row3, mList);
//        listView.setAdapter(adapter);
//
//        extras = getIntent().getExtras();
//        String hedefKonum = extras.getString("hedefKonum");
//
//        Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM TABLEDBS");
//        mList.clear();
//        while(cursor.moveToNext()){
//            int id = cursor.getInt(0);
//            String wifis = cursor.getString(1);
//            mList.add(new Model3(id, wifis,hedefKonum ));
//        }
//        adapter.notifyDataSetChanged();
    }
}
