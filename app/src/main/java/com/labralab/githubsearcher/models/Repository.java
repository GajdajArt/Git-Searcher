package com.labralab.githubsearcher.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repository {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
