package com.example.burcu.bpfirstlevel_v1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class KonumListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model> konumList;

    public KonumListAdapter(Context context, int layout, ArrayList<Model> konumList) {
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

        ImageView image_view;
        TextView txtName;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(layout, null);
            holder.txtName = row.findViewById(R.id.txtName);
            holder.image_view = row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder)row.getTag();
        }

        Model model = konumList.get(position);
        holder.txtName.setText(model.getName());

        byte[] recordImage = model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        holder.image_view.setImageBitmap(bitmap);
        return row;
    }
}
