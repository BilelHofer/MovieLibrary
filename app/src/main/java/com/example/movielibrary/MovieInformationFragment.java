package com.example.movielibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
            TextView releaseDate = view.findViewById(R.id.info_movie_date);
            TextView duration = view.findViewById(R.id.info_movie_time);

            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());

            String posterPath = movie.getPosterPath();

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterPath)
                    .into(poster);

            // Met à jour la date de sortie
            String date = movie.getRelease_date();
            releaseDate.setText(date);

            // Met à jour la durée du film
            String time = movie.getRuntime() + " min";
            duration.setText(time);

            // Mets à jour l'affichage des étoiles
            Float voteAverage = Float.parseFloat(movie.getVote_average()) / 2.0f;

            // vote average tous les 0.5
            int star = (int) Math.floor(voteAverage);
            int halfStar = (int) Math.ceil(voteAverage - star);
            int emptyStar = 5 - star - halfStar;

            ImageView star1 = view.findViewById(R.id.info_star_1);
            ImageView star2 = view.findViewById(R.id.info_star_2);
            ImageView star3 = view.findViewById(R.id.info_star_3);
            ImageView star4 = view.findViewById(R.id.info_star_4);
            ImageView star5 = view.findViewById(R.id.info_star_5);

            // Remplis les étoiles
            for (int i = 0; i < star; i++) {
                switch (i) {
                    case 0:
                        star1.setImageResource(R.drawable.star_full_filled);
                        break;
                    case 1:
                        star2.setImageResource(R.drawable.star_full_filled);
                        break;
                    case 2:
                        star3.setImageResource(R.drawable.star_full_filled);
                        break;
                    case 3:
                        star4.setImageResource(R.drawable.star_full_filled);
                        break;
                    case 4:
                        star5.setImageResource(R.drawable.star_full_filled);
                        break;
                }
            }

            // Remplis les demi étoiles
            if (halfStar == 1) {
                switch (star) {
                    case 1:
                        star2.setImageResource(R.drawable.star_half_filled);
                        break;
                    case 2:
                        star3.setImageResource(R.drawable.star_half_filled);
                        break;
                    case 3:
                        star4.setImageResource(R.drawable.star_half_filled);
                        break;
                    case 4:
                        star5.setImageResource(R.drawable.star_half_filled);
                        break;
                }
            }

            // Remplis les étoiles vides
            if (emptyStar >= 1) {
                if (halfStar == 1)
                    star++;

                switch (star) {
                    case 0:
                        star1.setImageResource(R.drawable.star_not_filled);
                        star2.setImageResource(R.drawable.star_not_filled);
                        star3.setImageResource(R.drawable.star_not_filled);
                        star4.setImageResource(R.drawable.star_not_filled);
                        star5.setImageResource(R.drawable.star_not_filled);
                        break;
                    case 1:
                        star2.setImageResource(R.drawable.star_not_filled);
                        star3.setImageResource(R.drawable.star_not_filled);
                        star4.setImageResource(R.drawable.star_not_filled);
                        star5.setImageResource(R.drawable.star_not_filled);
                        break;
                    case 2:
                        star3.setImageResource(R.drawable.star_not_filled);
                        star4.setImageResource(R.drawable.star_not_filled);
                        star5.setImageResource(R.drawable.star_not_filled);
                        break;
                    case 3:
                        star4.setImageResource(R.drawable.star_not_filled);
                        star5.setImageResource(R.drawable.star_not_filled);
                        break;
                    case 4:
                        star5.setImageResource(R.drawable.star_not_filled);
                        break;
                }
            }
        });
        return view;
    }
}