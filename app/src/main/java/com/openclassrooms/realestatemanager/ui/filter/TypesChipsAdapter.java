package com.openclassrooms.realestatemanager.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.openclassrooms.realestatemanager.R;

public class TypesChipsAdapter extends RecyclerView.Adapter {
    private Context context;
    private String[] types;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    }}

    public TypesChipsAdapter(String[] types, Context context) {
        this.types = types;
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chips, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChipGroup chipGroup = new ChipGroup(context);
        Chip chip = new Chip(chipGroup.getContext());
        chipGroup.addView(chip);
        chip.setText(types[position]);



    }

    @Override
    public int getItemCount() {
        return types.length;
    }

}
