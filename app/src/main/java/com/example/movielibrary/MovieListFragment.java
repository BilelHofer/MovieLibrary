package com.example.movielibrary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Movie;
import com.example.movielibrary.APIMovie.MovieAPI;
import com.example.movielibrary.APIMovie.MovieResults;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListFragment extends Fragment {
    private ArrayList<BasicMovie> datasetTest = new ArrayList<>();
    private MovieListAdapter adapter;
    private int actualPageLoaded = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.movie_list);

        // Création de la dataset pour le recycleView
        adapter = new MovieListAdapter(datasetTest);
        recyclerView.setAdapter(adapter);
        loadMovie();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Vérifiez si la dernière ligne visible est la dernière ligne de la liste
                int lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                if (lastVisiblePosition == totalItemCount - 1) {
                    // Chargez la page suivante des données
                    loadMovie();
                }
            }
        });


        return view;
    }

    private void loadMovie() {
        //TODO faire une class apart pour gérer l'api

        // Données de test récupérer depuis l'API
        // Créez une instance de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Créez une instance de l'interface MovieApi
        MovieAPI movieApi = retrofit.create(MovieAPI.class);

        String apiKey = "d8ca27475e9a87d4db474ea21042e5af";

        // Appel de la méthode getPopularMovies
        Call<MovieResults> call = movieApi.getPopularMovies(apiKey, actualPageLoaded);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                MovieResults results = response.body();
                List<Movie> movies = results.getMovies();

                // Pour chaque movie on ajoute un BasicMovie dans le dataset
                for (Movie movie : movies) {
                    datasetTest.add(new BasicMovie(movie.getId(), movie.getTitle(), movie.getVote_average()));
                }

                // Mise à jour de l'adapter
                adapter.updateData(datasetTest);
                if (actualPageLoaded < results.getTotalPages())
                    actualPageLoaded++;
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}