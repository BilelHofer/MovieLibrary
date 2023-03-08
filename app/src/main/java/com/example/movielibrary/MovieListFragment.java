package com.example.movielibrary;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import java.util.ArrayList;

import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.MovieAPIView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

public class MovieListFragment extends Fragment {
    private ArrayList<BasicMovie> localDataset = new ArrayList<>();
    private MovieListAdapter adapter;

    private DatabaseHelper dbHelper;
    private PageViewModel pageViewModel;
    private int actualPageLoaded = 1;
    private TextInputLayout textInputLayout;
    private boolean isLoading = false;
    private boolean isSearch = false;
    private LinearProgressIndicator progressIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.movie_list);

        textInputLayout = view.findViewById(R.id.movie_list_search_bar);
        progressIndicator = view.findViewById(R.id.movie_list_progress_bar);

        textInputLayout.setStartIconOnClickListener(v -> {
            //TODO: open menu burger
        });
        textInputLayout.setEndIconOnClickListener(v -> {
            if (isSearch) {
                resetSearch();
            } else {
                actualPageLoaded = 1;
                if (textInputLayout.getEditText().getText().toString().length() > 0) {
                    if (!isLoading) {
                        progressIndicator.setVisibility(View.VISIBLE);
                        MovieAPIView.searchMovie(actualPageLoaded, textInputLayout.getEditText().getText().toString(), pageViewModel);
                        isLoading = true;
                        isSearch = true;
                        adapter.clear();
                        //TODO: enlever la page info et mettre à la place un xml qui affiche aucun film sélectionné
                        adapter.updateSelectedAtNull();
                        textInputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_search_cancel));
                    }
                } else {
                    resetSearch();
                }
            }
        });

        // Création de la dataset pour le recycleView
        adapter = new MovieListAdapter(localDataset, pageViewModel, dbHelper);
        recyclerView.setAdapter(adapter);
        loadMovie();

        // Gère le chargement de la page suivante
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading) {
                    // Chargez la page suivante des données
                    loadMovie();
                    isLoading = true;
                }
            }
        });

        pageViewModel.getNeedUpdate().observe(requireActivity(), needUpdate -> {
            if (needUpdate) {
                adapter.notifyDataSetChanged();
                pageViewModel.setNeedUpdate(false);
            }
        });

        // Mise à jour de l'adapter
        pageViewModel.getMovieList().observe(requireActivity(), movies -> {
            // TODO: si film est vide afficher un fragments avec un message d'information

            if (movies != localDataset) {
                adapter.addAll(movies);
                localDataset = movies;

                // L'api ne prend pas plus haut que 500 pages
                if (actualPageLoaded < 500)
                    actualPageLoaded++;

                isLoading = false;
                progressIndicator.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    private void loadMovie() {
        MovieAPIView.getMoviePages(actualPageLoaded, pageViewModel);
    }

    private void resetSearch() {
        actualPageLoaded = 1;
        adapter.clear();
        progressIndicator.setVisibility(View.VISIBLE);
        loadMovie();
        isSearch = false;
        textInputLayout.getEditText().setText("");
        textInputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_search));
    }
}