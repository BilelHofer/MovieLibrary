package com.example.movielibrary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielibrary.APIMovie.Actor;
import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Movie;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {

    public enum ScreenSize {
        SMALL,
        MEDIUM,
        LARGE
    }
    private MutableLiveData<ArrayList<BasicMovie>> mMovieList = new MutableLiveData<>();

    private MutableLiveData<Actor[]> mActorList = new MutableLiveData<Actor[]>();

    private MutableLiveData<Movie> mMovie = new MutableLiveData<>();
    private String mLanguage = "fr-FR";
    private ScreenSize mScreenSize = ScreenSize.SMALL;

    private MutableLiveData<Boolean> mNeedUpdate = new MutableLiveData<>();
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

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setScreenSize(ScreenSize screenSzie) {
        mScreenSize = screenSzie;
    }

    public ScreenSize getScreenSize() {
        return mScreenSize;
    }

    public void setNeedUpdate(Boolean needUpdate) {
        mNeedUpdate.setValue(needUpdate);
    }

    public MutableLiveData<Boolean> getNeedUpdate() {
        return mNeedUpdate;
    }
}
