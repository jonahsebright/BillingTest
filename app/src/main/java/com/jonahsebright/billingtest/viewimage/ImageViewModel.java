package com.jonahsebright.billingtest.viewimage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImageViewModel extends ViewModel {
    private MutableLiveData<ImageFragmentModel> imageFragmentModel = new MutableLiveData<>();

    public void setImageFragmentModel(ImageFragmentModel imageFragmentModel) {
        this.imageFragmentModel.setValue(imageFragmentModel);
    }

    public MutableLiveData<ImageFragmentModel> getImageFragmentModel() {
        return imageFragmentModel;
    }
}
