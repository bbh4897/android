package com.example.burcu.test2;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class test extends AsyncTask<String, String, ArrayList<String>> {


    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("jkljl");
        list.add("nnnnn");
        list.add("nnnnn");
        list.add("aaaaa");
        list.add("nnnnn");
        list.add("nnnnn");
        list.add("nnnnn");
        list.add("oooo");

        System.out.println("Liste : " + list);
        return list;
    }
}
