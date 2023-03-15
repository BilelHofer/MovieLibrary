package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private PageViewModel pageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise le  ViewModel
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_actor_information_fragment_container)).commit();

        // Choix de la langue de l'API en fonction de la langue du téléphone
        String languagename = Locale.getDefault().getDisplayLanguage();

        if (languagename.equals("français")) {
            pageViewModel.setLanguage("fr-FR");
        } else {
            pageViewModel.setLanguage("en-US");
        }

        // Modifi le fonctionnement de l'application en fonction de la taille de l'écran
        if (getResources().getConfiguration().screenWidthDp < 600) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            pageViewModel.setScreenSize(PageViewModel.ScreenSize.SMALL);
        } else if (getResources().getConfiguration().screenWidthDp > 1200) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            pageViewModel.setScreenSize(PageViewModel.ScreenSize.LARGE);
        }

        // Listener pour ouvrir le fragment d'information sur la version mobile
        pageViewModel.getMovie().observe(this, movie -> {
            if (movie != null) {
                if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.SMALL) {
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_list_fragment_container)).commit();
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_actor_information_fragment_container)).commit();
                }
            }
        });

        // Listener pour fermer le clavier au choix d'un film
        pageViewModel.getNeedCloseKeyboard().observe(this, needCloseKeyboard -> {
            if (needCloseKeyboard) {
                closeKeyboard();
            }
        });

        pageViewModel.getMenuOpen().observe(this, menuOpen -> {
            closeKeyboard();
        });
    }

    /**
     * Gère le bouton retour
     */
    @Override
    public void onBackPressed() {
        if (pageViewModel.getScreenSize() == PageViewModel.ScreenSize.SMALL) {
            if (getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container).isVisible()) {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_list_fragment_container)).commit();
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
                pageViewModel.setMovie(null);
            } else {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_actor_information_fragment_container)).commit();
            }
        } else {
            if (!getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container).isVisible()) {
                getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_actor_information_fragment_container)).commit();
            }
        }
    }

    /**
     * Ferme le claviery
     */
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Met à jour le fragment d'information sur l'acteur
     */
    public void updateView() {
        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.main_movie_information_fragment_container)).commit();
        getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.main_actor_information_fragment_container)).commit();

    }
}