package com.example.movielibrary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Movie;
import com.example.movielibrary.APIMovie.MovieAPI;
import com.example.movielibrary.APIMovie.MovieAPIView;
import com.example.movielibrary.APIMovie.MovieResults;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListFragment extends Fragment {
    private ArrayList<BasicMovie> localDataset = new ArrayList<>();
    private MovieListAdapter adapter;
    private int actualPageLoaded = 1;
    private PageViewModel pageViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.movie_list);

        // Création de la dataset pour le recycleView
        adapter = new MovieListAdapter(localDataset, pageViewModel);
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
        MovieAPIView.getMoviePages(actualPageLoaded, pageViewModel);

        // Mise à jour de l'adapter
        pageViewModel.getMovieList().observe(requireActivity(), movies -> {
            localDataset.addAll(movies);
            adapter.updateData(localDataset);
            // L'api ne prend pas plus haut que 500 pages
            if (actualPageLoaded < 500)
                actualPageLoaded++;
        });

    }
}