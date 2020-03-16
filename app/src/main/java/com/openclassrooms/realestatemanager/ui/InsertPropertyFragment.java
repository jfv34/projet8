package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.ui.main.MainFragmentViewModel;

public class InsertPropertyFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    View root;

    public static Fragment newInstance() {
        InsertPropertyFragment insertPropertyFragment = new InsertPropertyFragment();
        return insertPropertyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_insert_property, container, false);

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
                "House", "Flat", "Duplex", "Triplex", "Penthouse", "Loft"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, TYPE);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                root.findViewById(R.id.fragment_insert_property_TextField_type_dropdown);
        textView.setAdapter(adapter);
    }

    private void configure_test_insert_property_editText(View view) {

      /*  EditText editText = view.findViewById(R.id.insertProperty_test_et);

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

        });*/

    }

}

