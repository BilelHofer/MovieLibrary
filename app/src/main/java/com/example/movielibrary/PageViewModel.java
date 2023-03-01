package com.example.movielibrary;

import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    /**
     * Live Data Instance
     */
    private int mMovieIndex = -1;

    public void setMovieIndex(int index) {
        mMovieIndex = index;
    }

    public int getMovieIndex() {
        return mMovieIndex;
    }
}
