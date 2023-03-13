package com.example.movielibrary.APIMovie;

public class GenresResult {
    private Genre[] genres;

    public GenresResult(Genre[] genres) {
        this.genres = genres;
    }

    public Genre[] getGenres() {
        return genres;
    }
}
