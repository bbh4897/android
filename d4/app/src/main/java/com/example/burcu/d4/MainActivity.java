package com.example.burcu.d4;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    myAdapter adapter;
    List<ListItem> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItems = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems.add(
                new ListItem("baslikeee","acıklama1"));

        listItems.add(
                new ListItem("baslik2","acıklama2"));

        listItems.add(
                new ListItem(
                        "baslik3",
                        "acıklama3"));

        adapter = new myAdapter(this, listItems.get(listItems.size() - 1),listItems);
        recyclerView.setAdapter(adapter);
    }
}
