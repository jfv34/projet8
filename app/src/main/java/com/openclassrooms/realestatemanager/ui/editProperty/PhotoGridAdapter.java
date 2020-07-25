package com.openclassrooms.realestatemanager.ui.editProperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPhotoDeleteClickedListener;
import com.openclassrooms.realestatemanager.clickedListener_interfaces.OnPhotoDescriptionClickedListener;
import com.openclassrooms.realestatemanager.models.Photo;

import java.io.File;
import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter {

    private List<Photo> photos;
    private Context context;
    private ImageView photoIV;
    private OnPhotoDeleteClickedListener deleteClickedListener;
    private OnPhotoDescriptionClickedListener descriptionClickedListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public PhotoGridAdapter(Context context, List<Photo> photos, OnPhotoDeleteClickedListener deleteClickedListener,
                            OnPhotoDescriptionClickedListener descriptionClickedListener) {
        this.context = context;
        this.photos = photos;
        this.deleteClickedListener = deleteClickedListener;
        this.descriptionClickedListener = descriptionClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Photo photo = photos.get(position);
        photoIV = holder.itemView.findViewById(R.id.item_photo_for_grid_photo_iv);

        Glide.with(context)
                .load(new File(photo.getFullPath()))
                .into(photoIV);

        ImageView delete_icon = holder.itemView.findViewById(R.id.item_photo_for_grid_delete_iv);
        delete_icon.setOnClickListener(v -> {
            deleteClickedListener.onPhotoDeleteClicked(position);
        });
        ImageView description_icon = holder.itemView.findViewById(R.id.item_photo_for_grid_descriptionIcon_iv);
        description_icon.setOnClickListener(v -> {
            descriptionClickedListener.onPhotoDescriptionClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}