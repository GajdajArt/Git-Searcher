package com.labralab.githubsearcher.views;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.presenters.HistoryPresenter;

import javax.inject.Inject;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRV;
    private RecyclerView.LayoutManager layoutManager;
    private TextView historyHintTV;
    private FloatingActionButton removeFAB;
    private Toolbar toolbar;

    @Inject
    HistoryPresenter historyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Инжектим презентер
        App.getAppComponents().inject(this);
        //Связываемся со всеми View
        initView();
        //Связываемся с historyPresenter
        historyPresenter.connect(this);
    }

    private void initView(){

        //RecyclerView
        historyRV = (RecyclerView) findViewById(R.id.history_list);
        layoutManager = new LinearLayoutManager(this);
        historyRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        historyRV.setLayoutManager(layoutManager);

        //TextView подсказка
        historyHintTV = (TextView) findViewById(R.id.history_hint);

        //FloatingActionButton для очистки списка
        removeFAB = (FloatingActionButton) findViewById(R.id.removeButton);
        removeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyPresenter.removeList();
            }
        });

        //Связываемся с toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.baseline_arrow_back_white_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public RecyclerView getHistoryRV() {
        return historyRV;
    }

    public TextView getHistoryHintTV() {
        return historyHintTV;
    }
}
