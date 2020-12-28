package com.jonahsebright.billingtest.load_buy_app_products;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.util.adapter.ModelAdapter;
import com.jonahsebright.billingtest.util.adapter.ModelViewHolder;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;

import java.util.ArrayList;

public class InAppProductAdapter extends ModelAdapter<ProductModel, InAppProductAdapter.InAppProductViewHolder, Button> {

    public InAppProductAdapter(ArrayList<ProductModel> models) {
        super(models);
    }

    @NonNull
    @Override
    public InAppProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InAppProductViewHolder(getItemView(parent), onViewInItemClickListener);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.product_item_card;
    }

    static class InAppProductViewHolder extends ModelViewHolder<ProductModel> {
        private final TextView tvName;
        private final TextView tvPrice;
        private final TextView tvDescription;
        private final Button buy;

        public InAppProductViewHolder(@NonNull View itemView, OnViewInItemClickListener<Button> onViewInItemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.product_name);
            tvPrice = itemView.findViewById(R.id.price);
            tvDescription = itemView.findViewById(R.id.product_description);
            buy = itemView.findViewById(R.id.buyProduct);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewInItemClickListener.onViewClicked(buy, getAdapterPosition());
                }
            });
        }

        @Override
        public <V> void bindData(ProductModel model, int position) {
            tvName.setText(model.getName());
            tvPrice.setText(model.getPrice());
            tvDescription.setText(model.getDescription());
        }
    }
}
