package com.example.movielibrary.APIMovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResults {
    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<BasicMovie> movies;

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<BasicMovie> getMovies() {
        return movies;
    }
}
