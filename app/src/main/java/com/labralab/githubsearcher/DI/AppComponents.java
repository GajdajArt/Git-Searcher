package com.labralab.githubsearcher.DI;

import com.labralab.githubsearcher.presenters.HistoryPresenter;
import com.labralab.githubsearcher.presenters.MainPresenter;
import com.labralab.githubsearcher.presenters.ResultPresenter;
import com.labralab.githubsearcher.presenters.adapters.CompanyAdapter;
import com.labralab.githubsearcher.views.HistoryActivity;
import com.labralab.githubsearcher.views.MainActivity;
import com.labralab.githubsearcher.views.ResultActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pc on 11.05.2018.
 */

@Singleton
@Component(modules = PresenterModule.class)
public interface AppComponents {

    void inject(MainActivity mainActivity);
    void inject(ResultActivity resultActivity);
    void inject(MainPresenter mainPresenter);
    void inject(ResultPresenter resultPresenter);
    void inject(HistoryActivity historyActivity);
    void inject(HistoryPresenter historyPresenter);
    void inject(CompanyAdapter companyAdapter);
}
