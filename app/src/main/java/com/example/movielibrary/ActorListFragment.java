package com.example.movielibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movielibrary.APIMovie.Actor;
import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Movie;
import com.example.movielibrary.APIMovie.MovieAPIView;

import java.util.ArrayList;

public class ActorListFragment extends Fragment {
    private ArrayList<Actor> localDataset = new ArrayList<>();
    private ActorListAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_actor_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.actor_list);

        // Création de la dataset pour le recycleView
        adapter = new ActorListAdapter(localDataset);
        recyclerView.setAdapter(adapter);
        loadActors();

        return view;
    }

    private void loadActors() {
        pageViewModel.getMovie().observe(requireActivity(), movie -> {
            MovieAPIView.getMovieCredits(movie.getId(), pageViewModel);
        });

        // Mise à jour de l'adapter
        pageViewModel.getActorList().observe(requireActivity(), actors -> {
            // parse la liste de tous les acteurs à 10 acteurs maximum
            ArrayList<Actor> LimitedActors = new ArrayList<>();

            int index = 0;
            for (Actor actor : actors) {
                LimitedActors.add(actor);

                // limite à 10 acteurs
                index++;
                if (index >= 10) {
                    break;
                }
            }

            adapter.updateActor(LimitedActors);
            localDataset = LimitedActors;
        });
    }
}