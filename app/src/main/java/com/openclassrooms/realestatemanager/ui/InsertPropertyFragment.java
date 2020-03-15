package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.ui.main.MainFragment;
import com.openclassrooms.realestatemanager.ui.main.MainFragmentViewModel;
import com.openclassrooms.realestatemanager.ui.main.PropertyAdapter;

public class InsertPropertyFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    public static Fragment newInstance() {
        InsertPropertyFragment insertPropertyFragment = new InsertPropertyFragment();
        return insertPropertyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert_property, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PropertyDataBase.getInstance(getContext());

        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

       configure_test_insert_property_editText(view);

    }



    private void configure_test_insert_property_editText(View view) {

        EditText editText = view.findViewById(R.id.insertProperty_test_et);

        Button button = view.findViewById(R.id.insertProperty_test_adding_button);
        button.setOnClickListener(v -> {

            String testAddress = editText.getText().toString();
            Property property = new Property(
                    "lkhllgjhkg",
                    353353,
                    testAddress,
                    1234,
                    4,
                    "kjhjkhkh",
                    "kjhkjhkjhkjhkh",
                    true,
                    "12/12/12",
                    "14/11/14",
                    "ljlkjlkjlkj",
                    null);


            viewModel.setProperty(property);

        });

    }

}

