package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private PageViewModel pageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise le  ViewModel
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        // Choix de la langue de l'API en fonction de la langue du téléphone
        String languagename = Locale.getDefault().getDisplayLanguage();

        if (languagename.equals("français")) {
            pageViewModel.setLanguage("fr-FR");
        } else {
            pageViewModel.setLanguage("en-US");
        }

        // Modifi le fonctionnement de l'application en fonction de la taille de l'écran
        if (getResources().getConfiguration().smallestScreenWidthDp < 600) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
            pageViewModel.setScreenSize(PageViewModel.ScreenSize.SMALL);
        } else if (getResources().getConfiguration().smallestScreenWidthDp < 1200) {
            pageViewModel.setScreenSize(PageViewModel.ScreenSize.MEDIUM);
        } else {
            pageViewModel.setScreenSize(PageViewModel.ScreenSize.LARGE);
        }

        pageViewModel.getMovie().observe(this, movie -> {
            if (movie != null) {
                if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.SMALL) {
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_list_fragment_container)).commit();
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("test", "onBackPressed: " + pageViewModel.getScreenSize());
        if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.SMALL) {
            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_list_fragment_container)).commit();
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
            pageViewModel.setMovie(null);
        } else {
            super.onBackPressed();
        }
    }
}