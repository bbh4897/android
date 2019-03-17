package com.example.burcu.bpfirstlevel_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WTopluListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model3> konumList;


    public WTopluListAdapter(Context context, int layout, ArrayList<Model3> konumList) {
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

        TextView txtwifis;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        WTopluListAdapter.ViewHolder holder = new WTopluListAdapter.ViewHolder();

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(layout, null);
            holder.txtwifis = row.findViewById(R.id.txtwifis);

            row.setTag(holder);
        }
        else{
            holder = (WTopluListAdapter.ViewHolder)row.getTag();
        }

        Model3 model = konumList.get(position);
        holder.txtwifis.setText(model.getWifis());

        return row;
    }
}