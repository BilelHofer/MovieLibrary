package com.example.movielibrary;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.movielibrary.APIMovie.BasicMovie;
import com.example.movielibrary.APIMovie.Genre;
import com.example.movielibrary.APIMovie.MovieAPIView;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;

public class DrawerMenuFragment extends Fragment {

    private ImageView closeButton;
    private PageViewModel pageViewModel;
    private RangeSlider dateSlider;
    private TextView textviewMinYear;
    private TextView textviewMaxYear;

    private ArrayList<RadioButton> genreRadioButtons = new ArrayList<>();
    private GridLayout genreGridLayout;

    private CheckBox onlyLikedCheckBox;

    private Button applyButton;
    private int minYear = 1874;
    private int maxYear = 2023;

    private int selectedGenreId = -1;

    private DatabaseHelper dbHelper;
    private Handler handler = new Handler();
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
        View view = inflater.inflate(R.layout.fragment_drawer_menu, container, false);

        closeButton = view.findViewById(R.id.drawer_close);

        dateSlider = view.findViewById(R.id.parameter_year_slider);
        textviewMinYear = view.findViewById(R.id.parameter_first_year);
        textviewMaxYear = view.findViewById(R.id.parameter_second_year);

        genreGridLayout = view.findViewById(R.id.genre_grid);

        RadioButton radioButtonAll = view.findViewById(R.id.genre_all);
        radioButtonAll.setChecked(true);
        genreRadioButtons.add(radioButtonAll);

        onlyLikedCheckBox = view.findViewById(R.id.checkbox_only_like);

        applyButton = view.findViewById(R.id.button_apply);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageViewModel.setMenuOpen(false);
                pageViewModel.setIsOnlyLiked(onlyLikedCheckBox.isChecked());
                pageViewModel.setIsOnFilter(true);

                if (pageViewModel.getIsOnlyLiked()) {
                    handler.post(getAllMovieLiked);
                } else {
                    MovieAPIView.getMoviesWithFilter(1, selectedGenreId, minYear, maxYear, pageViewModel);
                    pageViewModel.setFilterGenreId(selectedGenreId);
                    pageViewModel.setFilterYear1(minYear);
                    pageViewModel.setFilterYear2(maxYear);
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageViewModel.setMenuOpen(false);
            }
        });


        dateSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(RangeSlider slider, float value, boolean fromUser) {
                float value1 = slider.getValues().get(0);
                float value2 = slider.getValues().get(1);

                // Supprime les chiffre après la virgule
                int intValue1 = (int) value1;
                int intValue2 = (int) value2;

                // Met à jour les textView
                textviewMinYear.setText(String.valueOf(intValue1));
                textviewMaxYear.setText(String.valueOf(intValue2));

                minYear = intValue1;
                maxYear = intValue2;
            }
        });

        MovieAPIView.getGenres(pageViewModel);

        pageViewModel.getGenreList().observe(getViewLifecycleOwner(), genreList -> {
            for (Genre genre : genreList) {
                RadioButton radioButton = new RadioButton(getContext());

                // On change le style du bouton
                radioButton.setButtonDrawable(null);
                radioButton.setBackgroundResource(R.drawable.radio_selector);
                radioButton.setTextColor(Color.WHITE);
                radioButton.setGravity(Gravity.CENTER);

                // Ajoute 5dp de margin
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setMargins(5, 5, 5, 5);
                // Ajoute wrap_content
                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;

                radioButton.setLayoutParams(params);

                // On change le texte du bouton
                radioButton.setText(genre.getName());

                // On change l'id du bouton
                radioButton.setId(genre.getId());

                genreRadioButtons.add(radioButton);
                genreGridLayout.addView(radioButton);
            }

            initRadioButtons();
        });
        return view;
    }

    /**
     * Pour chaque radio button, on ajoute un listener qui déselectionne les autres
     */
    private void initRadioButtons() {
        for (RadioButton radioButton : genreRadioButtons) {
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (RadioButton radioButton : genreRadioButtons) {
                        if (radioButton.getId() != v.getId()) {
                            radioButton.setChecked(false);
                        }
                    }

                    if (v.getId() == R.id.genre_all)
                        selectedGenreId = -1;
                    else
                        selectedGenreId = v.getId();
                }
            });
        }
    }

    private Runnable getAllMovieLiked = new Runnable() {
        @Override
        public void run() {
            Cursor cursor = dbHelper.getAllLike();

            pageViewModel.setMovieList(new ArrayList<>());

            // On parcours le curseur
            while (cursor.moveToNext()) {

                MovieAPIView.getMovieToList(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOVIE_ID)), pageViewModel);

                // On attend 200ms pour éviter de spammer l'api
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
    };
}