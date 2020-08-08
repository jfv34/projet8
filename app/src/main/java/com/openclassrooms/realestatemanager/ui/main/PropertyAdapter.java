package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private List<Property> properties;
    private Context context;
    private OnPropertyClickedListener clickedListener;
    private Currency currency;
    @BindView(R.id.main_item_type_tv) TextView type;
    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public PropertyAdapter(List<Property> properties, Currency currency,Context context, OnPropertyClickedListener clickedListener) {
        this.properties = properties;
        this.currency = currency;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bind(holder,properties.get(position));
    }

    private void bind(@NonNull ViewHolder holder, Property property) {
        final View itemView = holder.itemView.findViewById(R.id.item_property);
        final TextView cityTv = itemView.findViewById(R.id.main_item_address_tv);
        final TextView typeTv = itemView.findViewById(R.id.main_item_type_tv);
        final TextView priceTv = itemView.findViewById(R.id.main_item_price_tv);
        final ImageView photoIv = itemView.findViewById(R.id.main_item_photo_iv);

        cityTv.setText(property.getCity());
        typeTv.setText(property.getType());
        if (!property.getPrice().isEmpty()) {
            if(currency==Currency.DOLLARS){
            priceTv.setText(String.format("$ %s", property.getPrice()));}
            else{priceTv.setText(String.format("%s €", Utils.convertDollarToEuro(Integer.parseInt(property.getPrice()))));}
        }
        int radius = 20;
        int margin = 8;
        if (property.getPhotos() != null) {
            if (property.getPhotos().size() >0) {
                Photo photo = property.getPhotos().get(0);

                Glide.with(context)
                        .load(new File(photo.getFullPath()))
                            .transform(new CenterCrop(), new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                            .into(photoIv);

            }
        } else {
            Glide.with(context)
                    .load(R.drawable.default_photo)
                    .transform(new CenterCrop(), new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                    .into(photoIv);

        }
        itemView.setOnClickListener(view -> clickedListener.onPropertyClicked(property.getId()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position, List<Object> payloads) {
        final View itemView = holder.itemView.findViewById(R.id.item_property);
        final TextView cityTv = itemView.findViewById(R.id.main_item_address_tv);
        final TextView typeTv = itemView.findViewById(R.id.main_item_type_tv);
        final TextView priceTv = itemView.findViewById(R.id.main_item_price_tv);
        final ImageView photoIv = itemView.findViewById(R.id.main_item_photo_iv);

        if (payloads.isEmpty()) {
            bind(holder,properties.get(position));
        } else {
            Bundle o = (Bundle) payloads.get(0);

            for (String key : o.keySet()) {
                switch (key) {
                    case "price":
                        if (currency == Currency.DOLLARS) {
                            priceTv.setText(String.format("$ %s", o.getString(key)));
                        } else {
                            priceTv.setText(String.format("%s €", Utils.convertDollarToEuro(Integer.parseInt(o.getString(key)))));
                        }
                        break;
                    case "city":
                        cityTv.setText(o.getString(key));
                        break;
                    case "type":
                        typeTv.setText(o.getString(key));
                        break;
                    case "photoPath":
                        String path = o.getString(key);
                        int radius = 20;
                        int margin = 8;
                        Glide.with(context)
                                .load(new File(path))
                                .transform(new CenterCrop(), new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                                .into(photoIv);

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public void updateProperties(List<Property> newProperties) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(newProperties, properties));
        this.properties = newProperties;
        diffResult.dispatchUpdatesTo(this);
    }
}



