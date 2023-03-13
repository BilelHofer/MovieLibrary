package com.example.movielibrary.APIMovie;

import com.google.gson.annotations.SerializedName;

/**
 * Class qui représente les acteurs d'un film
 */
public class CreditsResult {
    @SerializedName("cast")
    private Actor[] cast;

    public CreditsResult(Actor[] cast) {
        this.cast = cast;
    }

    public Actor[] getCast() {
        return cast;
    }
}
