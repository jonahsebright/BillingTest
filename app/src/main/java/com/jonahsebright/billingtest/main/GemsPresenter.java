package com.jonahsebright.billingtest.main;

import android.content.Context;
import android.widget.Toast;

import com.jonahsebright.billingtest.app_products.gems.GemsChangedListener;

public class GemsPresenter implements GemsChangedListener {
    private MainViewModel mainViewModel;
    private Context context;

    public GemsPresenter(MainViewModel mainViewModel, Context context) {
        this.mainViewModel = mainViewModel;
        this.context = context;
    }

    @Override
    public void onGemsChanged(int gems) {
        mainViewModel.setGems(gems);
        Toast.makeText(context, "You bought 30 Gems!", Toast.LENGTH_SHORT).show();
    }
}
