package com.example.movielibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.movielibrary.APIMovie.Genre;
import com.example.movielibrary.APIMovie.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MovieInformationFragment extends Fragment {

    private PageViewModel pageViewModel;
    private ImageView btn_like;
    private DatabaseHelper dbHelper;

    private TextView noMovieMessage;
    private LinearLayout infoLayout;

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
        View view = inflater.inflate(R.layout.fragment_movie_information, container, false);

        ScrollView scrollView = view.findViewById(R.id.info_main_scrollview);
        noMovieMessage = view.findViewById(R.id.info_no_movie_message);
        infoLayout = view.findViewById(R.id.info_panel);
        infoLayout.setVisibility(View.INVISIBLE);

        // Gère le bouton like pour la version mobile
        if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.SMALL) {
            Log.d("MovieInformationFragment", "onCreateView: " + pageViewModel.getScreenSize());
            btn_like = view.findViewById(R.id.info_like);
        } else {
            btn_like = null;
        }

        if (btn_like != null) {

            // Listener sur le like
            btn_like.setOnClickListener(v -> {
                if (pageViewModel.getMovie().getValue() != null) {
                    Movie movie = pageViewModel.getMovie().getValue();
                    if (dbHelper.updateLike(movie.getId())) {
                        btn_like.setImageResource(R.drawable.like_full);
                    } else {
                        btn_like.setImageResource(R.drawable.like_null);
                    }
                    pageViewModel.setNeedUpdate(true);
                }
            });
        }

        // Met à jour les informations du film
        pageViewModel.getMovie().observe(requireActivity(), movie -> {
            if (movie != null) {
                if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.SMALL) {
                    if (dbHelper.isMovieLiked(movie.getId())) {
                        btn_like.setImageResource(R.drawable.like_full);
                    } else {
                        btn_like.setImageResource(R.drawable.like_null);
                    }
                    scrollView.smoothScrollTo(0, 0);
                } else {
                    noMovieMessage.setVisibility(View.INVISIBLE);
                    infoLayout.setVisibility(View.VISIBLE);
                }
                updateMovieInformation(view, movie);
            } else if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.LARGE) {
                noMovieMessage.setVisibility(View.VISIBLE);
                infoLayout.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    private void updateMovieInformation(View view, Movie movie) {
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

        // Parse la date 1988-10-21 en Octobre 1988
        String[] dateSplit = date.split("-");
        String month = dateSplit[1];
        String year = dateSplit[0];
        String[] planets = getResources().getStringArray(R.array.month_array);

        Integer monthInt = Integer.parseInt(month);

        month = planets[monthInt - 1];

        date = month + " " + year;
        releaseDate.setText(date);

        // Met à jour la durée du film
        String time = movie.getRuntime() + " min";
        duration.setText(time);

        // Met à jour les genres
        TextView genre1 = view.findViewById(R.id.info_movie_genre_1);
        TextView genre2 = view.findViewById(R.id.info_movie_genre_2);
        TextView genre3 = view.findViewById(R.id.info_movie_genre_3);

        List<Genre> genres = movie.getGenre();

        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).getName().equals("Science Fiction") || genres.get(i).getName().equals("Science-Fiction")) {
                genres.get(i).setName("Sci-Fi");
            }

            switch (i) {
                case 0:
                    genre1.setText(genres.get(i).getName());
                    break;
                case 1:
                    genre2.setText(genres.get(i).getName());
                    break;
                case 2:
                    genre3.setText(genres.get(i).getName());
                    break;
            }
        }

        // Cache des genre si il en manque
        if (genres.size() < 3) {
            genre3.setVisibility(View.GONE);
        } else {
            genre3.setVisibility(View.VISIBLE);
        }

        if (genres.size() < 2) {
            genre2.setVisibility(View.GONE);
        } else {
            genre2.setVisibility(View.VISIBLE);
        }

        if (genres.size() < 1) {
            genre1.setVisibility(View.GONE);
        } else {
            genre1.setVisibility(View.VISIBLE);
        }

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
    }
}