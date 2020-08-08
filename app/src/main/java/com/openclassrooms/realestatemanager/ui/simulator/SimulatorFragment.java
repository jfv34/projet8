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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.ui.utils.SharedCurrencyViewModel;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimulatorFragment extends Fragment {

    @BindView(R.id.fragment_simulator_price_textInputEditText)
    EditText priceEt;
    @BindView(R.id.fragment_simulator_price_textfield)
    TextInputLayout priceTextInputLayout;
    @BindView(R.id.fragment_simulator_contribution_textInputEditText)
    EditText contributionEt;
    @BindView(R.id.fragment_simulator_contribution_textfield)
    TextInputLayout contributionTextInputLayout;
    @BindView(R.id.fragment_simulator_rate_textInputEditText)
    EditText rateEt;
    @BindView(R.id.fragment_simulator_duration_textInputEditText)
    EditText durationEt;
    @BindView(R.id.fragment_simulator_duration_radiogroup)
    RadioGroup duration_radioGroup;
    @BindView(R.id.fragment_simulator_result)
    TextView resultTv;
    @BindView(R.id.fragment_simulator_result_text)
    TextView textBeforeResultTv;

    public static SimulatorFragment newInstance(String bundlePrice) {
        SimulatorFragment simulatorFragment  = new SimulatorFragment();
        Bundle args = new Bundle();
        args.putString("price", bundlePrice);
        simulatorFragment.setArguments(args);
        return simulatorFragment;
    }

    private String bundlePrice;
    private SimulatorFragmentViewModel viewModel;
    private SharedCurrencyViewModel sharedCurrencyViewModel;

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
        viewModel = new ViewModelProvider(this).get(SimulatorFragmentViewModel.class);
        sharedCurrencyViewModel = new ViewModelProvider(requireActivity()).get(SharedCurrencyViewModel.class);
        textBeforeResultTv.setVisibility(View.INVISIBLE);

        viewModel.init(bundlePrice);

        observeTextInEditText(priceEt, "PRICE");
        observeTextInEditText(contributionEt, "CONTRIBUTION");
        observeTextInEditText(rateEt, "RATE");
        observeTextInEditText(durationEt, "DURATION");
        observeDurationRadioGroup();

        viewModel.result.observe(getViewLifecycleOwner(), result -> {
            if (result.isEmpty()) {
                textBeforeResultTv.setVisibility(View.INVISIBLE);
                resultTv.setText("");
            } else {
                textBeforeResultTv.setVisibility(View.VISIBLE);
                if (sharedCurrencyViewModel.currency.getValue() == Currency.EUROS) {
                    resultTv.setText(String.format("%s €", result));
                } else {
                    resultTv.setText(String.format("$ %s", result));
                }
            }
        });
        sharedCurrencyViewModel.currency.observe(getViewLifecycleOwner(), currency ->
        {
            viewModel.setCurrency(currency);
            viewModel.calculation();
            if (currency == Currency.EUROS) {
                int price = Integer.parseInt(bundlePrice);
                int euros_price = Utils.convertDollarToEuro(price);
                priceEt.setText(String.valueOf(euros_price));
                priceTextInputLayout.setStartIconDrawable(R.drawable.ic_euro_24px);
                contributionTextInputLayout.setStartIconDrawable(R.drawable.ic_euro_24px);
            } else {
                priceEt.setText(bundlePrice);
                priceTextInputLayout.setStartIconDrawable(R.drawable.ic_money_24px);
                contributionTextInputLayout.setStartIconDrawable(R.drawable.ic_money_24px);
            }
        });
        viewModel.price.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.contribution.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.duration.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.rateInPercentage.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
        viewModel.isDurationYears.observe(getViewLifecycleOwner(), price -> viewModel.calculation());
    }

    private void observeDurationRadioGroup() {

        duration_radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            viewModel.radioGroupChanged(checkedId == R.id.fragment_simulator_duration_radiobutton_years);
        });
    }

    private void observeTextInEditText(EditText editText, String form) {

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
}