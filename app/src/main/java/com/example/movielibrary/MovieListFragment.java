package com.example.movielibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieListFragment extends Fragment {
    private String[] datasetTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.movie_list);

        // Données de test
        datasetTest = new String[100];
        for (int i = 0; i < datasetTest.length; i++) {
            datasetTest[i] = "Test " + i;
        }

        // Utilisation de ListMovieAdapter
        MovieListAdapter adapter = new MovieListAdapter(datasetTest);

        recyclerView.setAdapter(adapter);

        return view;
    }
}