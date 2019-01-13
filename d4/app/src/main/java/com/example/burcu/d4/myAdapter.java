package com.example.burcu.d4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<ListItem> listItems;
    private Context context;
    ListItem header;

    /////////////////////////////

    private List<ListItem> listItemFull;


    /////////////




    public myAdapter(Context context, ListItem header, List<ListItem> listItems) {
        this.context = context;
        this.header = header;
        this.listItems = listItems;
        /////////
        listItemFull = new ArrayList<>(listItems);
    ////////////////
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
        ((ViewHolder) holder).textViewHead.setText("Başlık : " + listItem.getHead());
        ((ViewHolder) holder).textViewDesc.setText("Açıklama : " + listItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    //////////////////////////////

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ListItem> filteredLİst = new ArrayList<>();

            if(constraint==null || constraint.length()==0){
                filteredLİst.addAll(listItemFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ListItem i : listItemFull){
                    if(i.getHead().toLowerCase().contains(filterPattern)){
                        filteredLİst.add(i);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredLİst;

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listItems.clear();
            listItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    ///////////////////////////

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
