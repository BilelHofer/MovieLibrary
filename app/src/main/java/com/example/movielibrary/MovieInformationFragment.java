package com.example.movielibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieInformationFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_movie_information, container, false);

        pageViewModel.getMovie().observe(requireActivity(), movie -> {
           // Met les informations du film dans les composants
            TextView title  = view.findViewById(R.id.info_movie_title);
            ImageView poster = view.findViewById(R.id.info_movie_poster);
            TextView overview = view.findViewById(R.id.info_movie_overview);

            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());

            String posterPath = movie.getPosterPath();

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterPath)
                    .into(poster);
        });

        return view;
    }
}