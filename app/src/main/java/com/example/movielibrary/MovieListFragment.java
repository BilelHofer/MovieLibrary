package com.example.movielibrary;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;
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
    private boolean needResetIcon = false;
    private LinearProgressIndicator progressIndicator;

    private RelativeLayout noMovieFoundLayout;

    private ObjectAnimator drawerAnimation;

    private Boolean onFilter = false;
    private Boolean onSearch = false;

    private View menu;

    private boolean isEndOfList = false;

    private LinearLayout mainLayout;

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

        mainLayout = view.findViewById(R.id.movie_list_layout);

        menu = view.findViewById(R.id.menu_drawer);

        // Met à jour la couleur de fond du menu
        menu.setBackground(getResources().getDrawable(R.drawable.app_background));

        menu.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                menu.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Ferme le menu
                drawerAnimation = ObjectAnimator.ofFloat(menu, "translationX", -menu.getHeight());
                drawerAnimation.setDuration(0);
                drawerAnimation.start();

                drawerAnimation.setDuration(800);
            }
        });

        textInputLayout = view.findViewById(R.id.movie_list_search_bar);
        progressIndicator = view.findViewById(R.id.movie_list_progress_bar);
        noMovieFoundLayout = view.findViewById(R.id.no_result_layout);

        pageViewModel.getMenuOpen().observe(getViewLifecycleOwner(), this::manageDrawer);

        // Menu Burger
        textInputLayout.setStartIconOnClickListener(v -> {
            pageViewModel.setMenuOpen(true);
        });
        // Recherche
        textInputLayout.setEndIconOnClickListener(v -> {
            noMovieFoundLayout.setVisibility(View.GONE);
            if (needResetIcon) {
                resetSearch();
            } else {
                actualPageLoaded = 1;
                isEndOfList = false;
                if (textInputLayout.getEditText().getText().toString().length() > 0) {
                    if (!isLoading) {
                        progressIndicator.setVisibility(View.VISIBLE);
                        MovieAPIView.searchMovie(actualPageLoaded, textInputLayout.getEditText().getText().toString(), pageViewModel);
                        onSearch = true;
                        onFilter = false;
                        pageViewModel.setIsOnlyLiked(false);
                        isLoading = true;
                        needResetIcon = true;
                        adapter.clear();
                        adapter.updateSelectedAtNull();
                        textInputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_search_cancel));
                    }
                } else {
                    resetSearch();
                }
            }
        });

        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_search));
                needResetIcon = false;
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

            // Si la liste de film est vide, on affiche un message d'erreur
            if (movies.size() == 0 && !isEndOfList) {
                noMovieFoundLayout.setVisibility(View.VISIBLE);
            } else {
                noMovieFoundLayout.setVisibility(View.GONE);
            }

            if (movies != localDataset) {

                ArrayList<BasicMovie> moviesToInsert = new ArrayList<>();
                 if (pageViewModel.getIsOnlyLiked()) {
                    // Ajoute les films qui sont dans la base de données
                    for (BasicMovie movie : movies) {
                        if (dbHelper.isMovieLiked(movie.getId())) {
                            moviesToInsert.add(movie);
                        }
                    }
                } else {
                    moviesToInsert = movies;
                }

                adapter.addAll(moviesToInsert);
                localDataset = moviesToInsert;

                if (actualPageLoaded <= pageViewModel.getTotalPages()) {
                    actualPageLoaded++;
                    isEndOfList = true;
                }

                isLoading = false;
                progressIndicator.setVisibility(View.INVISIBLE);
            }
        });

        pageViewModel.getIsOnFilter().observe(requireActivity(), isOnFilter -> {
            if (isOnFilter) {
                actualPageLoaded = 1;
                isEndOfList = false;
                adapter.clear();
                adapter.updateSelectedAtNull();
                pageViewModel.setIsOnFilter(false);
                onFilter = true;
                isLoading = true;
                onSearch = false;
                progressIndicator.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void loadMovie() {
        if (!onFilter && !onSearch) {
            MovieAPIView.getMoviePages(actualPageLoaded, pageViewModel);
        } else if (onSearch) {
            MovieAPIView.searchMovie(actualPageLoaded, textInputLayout.getEditText().getText().toString(), pageViewModel);
        } else {
            MovieAPIView.getMoviesWithFilter(actualPageLoaded,pageViewModel.getFilterGenreId(), pageViewModel.getFilterYear1(), pageViewModel.getFilterYear2(), pageViewModel);
        }
    }

    private void resetSearch() {
        actualPageLoaded = 1;
        onSearch = false;
        onFilter = false;
        isEndOfList = false;
        pageViewModel.setIsOnlyLiked(false);
        adapter.clear();
        adapter.updateSelectedAtNull();
        progressIndicator.setVisibility(View.VISIBLE);
        loadMovie();
        needResetIcon = false;
        textInputLayout.getEditText().setText("");
        textInputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_search));
    }

    private void manageDrawer(boolean needOpen) {
        float translationX =  needOpen ? 0 : -menu.getHeight();

        drawerAnimation = ObjectAnimator.ofFloat(menu, "translationX", translationX);
        drawerAnimation.start();

        drawerAnimation = ObjectAnimator.ofFloat(mainLayout, "translationX", translationX == 0 ? menu.getHeight() : 0);
        drawerAnimation.start();
    }
}