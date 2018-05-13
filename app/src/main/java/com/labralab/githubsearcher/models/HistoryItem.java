package com.labralab.githubsearcher.models;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by pc on 12.05.2018.
 */

public class HistoryItem extends RealmObject {

    private String title;
    private String logoURL;
    private Long date;
    private int repo;

    public int getRepo() {
        return repo;
    }

    public void setRepo(int repo) {
        this.repo = repo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
