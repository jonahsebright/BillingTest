package com.jonahsebright.billingtest.load_buy_app_products;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.util.Converter;

import java.util.List;

public class PurchasePresenter implements PurchasesUpdatedListener , Converter<Purchase, PurchaseModel> {
    private Context context;

    public PurchasePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        PurchaseFlowModel flowModel = new PurchaseFlowModel(
                generateErrorMessage(billingResult.getResponseCode(),context),
                purchases);
    }

    public String generateErrorMessage(int billingResponseCode, Context context) {
        if (billingResponseCode == BillingClient.BillingResponseCode.OK) return null;
        if(billingResponseCode == BillingClient.BillingResponseCode.USER_CANCELED)
            return context.getString(R.string.prompt_purchase_cancelled);
        return context.getString(R.string.prompt_purchase_failed);
    }

    @Override
    public PurchaseModel convert(Purchase purchase) {
        return null;
    }
}
