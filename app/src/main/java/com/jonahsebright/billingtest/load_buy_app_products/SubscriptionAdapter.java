package com.jonahsebright.billingtest.load_buy_app_products;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;

import java.util.ArrayList;

public class SubscriptionAdapter extends ProductAdapter<SubscriptionProductModel> {

    public SubscriptionAdapter(ArrayList<SubscriptionProductModel> models) {
        super(models);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.subscription_item_card;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubscriptionViewHolder(getItemView(parent), onViewInItemClickListener);
    }

    static class SubscriptionViewHolder extends ProductViewHolder<SubscriptionProductModel> {
        private final TextView tvPricePerPeriod;

        public SubscriptionViewHolder(@NonNull View itemView, OnViewInItemClickListener<Button> onViewInItemClickListener) {
            super(itemView, onViewInItemClickListener);
            tvPricePerPeriod = itemView.findViewById(R.id.pricePerPeriod);
        }

        @Override
        protected Button getButton() {
            return itemView.findViewById(R.id.subscribe);
        }

        @Override
        public void bindData(SubscriptionProductModel model, int position) {
            tvName.setText(model.getName());
            tvDescription.setText(model.getDescription());
            tvPricePerPeriod.setText(String.format("%s / %s", model.getPrice(), model.getPeriod()));
        }
    }
}
