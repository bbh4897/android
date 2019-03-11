package com.example.burcu.bpfirstlevel_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WLevelListAdapter  extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model2> konumList;


    public WLevelListAdapter(Context context, int layout, ArrayList<Model2> konumList) {
        this.context = context;
        this.layout = layout;
        this.konumList = konumList;
    }

    @Override
    public int getCount() {
        return konumList.size();
    }

    @Override
    public Object getItem(int position) {
        return konumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{

        TextView txtarray;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        WLevelListAdapter.ViewHolder holder = new WLevelListAdapter.ViewHolder();

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(layout, null);
            holder.txtarray = row.findViewById(R.id.txtarray);

            row.setTag(holder);
        }
        else{
            holder = (WLevelListAdapter.ViewHolder)row.getTag();
        }

        Model2 model = konumList.get(position);
        holder.txtarray.setText(model.getArray());


        return row;
    }
}