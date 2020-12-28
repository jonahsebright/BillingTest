package com.jonahsebright.billingtest.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.app_products.PurchaseEntitlementGrantedListenersFactory;
import com.jonahsebright.billingtest.load_buy_app_products.AppProductsViewModel;
import com.jonahsebright.billingtest.load_buy_app_products.AppPurchases;
import com.jonahsebright.billingtest.load_buy_app_products.InAppProductAdapter;
import com.jonahsebright.billingtest.load_buy_app_products.ProductModel;
import com.jonahsebright.billingtest.load_buy_app_products.ProductsPresenter;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;
import com.jonahsebright.billingtest.util.storage.SharedPreferenceHelper;

import java.util.ArrayList;

import static com.jonahsebright.billingtest.app_products.gems.GemsConsumedHandler.KEY_GEMS;

public class MainActivity extends AppCompatActivity {

    private AppProductsViewModel appProductsViewModel;
    private InAppProductAdapter productAdapter;
    private AppPurchases appPurchases;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModels();
        initStartValuesOfViewModels();
        //TODO PurchasePresenter purchasePresenter = new PurchasePresenter(getApplicationContext());
        GemsPresenter gemsPresenter = new GemsPresenter(mainViewModel, getApplicationContext());
        appPurchases = new AppPurchases(getApplicationContext(),
                PurchaseEntitlementGrantedListenersFactory.createAll(gemsPresenter, getApplicationContext()));
        ProductsPresenter productsPresenter = new ProductsPresenter();
        productsPresenter.setAppProductsViewModel(appProductsViewModel);
        appPurchases.setInAppProductsQueriedListener(productsPresenter);
        appPurchases.startConnectionToGooglePlay();

        setupProductsListView();
        setupGemsView();
    }

    private void initStartValuesOfViewModels() {
        int currentGems = new SharedPreferenceHelper(getApplicationContext()).getInt(KEY_GEMS, 0);
        mainViewModel.setGems(currentGems);
    }

    private void setupGemsView() {
        mainViewModel.getGems().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer gems) {
                TextView tvGems = findViewById(R.id.gems);
                tvGems.setText(gems + " gems");
            }
        });
    }

    private void initViewModels() {
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        appProductsViewModel = new ViewModelProvider(this, factory).get(AppProductsViewModel.class);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
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
        appProductsViewModel.getProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                productAdapter.setModelsAndUpdateView(productModels);
            }
        });
    }

    private void launchPurchaseFlow(int positionProduct) {
        appPurchases.launchBillingFlow(this, positionProduct);
    }
}