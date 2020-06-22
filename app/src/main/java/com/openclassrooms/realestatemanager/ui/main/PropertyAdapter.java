package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final View itemView = holder.itemView.findViewById(R.id.item_property);
        final TextView city_tv = itemView.findViewById(R.id.main_item_address_tv);
        final TextView type_tv = itemView.findViewById(R.id.main_item_type_tv);
        final TextView price_tv = itemView.findViewById(R.id.main_item_price_tv);
        final ImageView photo_iv = itemView.findViewById(R.id.main_item_photo_iv);

        Property property = properties.get(position);

        city_tv.setText(property.getCity());
        type_tv.setText(property.getType());
        price_tv.setText(String.format("$ %s", property.getPrice()));
        int radius = 20;
        int margin = 8;
        if (property.getPhotos() != null) {
            if (property.getPhotos().size() >0) {
                Photo photo = property.getPhotos().get(0);
                String filePhoto = photo.getPath();
                String namePhoto = photo.getFileNamePhoto();
                Bitmap photoBM = Utils.loadImageFromStorage(filePhoto,namePhoto);
                if (photoBM != null) {
                    Glide.with(context)
                            .load(photoBM)
                            .transform(new CenterCrop(), new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                            .into(photo_iv);
                }
            }
        } else {
            Glide.with(context)
                    .load(R.drawable.default_photo)
                    .transform(new CenterCrop(), new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                    .into(photo_iv);

        }
        itemView.setOnClickListener(view -> clickedListener.onPropertyClicked(property.getId()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position, List<Object> payloads) {

        String p = null;
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals("price")) {
                    p = o.getString(key);

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



