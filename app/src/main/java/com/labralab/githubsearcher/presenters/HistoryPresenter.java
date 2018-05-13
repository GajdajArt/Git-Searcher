package com.labralab.githubsearcher.presenters;

import android.view.View;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.models.HistoryItem;
import com.labralab.githubsearcher.presenters.adapters.HistoryAdapter;
import com.labralab.githubsearcher.repository.SearchRepository;
import com.labralab.githubsearcher.views.HistoryActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pc on 13.05.2018.
 */

public class HistoryPresenter {

    HistoryActivity historyActivity;
    HistoryAdapter adapter;
    List<HistoryItem> data;

    @Inject
    SearchRepository searchRepository;

    //Метод для связи с historyActivity
    public void connect(HistoryActivity historyActivity) {

        this.historyActivity = historyActivity;
        data = new ArrayList<>();
        //Инжектим репозиторий
        App.getAppComponents().inject(this);
        //Получаем данный из базы
        setData();
        //Показываем подсказку если список пуст
        setHint();
    }

    //Получение данных из базы и передача их в адаптер списка
    public void setData(){

        data = searchRepository.getHistoryList();
        adapter = new HistoryAdapter();
        adapter.setList(data);
        historyActivity.getHistoryRV().setAdapter(adapter);

    }

    //Показываем подсказку если список пуст
    private void setHint(){

        if(data.isEmpty()){
            historyActivity.getHistoryHintTV().setVisibility(View.VISIBLE);
            historyActivity.getHistoryHintTV().setText(R.string.history_is_empty);
        }else {
            historyActivity.getHistoryHintTV().setVisibility(View.INVISIBLE);
        }
    }

    //Метод для очистки списка истории запросов
    public void removeList(){

        searchRepository.removeAllHistory();
        setData();
        setHint();
    }

}
