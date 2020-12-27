package com.jonahsebright.billingtest.util.adapter;

public interface OnItemClickListener {
    void onItemClicked(int pos);

    static OnItemClickListener NONE(){
        return new OnItemClickListener() {
            @Override
            public void onItemClicked(int pos) {
                //Do nothing
            }
        };
    }
}
