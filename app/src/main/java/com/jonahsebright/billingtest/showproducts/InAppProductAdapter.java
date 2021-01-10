package com.jonahsebright.billingtest.showproducts;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;

import java.util.ArrayList;

public class InAppProductAdapter extends ProductAdapter<InAppProductModel> {


    public InAppProductAdapter(ArrayList<InAppProductModel> models) {
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

    static class InAppProductViewHolder extends ProductViewHolder<InAppProductModel> {
        private final TextView tvPrice;
        private final Button buy;

        public InAppProductViewHolder(@NonNull View itemView, OnViewInItemClickListener<Button> onViewInItemClickListener) {
            super(itemView, onViewInItemClickListener);
            tvPrice = itemView.findViewById(R.id.price);
            buy = itemView.findViewById(R.id.buy);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewInItemClickListener.onViewClicked(buy, getAdapterPosition());
                }
            });
        }

        @Override
        protected Button getButton() {
            return itemView.findViewById(R.id.buy);
        }

        @Override
        public void bindData(InAppProductModel model, int position) {
            tvName.setText(model.getName());
            tvDescription.setText(model.getDescription());
            tvPrice.setText(model.getPrice());
        }
    }
}
