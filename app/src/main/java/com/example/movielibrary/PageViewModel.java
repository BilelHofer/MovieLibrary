package com.example.movielibrary;

import android.widget.RelativeLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private MutableLiveData<Integer> mItemSelected = new MutableLiveData<>();

    public void selectItem(Integer item) {
        mItemSelected.setValue(item);
    }

    public MutableLiveData<Integer> getSelected() {
        return mItemSelected;
    }
}
