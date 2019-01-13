package com.example.burcu.d4;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    myAdapter adapter;
    List<ListItem> listItems;

    test t = new test();
    //////////////////////////////////
    SearchView searchView;

    //////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItems = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        try {
            Log.d( "deneme","----------->"+listItems);
            listItems.addAll(t.execute().get());
            Log.d( "deneme","----------->"+listItems);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new myAdapter(this, listItems.get(listItems.size() - 1),listItems);
        recyclerView.setAdapter(adapter);

        /////////////////////////////////////////////// SearchView

        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    public class test extends AsyncTask<String, Object, List<ListItem>> {


        @Override
        protected List<ListItem> doInBackground(String... strings) {

            List<ListItem> list = new ArrayList<ListItem>();


            list.add(
                    new ListItem("baslikeee","acıklama1"));

            list.add(
                    new ListItem("baslik2","acıklama2"));

            list.add(
                    new ListItem("baslik3","acıklama3"));

            list.add(
                    new ListItem("gggggg","acıklama3"));

            list.add(
                    new ListItem("gggeeeeee","acıklama3"));

            Log.d( "dgfdgfd","----------->"+list);
            return list;
        }
    }



}
