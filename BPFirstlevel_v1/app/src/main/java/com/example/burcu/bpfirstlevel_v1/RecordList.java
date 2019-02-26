package com.example.burcu.bpfirstlevel_v1;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecordList extends AppCompatActivity {
    ListView listView;
    Button btn2;

    ArrayList<Model> mList;
    KonumListAdapter adapter = null;

    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        listView = (ListView)findViewById(R.id.list_view);
        mList = new ArrayList<>();
        adapter = new KonumListAdapter(this, R.layout.row, mList);
        listView.setAdapter(adapter);



        Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM KONUM_BILGILERI");
        mList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            mList.add(new Model(id, name, image));
        }
        adapter.notifyDataSetChanged();
    }
}
