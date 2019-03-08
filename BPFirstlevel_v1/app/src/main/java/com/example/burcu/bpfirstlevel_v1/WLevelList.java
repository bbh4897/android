package com.example.burcu.bpfirstlevel_v1;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WLevelList extends AppCompatActivity {

    ListView listView;
    Button btn2;

    ArrayList<Model2> mList;
    WLevelListAdapter adapter = null;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlevel_list);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Kayıtlı Levellar Listesi");

        listView = (ListView)findViewById(R.id.list_view);
        mList = new ArrayList<>();
        adapter = new WLevelListAdapter(this, R.layout.row2, mList);
        listView.setAdapter(adapter);



        extras = getIntent().getExtras();
        String BUTTONID = extras.getString("ButtonId2");
        String KONUMAD = extras.getString("KonumAd2");
        Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM TABLE" + BUTTONID + KONUMAD);
        mList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String level = cursor.getString(1);
            mList.add(new Model2(id, level));
        }
        adapter.notifyDataSetChanged();
    }
}
