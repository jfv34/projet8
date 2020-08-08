package com.openclassrooms.realestatemanager.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Currency;
import com.openclassrooms.realestatemanager.ui.filter.FilterFragment;
import com.openclassrooms.realestatemanager.ui.map.MapFragment;
import com.openclassrooms.realestatemanager.ui.utils.SharedCurrencyViewModel;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.ui.main.MainFragment.PREFS_CURRENCY;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    ActionBar actionBar;

    private MainFragment mainFragment;
    SharedPreferences sharedPreferences;
    private SharedCurrencyViewModel sharedCurrencyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureToolBar();
        configureAndShowMainFragment();
    }

    private void configureAndShowMainFragment() {
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @OnClick(R.id.activity_main_filter_button)
    public void onFilterClicked() {
        Fragment filterFragment = FilterFragment.newInstance();
        Utils.addFragmentInDetailScreen(this, filterFragment);
    }

    @OnClick(R.id.fragment_main_map_button)
    public void onMapClicked() {
        Fragment mapFragment = MapFragment.newInstance();
        Utils.addFragmentInDetailScreen(this, mapFragment);
    }

    @OnClick(R.id.fragment_main_preferences_button)
    public void onPreferencesClicked() {
        AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(this);
        alertdialogBuilder.setTitle(R.string.currency);

        final CharSequence[] currencyList = {"Dollars", "Euros"};
        final Currency[] currencySelected = {Currency.DOLLARS};

        alertdialogBuilder.setSingleChoiceItems(currencyList, 0, (dialog, which) -> {
            if (which == 0) {
                currencySelected[0] = Currency.DOLLARS;
            } else {
                currencySelected[0] = Currency.EUROS;
            }
        })
                .setPositiveButton("ok", (dialog, id) -> {
                    String prefsCurrency = "";
                    if (currencySelected[0] == Currency.DOLLARS) {
                        sharedCurrencyViewModel.setCurrency(Currency.DOLLARS);
                        prefsCurrency = "dollars";
                    } else {
                        sharedCurrencyViewModel.setCurrency(Currency.EUROS);
                        prefsCurrency = "euros";
                    }
                    sharedPreferences.edit()
                            .putString(PREFS_CURRENCY, prefsCurrency)
                            .apply();
                    Fragment mainFragment = MainFragment.newInstance();
                    Utils.replaceFragmentInMainScreen(this, mainFragment);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        alertdialogBuilder.show();
    }

    private void configureToolBar() {

        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

    }
}