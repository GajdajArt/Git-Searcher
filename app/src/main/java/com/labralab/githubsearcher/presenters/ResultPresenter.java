package com.labralab.githubsearcher.presenters;

import android.content.Intent;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.models.Repository;
import com.labralab.githubsearcher.presenters.adapters.GitRepoAdapter;
import com.labralab.githubsearcher.repository.SearchRepository;
import com.labralab.githubsearcher.views.ResultActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pc on 11.05.2018.
 */

public class ResultPresenter {

    private ResultActivity resultActivity;
    private List<Repository> data;
    private GitRepoAdapter gitRepoAdapter;

    @Inject
    SearchRepository searchRepository;

    private String title;
    private int size;

    public ResultPresenter() {

        data = new ArrayList<>();
        gitRepoAdapter = new GitRepoAdapter();
    }

    public void connect(ResultActivity resultActivity){

        //Связываемся с resultActivity
        this.resultActivity = resultActivity;
        //Получаем имя Организации
        setTitle();
        //Запрос на получение списка репозиториев
        getData();

    }

    //Запрос для получения списка репозиториев
    private void getData(){

        App.getAppComponents().inject(this);
        searchRepository.getRepoList(title, this);

    }

    //Получение списка репозиториев и передача их в адаптер
    public void setData(List<Repository> newData){

        gitRepoAdapter.setList(newData);
        resultActivity.getGitRepoRV().setAdapter(gitRepoAdapter);

    }

    //Получаем имя Организации и количество репозиториев
    private void setTitle(){

        Intent intent = resultActivity.getIntent();
        title = intent.getStringExtra(ResultActivity.TITLE_KAY);
        size = intent.getIntExtra(ResultActivity.REPO_SIDE_KAY, 0);

        //Передаем строку в Toolbar
        resultActivity.getToolbar().setTitle(title + " repository (" + size + ")" );
    }
}
