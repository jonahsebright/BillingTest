package com.jonahsebright.billingtest.viewimage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.purchases.buy.PurchaseEntitlementGrantedListener;
import com.jonahsebright.billingtest.purchases.products.AppProducts;

import java.util.ArrayList;

public class ImageFragment extends Fragment {

    private ImageViewModel imageViewModel;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        imageViewModel = new ViewModelProvider(getViewModelStore(), factory).get(ImageViewModel.class);
        ImagePresenter imagePresenter = new ImagePresenter(imageViewModel, getActivity());
        imageViewModel.setImageFragmentModel(imagePresenter.generateLockedModel(getActivity()));

        ArrayList<PurchaseEntitlementGrantedListener> listeners = new ArrayList<>();
        listeners.add(new PurchaseEntitlementGrantedListener(AppProducts.Product.BUY_IMAGE) {
            @Override
            public void onEntitlementGranted(Purchase purchase) {
                boolean isAcknowledged = purchase.isAcknowledged();
                System.out.println("isAcknowledged = " + isAcknowledged);
                if (isAcknowledged) imagePresenter.unLocked();
            }
        });
       /* TODO AppPurchases appPurchases = new AppPurchases(getActivity(), listeners);
        appPurchases.startConnectionToGooglePlay();*/

        bindViewsToData();
    }

    private void bindViewsToData() {
        imageViewModel.getImageFragmentModel().observe(getViewLifecycleOwner(), new Observer<ImageFragmentModel>() {
            @Override
            public void onChanged(ImageFragmentModel imageFragmentModel) {
                ImageView imageView = getView().findViewById(R.id.imageView);
                imageView.setImageResource(imageFragmentModel.getImageRes());

                TextView tvInfo = getView().findViewById(R.id.tvImageInfo);
                tvInfo.setText(imageFragmentModel.getInfo());
            }
        });
    }
}