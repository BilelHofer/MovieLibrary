package com.example.movielibrary.APIMovie;

import com.google.gson.annotations.SerializedName;

/**
 * Class qui représente un acteur
 */
public class Actor {
    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profile_path;

    public Actor(String name, String profile_path) {
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
