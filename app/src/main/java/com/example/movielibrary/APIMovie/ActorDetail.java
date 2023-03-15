package com.example.movielibrary.APIMovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorDetail {
    @SerializedName("name")
    private String name;

    @SerializedName("biography")
    private String biography;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("deathday")
    private String deathday;

    @SerializedName("profile_path")
    private String profile_path;

    public ActorDetail(String name, String biography, String birthday, String deathday, String profile_path) {
        this.name = name;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
