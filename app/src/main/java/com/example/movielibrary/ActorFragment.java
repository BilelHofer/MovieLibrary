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

public class ActorFragment extends Fragment {

    private PageViewModel pageViewModel;
    private TextView actorName;
    private TextView actorBirthDate;
    private TextView actorDeathDate;
    private TextView actorBiography;
    private ImageView actorImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actor, container, false);

        actorName = view.findViewById(R.id.actor_name);
        actorBirthDate = view.findViewById(R.id.actor_birth_date);
        actorDeathDate = view.findViewById(R.id.actor_death_date);
        actorBiography = view.findViewById(R.id.actor_biography);
        actorImage = view.findViewById(R.id.actor_poster);

        pageViewModel.getActorDetail().observe(requireActivity(), actor -> {
            actorName.setText(actor.getName());

            String birthDate = formatDate(actor.getBirthday());
            String deathDate = formatDate(actor.getDeathday());

            actorBirthDate.setText(birthDate);
            actorDeathDate.setText(deathDate);
            actorBiography.setText(actor.getBiography());

            if (actor.getProfile_path() != null)
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w300" + actor.getProfile_path())
                        .into(actorImage);
            else
                actorImage.setImageResource(R.drawable.ic_baseline_person);
        });


        return view;
    }

    /**
     * Récupère une date par référence et la formate
     */
    private String formatDate(String date) {
        if (date == null)
            return "N/A";

        String[] dateSplit = date.split("-");

        String day = dateSplit[2];

        String month = dateSplit[1];

        String year = dateSplit[0];

        String[] monthArray = getResources().getStringArray(R.array.month_array);

        Integer monthInt = Integer.parseInt(month);

        month = monthArray[monthInt - 1];

        date = day + " " + month + " " + year;

        return date;
    }
}