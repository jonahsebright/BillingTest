package com.jonahsebright.billingtest.showproducts;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.util.adapter.ModelAdapter;
import com.jonahsebright.billingtest.util.adapter.ModelViewHolder;
import com.jonahsebright.billingtest.util.adapter.OnViewInItemClickListener;

import java.util.ArrayList;

public abstract class ProductAdapter<M extends ProductModel> extends ModelAdapter<M, ProductAdapter.ProductViewHolder<M>, Button> {

    public ProductAdapter(ArrayList<M> models) {
        super(models);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.product_item_card;
    }

    static abstract class ProductViewHolder<M extends ProductModel> extends ModelViewHolder<M> {
        protected final TextView tvName;
        protected final TextView tvDescription;

        public ProductViewHolder(@NonNull View itemView, OnViewInItemClickListener<Button> onViewInItemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.product_name);
            tvDescription = itemView.findViewById(R.id.product_description);

            getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewInItemClickListener.onViewClicked(getButton(), getAdapterPosition());
                }
            });
        }

        protected abstract Button getButton();
    }
}
