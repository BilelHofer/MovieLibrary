package com.example.movielibrary.APIMovie;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface qui représente les différentes requêtes à l'API
 */
public interface MovieAPI {
    // Récupère les films populaires
    @GET("movie/popular")
    Call<MovieResult> getAllMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("language") String language
    );

    // Récupère un film en particulier
    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    // Récupère les acteurs grâce à leur id
    @GET("person/{person_id}")
    Call<ActorDetail> getActor (
            @Path("person_id") int person_id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
    // Récupère les crédits d'un film
    @GET("movie/{movie_id}/credits")
    Call<CreditsResult> getCredits(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    // Récupère les films avec des filtre particulier
    @GET("discover/movie")
    Call<MovieResult> getMovieFiltred(
            @Query("api_key") String apiKey,
            @Query("with_genres") int with_genres,
            @Query("page") int page,
            @Query("primary_release_date.gte") String primary_release_date_gte,
            @Query("primary_release_date.lte") String primary_release_date_lte,
            @Query("language") String language
    );

    // Récupère les films avec des année seulement
    @GET("discover/movie")
    Call<MovieResult> getMovieFiltredYear(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("primary_release_date.gte") String primary_release_date_gte,
            @Query("primary_release_date.lte") String primary_release_date_lte,
            @Query("language") String language
    );

    // Récupère les films correspondant à la recherche
    @GET("search/movie")
    Call<MovieResult> searchMovie(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("query") String query,
            @Query("language") String language
    );

    // Récupère les genres de films
    @GET("genre/movie/list")
    Call<GenresResult> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
