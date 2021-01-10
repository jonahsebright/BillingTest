package com.jonahsebright.billingtest.viewimage;

import androidx.annotation.DrawableRes;

public class ImageFragmentModel {
    private @DrawableRes
    int imageRes;
    private String info;

    public ImageFragmentModel(int imageRes, String info) {
        this.imageRes = imageRes;
        this.info = info;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getInfo() {
        return info;
    }
}
