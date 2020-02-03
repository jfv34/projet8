package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private ArrayList<String> testList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public PropertyAdapter(ArrayList<String> testList, Context context) {
        this.testList = testList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_property, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView test_tv = holder.itemView.findViewById(R.id.item_test_tv);
        test_tv.setText(testList.get(position));
        test_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }


    @Override
    public int getItemCount() {
        return testList.size();
    }
}


