package com.example.movielibrary;

import android.widget.RelativeLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Movie;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {
    private MutableLiveData<ArrayList<BasicMovie>> mMovieList = new MutableLiveData<>();

    private MutableLiveData<Movie> mMovie = new MutableLiveData<>();

    public void setMovieList(ArrayList<BasicMovie> movieList) {
        mMovieList.setValue(movieList);
    }

    public MutableLiveData<ArrayList<BasicMovie>> getMovieList() {
        return mMovieList;
    }

    public void setMovie(Movie movie) {
        mMovie.setValue(movie);
    }

    public MutableLiveData<Movie> getMovie() {
        return mMovie;
    }
}
