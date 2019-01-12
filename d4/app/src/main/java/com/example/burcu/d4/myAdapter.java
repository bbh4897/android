package com.example.burcu.d4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    ListItem header;

    public myAdapter(Context context, ListItem header, List<ListItem> listItems) {
        this.context = context;
        this.header = header;
        this.listItems = listItems;
    }








    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

        ListItem listItem = listItems.get(i);
        myAdapter.ViewHolder vItem = (myAdapter.ViewHolder) holder;
        ((ViewHolder) holder).textViewHead.setText(listItem.getHead());
        ((ViewHolder) holder).textViewDesc.setText(listItem.getDesc());



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewDesc;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView)itemView.findViewById(R.id.textHead);
            textViewDesc = (TextView)itemView.findViewById(R.id.textDesc);
        }
    }
}
