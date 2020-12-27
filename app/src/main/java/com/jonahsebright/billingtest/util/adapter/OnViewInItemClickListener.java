package com.jonahsebright.billingtest.util.adapter;

public interface OnViewInItemClickListener<V> {
    void onViewClicked(V view, int position);

    static <V> OnViewInItemClickListener<V> NONE(){
        return new OnViewInItemClickListener<V>() {
            @Override
            public void onViewClicked(V view, int position) {
                //do nothing
            }
        };
    }
}
