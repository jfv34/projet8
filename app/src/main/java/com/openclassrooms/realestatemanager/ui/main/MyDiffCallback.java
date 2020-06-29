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
        return oldProperties.get(oldItemPosition).id == newProperties.get(newItemPosition).id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean result = oldProperties.get(oldItemPosition).equals(newProperties.get(newItemPosition));
        return result;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        Property oldItem = oldProperties.get(oldItemPosition);
        Property newItem = newProperties.get(newItemPosition);

        Bundle diff = new Bundle();
        if (!newItem.getPrice().equals(oldItem.getPrice())) {
            diff.putString("price", newItem.getPrice());
        }

        return diff;
    }
}
