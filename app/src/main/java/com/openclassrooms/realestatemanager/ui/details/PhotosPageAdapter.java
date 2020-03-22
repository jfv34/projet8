package com.openclassrooms.realestatemanager.ui.details;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Photo;

import java.util.ArrayList;

public class PhotosPageAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Photo> photos;

    public PhotosPageAdapter(Context context, ArrayList<Photo> photos) {

        this.context = context;
        this.photos = photos;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container,false);
        ImageView imageView = view.findViewById(R.id.detail_item_photo_iv);
        Bitmap image = getImage(position);
        imageView.setImageBitmap(image);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }


    private Bitmap getImage(int position) {
        Photo photo = photos.get(position);
        Bitmap photoBM = Utils.loadImageFromStorage(photo.getPath(),photo.getFileNamePhoto());

        return photoBM;

    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
}