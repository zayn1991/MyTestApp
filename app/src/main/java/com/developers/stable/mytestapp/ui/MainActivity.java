package com.developers.stable.mytestapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.developers.stable.mytestapp.R;
import com.developers.stable.mytestapp.models.AnimalViewModel;
import com.developers.stable.mytestapp.utils.CommonUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmetContainer;
    private TabLayout tabLayout;

    private FragmentList fragmentCats;
    private FragmentList fragmentDogs;
    private FragmentManager fragmentManager;
    private AnimalViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(AnimalViewModel.class);
        fragmentManager = getSupportFragmentManager();

        initViews();
    }

    private void initViews() {
        fragmetContainer = findViewById(R.id.fragment_container);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cat));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dog));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (viewModel != null && !TextUtils.isEmpty(viewModel.getAnimalType())) {
            if (viewModel.getAnimalType().equals(getString(R.string.cat))) {
                selectTab(Objects.requireNonNull(tabLayout.getTabAt(0)));
                tabLayout.getTabAt(0).select();
            } else if (viewModel.getAnimalType().equals(getString(R.string.dog))) {
                selectTab(Objects.requireNonNull(tabLayout.getTabAt(1)));
                tabLayout.getTabAt(1).select();
            }
        } else {
            selectTab(Objects.requireNonNull(tabLayout.getTabAt(0)));
        }
    }

    private void selectTab(@NonNull TabLayout.Tab tab) {
        String tabTitle = tab.getText().toString();
        if (!TextUtils.isEmpty(tabTitle)) {
            chooseFragment(tabTitle);
        } else {
            CommonUtils.showToast(this, R.string.error_when_selectng_tabs);
        }
    }

    private void chooseFragment(String tabTitle) {
        if (tabTitle.equals(getString(R.string.cat))) {
            viewModel.setAnimalType(getString(R.string.cat));

            if (fragmentCats == null) {
                fragmentCats = new FragmentList();
            }
            fragmentCats.setViewModel(viewModel);
            setFragment(fragmentCats);

        } else if (tabTitle.equals(getString(R.string.dog))) {
            viewModel.setAnimalType(getString(R.string.dog));

            if (fragmentDogs == null) {
                fragmentDogs = new FragmentList();
            }
            fragmentDogs.setViewModel(viewModel);
            setFragment(fragmentDogs);

        }
    }

    private void setFragment(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(fragmetContainer.getId(), fragment, fragment.getTag())
                .commit();
    }
}
