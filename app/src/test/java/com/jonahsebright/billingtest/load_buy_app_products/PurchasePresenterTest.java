package com.jonahsebright.billingtest.load_buy_app_products;

import android.content.Context;

import com.android.billingclient.api.BillingClient.BillingResponseCode;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.util.Converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PurchasePresenterTest {

    @Mock
    Context context = mock(Context.class);
    private PurchasePresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new PurchasePresenter(context);
    }

    @Test
    public void implementsPurchasesUpdatedListener() throws Exception {
        assertThat(presenter)
                .isInstanceOf(PurchasesUpdatedListener.class);
    }

    @Test
    public void generatesCorrectErrorMessageFromBillingResponseCode() throws Exception {
        when(context.getString(R.string.prompt_purchase_cancelled))
                .thenReturn("You cancelled the purchase");
        when(context.getString(R.string.prompt_purchase_failed))
                .thenReturn("Purchase failed");

        assertNull(presenter.generateErrorMessage(BillingResponseCode.OK, context));
        assertEquals(context.getString(R.string.prompt_purchase_cancelled),
                presenter.generateErrorMessage(BillingResponseCode.USER_CANCELED, context));
        assertEquals(context.getString(R.string.prompt_purchase_failed),
                presenter.generateErrorMessage(BillingResponseCode.SERVICE_DISCONNECTED, context));
        assertEquals(context.getString(R.string.prompt_purchase_failed),
                presenter.generateErrorMessage(BillingResponseCode.BILLING_UNAVAILABLE, context));
        assertEquals(context.getString(R.string.prompt_purchase_failed),
                presenter.generateErrorMessage(BillingResponseCode.ITEM_ALREADY_OWNED, context));
        assertEquals(context.getString(R.string.prompt_purchase_failed),
                presenter.generateErrorMessage(BillingResponseCode.ERROR, context));
    }

    @Test
    public void implementsConverter() throws Exception {
        assertThat(presenter)
                .isInstanceOf(Converter.class);
    }

    @Test
    public void generatesCorrectPurchaseModelsFromListOfPurchases() throws Exception {
        ArrayList<Purchase> purchases = new ArrayList<>();
        assertThat(presenter.convertItems(purchases)).isEmpty();
        Purchase purchase = new Purchase("", "");
        assertThat(presenter.convert(purchase))
                .usingRecursiveComparison()
                .isEqualTo(new PurchaseModel());
    }
}