package com.openclassrooms.realestatemanager.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback {

    List<Property> oldProperties;
    List<Property> newProperties;

    public MyDiffCallback(List<Property> newProperties, List<Property> oldProperties) {
        this.newProperties = newProperties;
        this.oldProperties = oldProperties;
    }

    @Override
    public int getOldListSize() {
        return oldProperties.size();
    }

    @Override
    public int getNewListSize() {
        return newProperties.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean result = oldProperties.get(oldItemPosition).id == newProperties.get(newItemPosition).id;
        return result;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean result = ((Bundle) getChangePayload(oldItemPosition, newItemPosition)).isEmpty();
        return result;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        Property oldItem = oldProperties.get(oldItemPosition);
        Property newItem = newProperties.get(newItemPosition);

        Bundle diff = new Bundle();
        if (!newItem.getPrice().equals(oldItem.getPrice()))
            diff.putString("price", newItem.getPrice());
        if (!newItem.getCity().equals(oldItem.getCity()))
            diff.putString("city", newItem.getCity());
        if (!newItem.getType().equals(oldItem.getType()))
            diff.putString("type", newItem.getType());
        if (newItem.getPhotos() != null && !newItem.getPhotos().equals(oldItem.getPhotos()))
            diff.putSerializable("photos", newItem.getPhotos());

        return diff;
    }
}