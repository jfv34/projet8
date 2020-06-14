package com.openclassrooms.realestatemanager.simulator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultFragment extends Fragment {
    public static ResultFragment newInstance(String bundleResult) {
        ResultFragment resultFragment = new ResultFragment();

        Bundle args = new Bundle();
        args.putString("result", bundleResult);
        resultFragment.setArguments(args);

        return resultFragment;
    }

    private String bundleResult;

    @BindView(R.id.fragment_simulator_result_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_simulator_result_amount_tv)
    TextView result_TV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleResult = getArguments().getString("result", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result_simulator, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolBar();
        result_TV.setText("$ " + String.valueOf(bundleResult));
    }

    private void toolBar() {
        toolbar.setTitle(R.string.result_of_the_simulation);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }
}