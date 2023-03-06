package com.example.movielibrary.APIMovie;

import android.util.Log;

import com.example.movielibrary.PageViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieAPIView {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "d8ca27475e9a87d4db474ea21042e5af";

    public static void getMoviePages(int page, PageViewModel pageViewModel) {
        ArrayList<BasicMovie> dataset = new ArrayList<>();

        // Créez une instance de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Créez une instance de l'interface MovieApi
        MovieAPI movieApi = retrofit.create(MovieAPI.class);

        // Appel de la méthode pour récupérer les films
        Call<MovieResults> call = movieApi.getAllMovies(API_KEY, page);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                if (response.isSuccessful()) {

                    MovieResults results = response.body();
                    List<Movie> movies = results.getMovies();

                    // Pour chaque movie on ajoute un BasicMovie dans le dataset
                    for (Movie movie : movies) {
                        dataset.add(new BasicMovie(movie.getId(), movie.getTitle(), movie.getVote_average()));
                    }

                    // On ajoute le dataset à la pageViewModel
                    pageViewModel.setMovieList(dataset);
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}
