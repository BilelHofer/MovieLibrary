package com.example.movielibrary.APIMovie;

import java.util.List;

public class Movie {
    private Boolean adult;
    private List<Genre> genres;
    private String backdrop_path;
    private Integer id;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String release_date;
    private Integer runtime;
    private String title;
    private Boolean video;
    private String vote_average;
    private Integer vote_count;

    public Movie(Boolean adult, String backdrop_path, List<Genre> genre, Integer id, String overview, Double popularity, String poster_path, String release_date, Integer runtime, String title, Boolean video, String vote_average, Integer vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genres = genre;
        this.id = id;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.runtime = runtime;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getRuntime() {
        // Parse la durée 120 en 2h00
        int hours = runtime / 60;
        int minutes = runtime % 60;
        String time = hours + "h" + minutes;

        return time;
    }

    public List<Genre> getGenre() {
        return genres;
    }
}
