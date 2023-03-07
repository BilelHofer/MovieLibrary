package com.example.movielibrary.APIMovie;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {
    @GET("movie/popular")
    Call<MovieResult> getAllMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
            );

    @GET("movie/{movie_id}/credits")
    Call<CreditsResult> getCredits(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );
}
