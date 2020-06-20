package com.openclassrooms.realestatemanager.ui.simulator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimulatorFragment extends Fragment {

    @BindView(R.id.fragment_simulator_price_textInputEditText)
    EditText price_et;
    @BindView(R.id.fragment_simulator_contribution_textInputEditText)
    EditText contribution_et;
    @BindView(R.id.fragment_simulator_rate_textInputEditText)
    EditText rate_et;
    @BindView(R.id.fragment_simulator_duration_textInputEditText)
    EditText duration_et;
    @BindView(R.id.fragment_simulator_duration_radiogroup)
    RadioGroup duration_radioGroup;
    @BindView(R.id.fragment_simulator_result)
    TextView result_tv;
    @BindView(R.id.fragment_simulator_result_text)
    TextView text_before_result_tv;
    @BindView(R.id.fragment_simulator_toolbar)
    Toolbar toolbar;

    public static SimulatorFragment newInstance(String bundlePrice) {
        SimulatorFragment simulatorFragment  = new SimulatorFragment();
        Bundle args = new Bundle();
        args.putString("price", bundlePrice);
        simulatorFragment.setArguments(args);
        return simulatorFragment;
    }

    private String bundlePrice;
    private SimulatorFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundlePrice = getArguments().getString("price", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_simulator, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolBar();
        viewModel = new ViewModelProvider(this).get(SimulatorFragmentViewModel.class);
        text_before_result_tv.setVisibility(View.INVISIBLE);
        price_et.setText(bundlePrice);
        viewModel.init(bundlePrice);

        observe_text_in_editText(price_et, "PRICE");
        observe_text_in_editText(contribution_et, "CONTRIBUTION");
        observe_text_in_editText(rate_et, "RATE");
        observe_text_in_editText(duration_et, "DURATION");
        observe_duration_radioGroup();

        viewModel.result.observe(getViewLifecycleOwner(), result -> {
            if (result.isEmpty()) {
                text_before_result_tv.setVisibility(View.INVISIBLE);
                result_tv.setText("");
            } else {
                text_before_result_tv.setVisibility(View.VISIBLE);
                result_tv.setText("$ " + result);
            }
        });
        viewModel.price.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.contribution.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.duration.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.rate_in_percentage.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.isDurationYears.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
    }

    private void observe_duration_radioGroup() {

        duration_radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            viewModel.radioGroupChanged(checkedId == R.id.fragment_simulator_duration_radiobutton_years);
        });
    }

    private void observe_text_in_editText(EditText editText, String form) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.textChanged(form, s.toString());
            }
        });
    }
    private void toolBar() {
        toolbar.setTitle("Real estate simulator");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v ->
                getActivity().onBackPressed());
    }
}