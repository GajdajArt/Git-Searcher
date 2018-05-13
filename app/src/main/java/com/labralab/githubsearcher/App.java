package com.labralab.githubsearcher;

import android.app.Application;
import android.content.Context;

import com.labralab.githubsearcher.DI.AppComponents;
import com.labralab.githubsearcher.DI.DaggerAppComponents;
import com.labralab.githubsearcher.DI.PresenterModule;

import io.realm.Realm;

/**
 * Created by pc on 11.05.2018.
 */

public class App extends Application {

    static AppComponents appComponents;


    @Override
    public void onCreate() {
        super.onCreate();

        //Realm DB
        Realm.init(this);

        //creating DI components
        appComponents = DaggerAppComponents.builder()
                .presenterModule(new PresenterModule())
                .build();
    }

    public static AppComponents getAppComponents() {
        return appComponents;
    }
}
