package com.openclassrooms.realestatemanager.ui.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPropertyAdapter extends RecyclerView.Adapter<SearchPropertyAdapter.ViewHolder> {

    private List<Property> properties;
    private Context context;
    private OnPropertyClickedListener clickedListener;
    @BindView(R.id.main_item_type_tv) TextView type;

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public SearchPropertyAdapter(List<Property> properties, Context context, OnPropertyClickedListener clickedListener) {
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

        Property property = properties.get(position);

        city_tv.setText(property.getCity());
        type_tv.setText(property.getType());
        price_tv.setText("$ " + String.valueOf(property.getPrice()));

        if (property.getPhotos() != null) {
            if (property.getPhotos().size() >0) {
                Photo photo = property.getPhotos().get(0);
        String filePhoto = photo.getPath();
        String namePhoto = photo.getFileNamePhoto();

            Bitmap photoBM = Utils.loadImageFromStorage(filePhoto,namePhoto);
                photo_iv.setImageBitmap(photoBM);
            }
        }

        itemView.setOnClickListener(view -> clickedListener.onPropertyClicked(property.getId()));

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}

