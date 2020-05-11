package com.openclassrooms.realestatemanager.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.interfaces_clickedListener.OnChipClickedListener;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;

public class TypesChipsAdapter extends RecyclerView.Adapter {
    private Context context;
    private String[] types;
    private OnChipClickedListener chipClickedListener;
    private ArrayList<Boolean> chipsSelected = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    }}

    public TypesChipsAdapter(String[] types, Context context, OnChipClickedListener chipClickedListener) {
        this.types = types;
        this.context = context;
        this.chipClickedListener = chipClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chips, parent, false);
        for (int i = 0; i < getItemCount(); i++) {
            chipsSelected.add(i, false);
            ;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Chip chip = holder.itemView.findViewById(R.id.item_chips);
        chip.setText(types[position]);

        if (chipsSelected.get(position)) {
            chip.setChipBackgroundColorResource(R.color.colorFilterClick);
        } else {
            chip.setChipBackgroundColorResource(R.color.colorGrey);
        }

        chip.setOnClickListener(v -> {

            if (chipsSelected.get(position)) {
                chipsSelected.set(position, false);
                chip.setChipBackgroundColorResource(R.color.colorGrey);
                chipClickedListener.onChipClicked(chip.getText().toString(), false);
            } else {
                chipsSelected.set(position, true);
                chip.setChipBackgroundColorResource(R.color.colorFilterClick);
                chipClickedListener.onChipClicked(chip.getText().toString(), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return types.length;
    }
}
