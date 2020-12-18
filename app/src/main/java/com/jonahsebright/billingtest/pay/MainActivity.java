package com.jonahsebright.billingtest.pay;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.jonahsebright.billingtest.R;

public class MainActivity extends AppCompatActivity {

    private PayViewModel payViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        payViewModel = new ViewModelProvider(this, factory).get(PayViewModel.class);

        Pay pay = new Pay(getApplicationContext());
        ProductsPresenter productsPresenter = new ProductsPresenter();
        productsPresenter.setPayViewModel(payViewModel);
        pay.setInAppProductsQueriedListener(productsPresenter);

        setupProductsTextView();
    }

    private void setupProductsTextView() {
        payViewModel.getProducts().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView textView = findViewById(R.id.tvProducts);
                textView.setText(s);
            }
        });
    }
}