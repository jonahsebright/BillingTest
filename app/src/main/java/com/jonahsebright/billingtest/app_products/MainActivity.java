package com.jonahsebright.billingtest.app_products;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.launchpurchaseflow.LaunchPurchaseFlow;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LoadInAppProductsViewModel loadInAppProductsViewModel;
    private InAppProductAdapter productAdapter;
    private AppPurchases appPurchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        loadInAppProductsViewModel = new ViewModelProvider(this, factory).get(LoadInAppProductsViewModel.class);

        appPurchases = new AppPurchases(getApplicationContext());
        ProductsPresenter productsPresenter = new ProductsPresenter();
        productsPresenter.setLoadInAppProductsViewModel(loadInAppProductsViewModel);
        appPurchases.setInAppProductsQueriedListener(productsPresenter);
        appPurchases.startConnectionToGooglePlay();

        setupProductsListView();
    }

    private void setupProductsListView() {
        RecyclerView productsListView = findViewById(R.id.products);
        productsListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        productAdapter = new InAppProductAdapter(new ArrayList<>());
        productsListView.setAdapter(productAdapter);
        productAdapter.setOnViewInItemClickListener(new OnViewInItemClickListener<Button>() {
            @Override
            public void onViewClicked(Button buyProduct, int position) {
                launchPurchaseFlow(position);
            }
        });
        loadInAppProductsViewModel.getProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                productAdapter.setModelsAndUpdateView(productModels);
            }
        });
    }

    private void launchPurchaseFlow(int positionProduct) {
        SkuDetails skuDetails = appPurchases.getSkuDetailsList().get(positionProduct);
        LaunchPurchaseFlow launchPurchaseFlow = new LaunchPurchaseFlow();
        launchPurchaseFlow.launchBillingFlow(appPurchases.getBillingClient(),this, skuDetails);
    }
}