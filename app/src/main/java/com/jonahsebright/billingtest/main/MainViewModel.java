package com.jonahsebright.billingtest.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Integer> gems = new MutableLiveData<>(0);

    public MutableLiveData<Integer> getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems.setValue(gems);
    }
}
