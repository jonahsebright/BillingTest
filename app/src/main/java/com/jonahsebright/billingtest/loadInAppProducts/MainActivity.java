package com.jonahsebright.billingtest.loadInAppProducts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jonahsebright.billingtest.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LoadInAppProductsViewModel loadInAppProductsViewModel;
    private InAppProductAdapter productAdapter;

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

        setupProductsListView();
    }

    private void setupProductsListView() {
        RecyclerView productsListView = findViewById(R.id.products);
        productsListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        productAdapter = new InAppProductAdapter(new ArrayList<>());
        productsListView.setAdapter(productAdapter);
        loadInAppProductsViewModel.getProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                productAdapter.setModelsAndUpdateView(productModels);
            }
        });
    }
}