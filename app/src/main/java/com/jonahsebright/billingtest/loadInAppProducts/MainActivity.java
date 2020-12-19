package com.jonahsebright.billingtest.loadInAppProducts;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jonahsebright.billingtest.R;

public class MainActivity extends AppCompatActivity {

    private LoadInAppProductsViewModel loadInAppProductsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        loadInAppProductsViewModel = new ViewModelProvider(this, factory).get(LoadInAppProductsViewModel.class);

        LoadInAppPurchases loadInAppPurchases = new LoadInAppPurchases(getApplicationContext());
        ProductsPresenter productsPresenter = new ProductsPresenter();
        productsPresenter.setLoadInAppProductsViewModel(loadInAppProductsViewModel);
        loadInAppPurchases.setInAppProductsQueriedListener(productsPresenter);
        loadInAppPurchases.startConnectionToGooglePlay();

        setupProductsTextView();
    }

    private void setupProductsTextView() {
        loadInAppProductsViewModel.getProducts().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView textView = findViewById(R.id.tvProducts);
                textView.setText(s);
            }
        });
    }
}