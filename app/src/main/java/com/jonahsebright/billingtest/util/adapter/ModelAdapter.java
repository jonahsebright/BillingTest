package com.jonahsebright.billingtest.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @param <M> type of model
 * @param <MVH> type of {@link ModelViewHolder<M>}
 * @param <V> type of View of {@link OnViewInItemClickListener<V>}
 */
public abstract class ModelAdapter<M, MVH extends ModelViewHolder<M>, V>
        extends RecyclerView.Adapter<MVH> {

    private ArrayList<M> models;
    protected OnItemClickListener onItemClickListener = OnItemClickListener.NONE();
    protected OnViewInItemClickListener<V> onViewInItemClickListener = OnViewInItemClickListener.NONE();

    public ModelAdapter(ArrayList<M> models) {
        this.models = models;
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void removeOnItemClickListener(){
        onItemClickListener = OnItemClickListener.NONE();
    }

    public void setOnViewInItemClickListener(OnViewInItemClickListener<V> onViewInItemClickListener) {
        this.onViewInItemClickListener = onViewInItemClickListener;
    }

    public void removeOnViewInItemClickListener(){
        onViewInItemClickListener = OnViewInItemClickListener.NONE();
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
