package com.openclassrooms.realestatemanager.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.OnPropertyClickedListener;
import com.openclassrooms.realestatemanager.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private List<String> items;
    private Context context;
    private OnPropertyClickedListener clickedListener;
    @BindView(R.id.item_type_tv) TextView type;

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public PropertyAdapter(List<String> testList, Context context, OnPropertyClickedListener clickedListener) {
        this.items = testList;
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
        type.setText(items.get(position));

       itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.onPropertyClicked(String.valueOf(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

