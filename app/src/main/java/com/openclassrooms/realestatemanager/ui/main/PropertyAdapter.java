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
import com.openclassrooms.realestatemanager.models.Property;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private List<Property> properties;
    private Context context;
    private OnPropertyClickedListener clickedListener;
    @BindView(R.id.item_type_tv) TextView type;

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public PropertyAdapter(List<Property> properties, Context context, OnPropertyClickedListener clickedListener) {
        this.properties = properties;
        this.context = context;
        this.clickedListener = clickedListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_property, parent, false);
        ButterKnife.bind(this,v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final View itemView = holder.itemView.findViewById(R.id.item_property);
        final TextView city_tv = itemView.findViewById(R.id.item_city_tv);
        final TextView type_tv = itemView.findViewById(R.id.item_type_tv);
        final TextView price_tv = itemView.findViewById(R.id.item_price_tv);

       city_tv.setText(properties.get(position).getCity());
        type_tv.setText(properties.get(position).getType());
        price_tv.setText("$ "+String.valueOf(properties.get(position).getPrice()));




       itemView.setOnClickListener(view -> clickedListener.onPropertyClicked(String.valueOf(position)));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}

