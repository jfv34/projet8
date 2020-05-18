package com.openclassrooms.realestatemanager.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnChipClickedListener;
import com.openclassrooms.realestatemanager.models.Type;

import java.util.List;

public class TypesChipsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Type> types;
    private OnChipClickedListener chipClickedListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    }}

    public TypesChipsAdapter(List<Type> types, Context context, OnChipClickedListener chipClickedListener) {
        this.types = types;
        this.context = context;
        this.chipClickedListener = chipClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chips, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String type = types.get(position).getType();
        boolean isSelected = types.get(position).isSelected();
        Chip chip = holder.itemView.findViewById(R.id.item_chips);
        chip.setText(type);

        if (isSelected) {
            chip.setChipBackgroundColorResource(R.color.colorFilterClick);
        } else {
            chip.setChipBackgroundColorResource(R.color.colorGrey);
        }

        chip.setChecked(isSelected);

        chip.setOnClickListener(v -> {
            chipClickedListener.onChipClicked(position, !isSelected);
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
}
