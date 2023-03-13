package com.example.movielibrary;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movielibrary.APIMovie.Genre;
import com.example.movielibrary.APIMovie.MovieAPIView;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

public class DrawerMenuFragment extends Fragment {

    private ImageView closeButton;
    private PageViewModel pageViewModel;
    private RangeSlider dateSlider;
    private TextView textviewMinYear;
    private TextView textviewMaxYear;

    private int minYear;
    private int maxYear;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drawer_menu, container, false);

        closeButton = view.findViewById(R.id.drawer_close);

        dateSlider = view.findViewById(R.id.parameter_year_slider);
        dateSlider.setLabelBehavior(LabelFormatter.LABEL_GONE);
        textviewMinYear = view.findViewById(R.id.parameter_first_year);
        textviewMaxYear = view.findViewById(R.id.parameter_second_year);

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
            //TODO crée les radio boutons, les ajouter dans une liste et faire que quand on clique sur un bouton on déselectionne les autres
            for (Genre genre : genreList) {
                Log.d("Genre", genre.getName());
            }
        });

        //TODO mettre à jour le onlyLike

        return view;
    }


}