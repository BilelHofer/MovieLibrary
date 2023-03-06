package com.example.movielibrary;

import android.widget.RelativeLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielibrary.APIMovie.BasicMovie;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {
    private MutableLiveData<Integer> mItemSelected = new MutableLiveData<>();

    private MutableLiveData<ArrayList<BasicMovie>> mMovieList = new MutableLiveData<>();

    public void selectItem(Integer item) {
        mItemSelected.setValue(item);
    }

    public MutableLiveData<Integer> getSelected() {
        return mItemSelected;
    }

    public void setMovieList(ArrayList<BasicMovie> movieList) {
        mMovieList.setValue(movieList);
    }

    public MutableLiveData<ArrayList<BasicMovie>> getMovieList() {
        return mMovieList;
    }
}
