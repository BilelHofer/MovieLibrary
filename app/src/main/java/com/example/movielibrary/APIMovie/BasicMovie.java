package com.example.movielibrary.APIMovie;

import com.google.gson.annotations.SerializedName;

/**
 * Class qui représente les informations cruciales d'un film
 */
public class BasicMovie {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("vote_average")
    private String average;

    public BasicMovie(int id, String title, String average) {
        this.id = id;
        this.title = title;
        this.average = average;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAverage() {
        return average;
    }
}
