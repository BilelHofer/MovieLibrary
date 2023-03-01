package com.example.movielibrary;

import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private int mMovieIndex = -1;

    public void setMovieIndex(int index) {
        mMovieIndex = index;
    }

    public int getMovieIndex() {
        return mMovieIndex;
    }

}
