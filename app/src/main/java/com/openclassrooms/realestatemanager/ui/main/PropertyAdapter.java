package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private List<Property> properties;
    private Context context;
    private OnPropertyClickedListener clickedListener;
    @BindView(R.id.main_item_type_tv) TextView type;

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
        final TextView city_tv = itemView.findViewById(R.id.main_item_address_tv);
        final TextView type_tv = itemView.findViewById(R.id.main_item_type_tv);
        final TextView price_tv = itemView.findViewById(R.id.main_item_price_tv);
        final ImageView photo_iv = itemView.findViewById(R.id.main_item_photo_iv);

       city_tv.setText(properties.get(position).getCity());
        type_tv.setText(properties.get(position).getType());
        price_tv.setText("$ "+String.valueOf(properties.get(position).getPrice()));

        if (properties.get(position).getPhotos() != null) {
                Photo photoAndDescription = properties.get(position).getPhotos().get(0);
                Bitmap photoBMP = photoAndDescription.getPhotoBMP();
                Log.i("tag_photoBMP ", position + "   "+photoBMP.getHeight()+"<");
                photo_iv.setImageBitmap(photoBMP);

        }

        itemView.setOnClickListener(view -> clickedListener.onPropertyClicked(position));

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}

