package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private ArrayList<String> testList;
    private Context context;
    private OnPropertyClickedListener clickedListener;


    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public PropertyAdapter(ArrayList<String> testList, Context context, OnPropertyClickedListener clickedListener) {
        this.testList = testList;
        this.context = context;
        this.clickedListener = clickedListener;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final View itemView = holder.itemView.findViewById(R.id.item_property);
        final TextView type = holder.itemView.findViewById(R.id.item_type_tv);
        type.setText(testList.get(position));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
clickedListener.onClicked(String.valueOf(position));

            }
        });

    }


    @Override
    public int getItemCount() {
        return testList.size();
    }
}


