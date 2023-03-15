package com.example.movielibrary.APIMovie;

import com.google.gson.annotations.SerializedName;

/**
 * Class qui représente un acteur
 */
public class Actor {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profile_path;

    public Actor(int id, String name, String profile_path) {
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
