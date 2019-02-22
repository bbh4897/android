package com.example.burcu.recycleviewexamp;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable{

    private List<ExampleItem> exampleList;
    private List<ExampleItem> exampleListFull;

    class ExampleViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView textView2;

        ExampleViewHolder(View itemView){

            super(itemView);

            textView = itemView.findViewById(R.id.text);
            textView = itemView.findViewById(R.id.text2);

        }
    }


    ExampleAdapter(List<ExampleItem> exampleList){

        this.exampleList = exampleList;
        this.exampleListFull = new ArrayList<>(exampleList);

    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup.parent, int viewType){



    }
}
