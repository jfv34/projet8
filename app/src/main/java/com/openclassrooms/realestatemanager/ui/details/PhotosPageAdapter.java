package com.openclassrooms.realestatemanager.ui.details;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PhotosPageAdapter extends PagerAdapter {

    private Context context;

    public PhotosPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container,false);
        ImageView imageView = view.findViewById(R.id.detail_item_photo_iv);
        imageView.setImageDrawable(context.getResources().getDrawable(getImageAt(position)));
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }


    private int getImageAt(int position) {

        return R.drawable.picture_flat_exemple;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
}