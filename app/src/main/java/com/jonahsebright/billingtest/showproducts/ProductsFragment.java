package com.jonahsebright.billingtest.showproducts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.app.BillingTestApplication;
import com.jonahsebright.billingtest.main.MainViewModel;
import com.jonahsebright.billingtest.purchases.PurchasesManager;
import com.jonahsebright.billingtest.purchases.load.LoadProducts;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;
import com.jonahsebright.billingtest.util.storage.SharedPreferenceHelper;

import java.util.ArrayList;

import static com.jonahsebright.billingtest.purchases.products.gems.GemsConsumedHandler.KEY_GEMS;

public class ProductsFragment extends Fragment {

    private AppProductsViewModel appProductsViewModel;
    private InAppProductAdapter inAppProductAdapter;
    private SubscriptionAdapter subscriptionAdapter;
    private MainViewModel mainViewModel;

    public static ProductsFragment newInstance() {
        Bundle args = new Bundle();
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.products_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModels();
        initStartValuesOfViewModels();
        //TODO PurchasePresenter purchasePresenter = new PurchasePresenter(getActivity());
        setupAppPurchases();
        PurchasesManager purchasesManager = BillingTestApplication.getPurchasesManager(getActivity());
        setQueriedListenersToDisplayResults(purchasesManager.getLoadProducts());

        setupInAppProductsListView();
        setupSubscriptionsListView();
        setupGemsView();
    }

    private void setupAppPurchases() {
        /*
        TODO
        GemsPresenter gemsPresenter = new GemsPresenter(mainViewModel, getActivity());
        appPurchases = new AppPurchases(getActivity(),
                PurchaseEntitlementGrantedListenersFactory.createAll(gemsPresenter, getActivity()));
        setQueriedListenersToDisplayResults(purchasesManager.getLoadProducts());
        appPurchases.startConnectionToGooglePlay();

         */
    }

    private void setQueriedListenersToDisplayResults(LoadProducts loadProducts) {
        ProductsPresenter<InAppProductModel> inAppProductsPresenter = new InAppProductsPresenter();
        inAppProductsPresenter.setAppProductsViewModel(appProductsViewModel);
        loadProducts.setInAppProductsQueriedListener(inAppProductsPresenter);

        ProductsPresenter<SubscriptionProductModel> subsPresenter = new SubscriptionProductsPresenter();
        subsPresenter.setAppProductsViewModel(appProductsViewModel);
        loadProducts.setSubsQueriedListener(subsPresenter);
    }

    private void setupSubscriptionsListView() {
        RecyclerView subscriptionsListView = getView().findViewById(R.id.subscriptions);
        subscriptionsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        subscriptionAdapter = new SubscriptionAdapter(new ArrayList<>());
        subscriptionsListView.setAdapter(subscriptionAdapter);
        subscriptionAdapter.setOnViewInItemClickListener(new OnViewInItemClickListener<Button>() {
            @Override
            public void onViewClicked(Button buyProduct, int position) {
                launchSubsPurchaseFlow(position);
            }
        });
        appProductsViewModel.getSubscriptionModels().observe(getViewLifecycleOwner(), new Observer<ArrayList<SubscriptionProductModel>>() {
            @Override
            public void onChanged(ArrayList<SubscriptionProductModel> subscriptionProductModels) {
                subscriptionAdapter.setModelsAndUpdateView(subscriptionProductModels);
            }
        });
    }

    private void initStartValuesOfViewModels() {
        int currentGems = new SharedPreferenceHelper(getActivity()).getInt(KEY_GEMS, 0);
        mainViewModel.setGems(currentGems);
        appProductsViewModel.setInAppProducts(new ArrayList<>());
        appProductsViewModel.setSubscriptionModels(new ArrayList<>());
    }

    private void setupGemsView() {
        mainViewModel.getGems().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer gems) {
                TextView tvGems = getView().findViewById(R.id.gems);
                tvGems.setText(gems + " gems");
            }
        });
    }

    private void initViewModels() {
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        appProductsViewModel = new ViewModelProvider(this, factory).get(AppProductsViewModel.class);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    private void setupInAppProductsListView() {
        RecyclerView productsListView = getView().findViewById(R.id.productsInApp);
        productsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        inAppProductAdapter = new InAppProductAdapter(new ArrayList<>());
        productsListView.setAdapter(inAppProductAdapter);
        inAppProductAdapter.setOnViewInItemClickListener(new OnViewInItemClickListener<Button>() {
            @Override
            public void onViewClicked(Button buyProduct, int position) {
                launchInAppProductPurchaseFlow(position);
            }
        });
        appProductsViewModel.getInAppProducts().observe(getViewLifecycleOwner(), new Observer<ArrayList<InAppProductModel>>() {
            @Override
            public void onChanged(ArrayList<InAppProductModel> productModels) {
                inAppProductAdapter.setModelsAndUpdateView(productModels);
            }
        });
    }

    private void launchInAppProductPurchaseFlow(int positionProduct) {
        /*TODO
        SkuDetails skuDetails = appPurchases.getInAppSkuDetailsList().get(positionProduct);
        appPurchases.launchBillingFlow(getActivity(), skuDetails);*/
    }

    private void launchSubsPurchaseFlow(int positionSubs) {
        /* TODO
        SkuDetails skuDetails = appPurchases.getSubsSkuDetailsList().get(positionSubs);
        appPurchases.launchBillingFlow(getActivity(), skuDetails);*/
    }
}
