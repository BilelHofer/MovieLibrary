package com.example.movielibrary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielibrary.APIMovie.Actor;
import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Movie;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {
    private MutableLiveData<ArrayList<BasicMovie>> mMovieList = new MutableLiveData<>();

    private MutableLiveData<Actor[]> mActorList = new MutableLiveData<Actor[]>();

    private MutableLiveData<Movie> mMovie = new MutableLiveData<>();

    public void setMovieList(ArrayList<BasicMovie> movieList) {
        mMovieList.setValue(movieList);
    }

    public MutableLiveData<ArrayList<BasicMovie>> getMovieList() {
        return mMovieList;
    }

    public void setActorList(Actor[] actorList) {
        mActorList.setValue(actorList);
    }

    public MutableLiveData<Actor[]> getActorList() {
        return mActorList;
    }

    public void setMovie(Movie movie) {
        mMovie.setValue(movie);
    }

    public MutableLiveData<Movie> getMovie() {
        return mMovie;
    }
}
