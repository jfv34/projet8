package com.openclassrooms.realestatemanager.simulator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public static SimulatorFragment newInstance(String bundlePrice) {
        SimulatorFragment simulatorFragment  = new SimulatorFragment();
        Bundle args = new Bundle();
        args.putString("price", bundlePrice);
        simulatorFragment.setArguments(args);
        return simulatorFragment;
    }

    private String bundlePrice;
    private View root;
    private SimulatorFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundlePrice = getArguments().getString("price", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_simulator, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SimulatorFragmentViewModel.class);
        price_et.setText(bundlePrice);

    }

    @OnClick(R.id.fragment_simulator_result_fab)
    public void onValidateClicked() {
        if (!price_et.getText().toString().equals("")
                && (!rate_et.getText().toString().equals(""))
                && !duration_et.getText().toString().equals("")) {
            boolean isDurationYears = false;
            if (duration_radioGroup.getCheckedRadioButtonId() == R.id.fragment_simulator_duration_radiobutton_years) {
                isDurationYears = true;
            }
            viewModel.validate(
                    price_et.getText().toString(),
                    contribution_et.getText().toString(),
                    rate_et.getText().toString(),
                    duration_et.getText().toString(),
                    isDurationYears);

            Fragment resultFragment = ResultFragment.newInstance(viewModel.getResult());
            Utils.addFragmentInDetailScreen(getActivity(), resultFragment);
        }

    }
}
