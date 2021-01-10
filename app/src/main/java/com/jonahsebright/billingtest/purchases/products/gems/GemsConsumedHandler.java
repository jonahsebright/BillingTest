package com.jonahsebright.billingtest.purchases.products.gems;

import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.purchases.buy.ConsumableEntitlementGrantedListener;
import com.jonahsebright.billingtest.util.storage.SharedPreferenceHelper;

public class GemsConsumedHandler extends ConsumableEntitlementGrantedListener {
    private final GemsChangedListener gemsChangedListener;
    private final SharedPreferenceHelper sharedPreferenceHelper;

    public static final String KEY_GEMS = "gems";

    public GemsConsumedHandler(String id, GemsChangedListener gemsChangedListener, SharedPreferenceHelper sharedPreferenceHelper) {
        super(id);
        this.gemsChangedListener = gemsChangedListener;
        this.sharedPreferenceHelper = sharedPreferenceHelper;
    }

    @Override
    public void onEntitlementGranted(Purchase purchase) {
        int currentGems = sharedPreferenceHelper.getInt(KEY_GEMS, 0);
        currentGems += 30;
        sharedPreferenceHelper.saveInt(currentGems, KEY_GEMS);
        gemsChangedListener.onGemsChanged(currentGems);
    }

}
