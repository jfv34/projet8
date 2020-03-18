package com.openclassrooms.realestatemanager.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.main.MainFragment;
import com.openclassrooms.realestatemanager.ui.main.MainFragmentViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsertPropertyFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private View root;
    @BindView(R.id.fragment_insert_property_TextField_type)
    TextInputLayout newProperty_type;
    @BindView(R.id.fragment_insert_property_TextField_price)
    TextInputLayout newProperty_price;
    @BindView(R.id.fragment_insert_property_TextField_address)
    TextInputLayout newProperty_address;
    @BindView(R.id.fragment_insert_property_TextField_city)
    TextInputLayout newProperty_city;
    @BindView(R.id.fragment_insert_property_TextField_state)
    TextInputLayout newProperty_state;
    @BindView(R.id.fragment_insert_property_TextField_zip)
    TextInputLayout newProperty_zip;
    @BindView(R.id.fragment_insert_property_TextField_area)
    TextInputLayout newProperty_area;
    @BindView(R.id.fragment_insert_property_TextField_pieces)
    TextInputLayout newProperty_pieces;
    @BindView(R.id.fragment_insert_property_TextField_interestPoints)
    TextInputLayout newProperty_interestPoints;
    @BindView(R.id.fragment_insert_property_TextField_description)
    TextInputLayout newProperty_description;

    public static Fragment newInstance() {
        return new InsertPropertyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_insert_property, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        configure_autoCompleteTextView();

    }
    private void configure_autoCompleteTextView() {
        final String[] TYPE = new String[]{
                "Duplex", "Loft", "Penthouse", "Manor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                root.findViewById(R.id.fragment_insert_property_TextField_type_dropdown);
        textView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_insert_property_button)
    public void insert_property() {

        viewModel.setProperty(newProperty());
        Utils.toast(getActivity(),"Property added");
        notification_property_added();
        displayMainFragment();
    }

    private void notification_property_added() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_home_24px)
                .setContentTitle("Property added")
                .setContentText("The new property has been added")
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private Property newProperty() {

        String type = newProperty_type.getEditText().getText().toString();
        String price = newProperty_price.getEditText().getText().toString();
        String address = newProperty_address.getEditText().getText().toString();
        String city = newProperty_city.getEditText().getText().toString();
        String state = newProperty_state.getEditText().getText().toString();
        String zip = newProperty_zip.getEditText().getText().toString();
        String area = newProperty_area.getEditText().getText().toString();
        String pieces = newProperty_pieces.getEditText().getText().toString();
        String interestPoints = newProperty_interestPoints.getEditText().getText().toString();
        String description = newProperty_description.getEditText().getText().toString();

        Property property = new Property(
                type,
                price,
                address,
                city,
                state,
                zip,
                area,
                pieces,
                interestPoints,
                description,
                "",
                false,
                "",
                "",
                ""
        );
        return property;
    }

    private void displayMainFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment mainFragment = MainFragment.newInstance();
        transaction.replace(R.id.frame_layout_main, mainFragment).commit();
    }
}