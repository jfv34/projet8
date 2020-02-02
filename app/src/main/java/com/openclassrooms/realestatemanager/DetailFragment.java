package com.openclassrooms.realestatemanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private TextView textViewMain;
    private TextView textViewQuantity;
    private TextView textView2;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        textViewMain = rootView.findViewById(R.id.fragment_detail_mainText_tv);
        textViewQuantity = rootView.findViewById(R.id.fragment_detail_quantity_tv);
        textView2 =rootView.findViewById(R.id.fragment_detail_textView2_tv);


        configureTextViewMain();
        configureTextViewQuantity();

        return rootView;
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
        this.textViewQuantity.setText(String.valueOf(quantity));
    }

    public void updateTextView(int tag) {
        switch (tag) {
            case 1:
                this.textView2.setText("button 1 clicked");
                break;
            case 2:
                this.textView2.setText("button 2 clicked");
                break;

            default:
                break;
        }
    }
}
