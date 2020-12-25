package com.jonahsebright.billingtest.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class ModelAdapter<M, MVH extends ModelViewHolder<M>>
        extends RecyclerView.Adapter<MVH> {

    private ArrayList<M> models;

    public ModelAdapter(ArrayList<M> models) {
        this.models = models;
    }

    public void setModelsAndUpdateView(ArrayList<M> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    protected View getItemView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(getCardLayoutResource(), parent, false);
    }

    protected abstract int getCardLayoutResource();

    @Override
    public void onBindViewHolder(@NonNull MVH holder, int position) {
        M model = models.get(position);
        holder.bindData(model, position);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }
}
