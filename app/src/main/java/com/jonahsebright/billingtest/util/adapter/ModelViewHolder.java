package com.jonahsebright.billingtest.util.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ModelViewHolder<M> extends RecyclerView.ViewHolder{

    public ModelViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract <V> void bindData(M model, int position);
}
