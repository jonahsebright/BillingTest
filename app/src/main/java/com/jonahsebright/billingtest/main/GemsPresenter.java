package com.jonahsebright.billingtest.main;

import com.jonahsebright.billingtest.app_products.gems.GemsChangedListener;

public class GemsPresenter implements GemsChangedListener {
    private MainViewModel mainViewModel;

    public GemsPresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void onGemsChanged(int gems) {
        mainViewModel.setGems(gems);
    }
}
