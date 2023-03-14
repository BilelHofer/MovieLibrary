package com.example.movielibrary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielibrary.APIMovie.Actor;
import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Genre;
import com.example.movielibrary.APIMovie.Movie;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {

    public enum ScreenSize {
        SMALL,
        LARGE
    }
    private MutableLiveData<ArrayList<BasicMovie>> mMovieList = new MutableLiveData<>();

    private MutableLiveData<Actor[]> mActorList = new MutableLiveData<Actor[]>();

    private MutableLiveData<Movie> mMovie = new MutableLiveData<>();
    private String mLanguage = "fr-FR";
    private ScreenSize mScreenSize = ScreenSize.SMALL;

    private MutableLiveData<Boolean> mNeedCloseKeyboard = new MutableLiveData<>();

    private MutableLiveData<Boolean> mNeedUpdate = new MutableLiveData<>();

    private MutableLiveData<Boolean> mMenuOpen = new MutableLiveData<>();

    private MutableLiveData<Genre[]> mGenreList = new MutableLiveData<>();

    private Boolean mIsOnlyLiked = false;

    private MutableLiveData<Boolean> mIsOnFilter = new MutableLiveData<>();

    private int mFilterGenreId = -1;
    private int mFilterYear1 = 1874;
    private int mFilterYear2 = 2023;

    private int mTotalPages = 0;
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

    public void setNeedCloseKeyboard(Boolean needCloseKeyboard) {
        mNeedCloseKeyboard.setValue(needCloseKeyboard);
    }

    public MutableLiveData<Boolean> getNeedCloseKeyboard() {
        return mNeedCloseKeyboard;
    }

    public void setMenuOpen(Boolean menuOpen) {
        mMenuOpen.setValue(menuOpen);
    }

    public MutableLiveData<Boolean> getMenuOpen() {
        return mMenuOpen;
    }

    public void setGenreList(Genre[] genreList) {
        mGenreList.setValue(genreList);
    }

    public MutableLiveData<Genre[]> getGenreList() {
        return mGenreList;
    }

    public void setIsOnlyLiked(Boolean isOnlyLiked) {
        mIsOnlyLiked = isOnlyLiked;
    }

    public Boolean getIsOnlyLiked() {
        return mIsOnlyLiked;
    }

    public void setIsOnFilter(Boolean isOnFilter) {
        mIsOnFilter.setValue(isOnFilter);
    }

    public MutableLiveData<Boolean> getIsOnFilter() {
        return mIsOnFilter;
    }

    public void setFilterGenreId(int filterGenreId) {
        mFilterGenreId = filterGenreId;
    }

    public int getFilterGenreId() {
        return mFilterGenreId;
    }

    public void setFilterYear1(int filterYear1) {
        mFilterYear1 = filterYear1;
    }

    public int getFilterYear1() {
        return mFilterYear1;
    }

    public void setFilterYear2(int filterYear2) {
        mFilterYear2 = filterYear2;
    }

    public int getFilterYear2() {
        return mFilterYear2;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public int getTotalPages() {
        return mTotalPages;
    }
}
