package com.openclassrooms.realestatemanager.ui.editProperty;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.OnPhotoDeleteClickedListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Photo;

import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter {

    private List<Photo> photos;
    private Context context;
    private ImageView photoIV;
    private OnPhotoDeleteClickedListener clickedListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public PhotoGridAdapter(Context context, List<Photo> photos, OnPhotoDeleteClickedListener clickedListener) {
        this.context = context;
        this.photos = photos;
        this.clickedListener = clickedListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Bitmap photoBM = Utils.loadImageFromStorage(photos.get(position).getPath(), photos.get(position).getFileNamePhoto());
        photoIV = holder.itemView.findViewById(R.id.item_photo_for_grid_photo_iv);
        photoIV.setImageBitmap(photoBM);
        ImageView delete_icon = holder.itemView.findViewById(R.id.item_photo_for_grid_delete_iv);
        delete_icon.setOnClickListener(v -> {
            clickedListener.onPhotoDeleteClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}