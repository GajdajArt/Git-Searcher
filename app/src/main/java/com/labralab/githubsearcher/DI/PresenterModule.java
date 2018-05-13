package com.labralab.githubsearcher.DI;
import com.labralab.githubsearcher.presenters.HistoryPresenter;
import com.labralab.githubsearcher.presenters.MainPresenter;
import com.labralab.githubsearcher.presenters.ResultPresenter;
import com.labralab.githubsearcher.repository.SearchRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pc on 11.05.2018.
 */

@Module
public class PresenterModule {

    MainPresenter mainPresenter;
    ResultPresenter resultPresenter;


    @Provides
    @Singleton
    MainPresenter provideMainPresenter(){
        mainPresenter = new MainPresenter();
        return mainPresenter;
    }

    @Provides
    @Singleton
    ResultPresenter provideResultPresenter(){
        resultPresenter = new ResultPresenter();
        return resultPresenter;
    }

    @Provides
    @Singleton
    HistoryPresenter provideHistoryPresenter(){
        return new HistoryPresenter();
    }

    @Provides
    @Singleton
    SearchRepository provideRepository(){
        return new SearchRepository();
    }


}
