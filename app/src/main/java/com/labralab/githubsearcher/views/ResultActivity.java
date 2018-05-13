package com.labralab.githubsearcher.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.presenters.ResultPresenter;

import javax.inject.Inject;

public class ResultActivity extends AppCompatActivity {

    public final static String TITLE_KAY = "title";
    public final static String REPO_SIDE_KAY = "size";

    private RecyclerView gitRepoRV;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;

    @Inject
    ResultPresenter resultPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Инжектим экземпляр презентера
        App.getAppComponents().inject(this);
        //Связываемся со всеми View
        initView();

        //Связываемся с презентером
        resultPresenter.connect(this);
    }

    private void initView(){

        //Связываемся с RecyclerView
        gitRepoRV = (RecyclerView) findViewById(R.id.gitRepoRV);
        layoutManager = new LinearLayoutManager(this);
        gitRepoRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        gitRepoRV.setLayoutManager(layoutManager);

        //Связываемся с toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //"Назад" на тулБаре
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.baseline_arrow_back_white_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public RecyclerView getGitRepoRV() {
        return gitRepoRV;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

}
