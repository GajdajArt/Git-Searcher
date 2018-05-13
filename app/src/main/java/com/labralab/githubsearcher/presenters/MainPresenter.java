package com.labralab.githubsearcher.presenters;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.models.Organization;
import com.labralab.githubsearcher.presenters.adapters.CompanyAdapter;
import com.labralab.githubsearcher.repository.SearchRepository;
import com.labralab.githubsearcher.views.HistoryActivity;
import com.labralab.githubsearcher.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pc on 11.05.2018.
 */

public class MainPresenter {

    MainActivity mainActivity;
    List<Organization> data = new ArrayList<>();
    CompanyAdapter adapter;
    String searchArgument = "";

    @Inject
    SearchRepository repository;

    public MainPresenter() {

        data = new ArrayList<>();
        adapter = new CompanyAdapter();
        //Получаем экземпляр SearchRepository
        App.getAppComponents().inject(this);
    }

    //Метод для свзывания перзентера и активити
    //В нем происходит передача сохраненных данных в активити если связь была прервана ранее
    public void connect(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
        setStartHint();
        if (!searchArgument.isEmpty()) {
            mainActivity.geteTSearch().setText(searchArgument);
            search(searchArgument);
        }

    }

    //Создание поискового запроса
    public void search(String request) {

        searchArgument = request;
        repository.getCompanyList(request, this);
        nothingFoundHint(request);

    }

    //Получение данных и передача их в адаптер списка
    public void setData(List<Organization> newData) {

        data = newData;
        adapter.setList(data);
        mainActivity.getRVCompany().setAdapter(adapter);
        setStartHint();

    }

    //Очистка списка и отображение актуальной подсказки
    public void clearData(String request) {

        searchArgument = request;
        data.clear();
        adapter.setList(data);
        mainActivity.getRVCompany().setAdapter(adapter);
        nothingFoundHint(searchArgument);
    }

    //Стартовая подсказка
    public void setStartHint() {

        if (data.isEmpty()) {
            mainActivity.getHintTV().setText(R.string.hint);
            hidePB();
        } else {
            mainActivity.getHintTV().setVisibility(View.INVISIBLE);
        }
    }

    //Подсказака показываемая когда ничего не найдено по запросу
    public void nothingFoundHint(String request) {

        if (request.length() >= 3 && !data.isEmpty()) {
            mainActivity.getHintTV().setVisibility(View.INVISIBLE);
        } else {
            mainActivity.getHintTV().setVisibility(View.VISIBLE);
            mainActivity.getHintTV().setText(R.string.nothing_found);
        }
    }

    //Подсказка показываемая при ошибке соеденения
    public void networkErrorHint() {
        mainActivity.getHintTV().setVisibility(View.VISIBLE);
        mainActivity.getHintTV().setText(R.string.something_went_wrong);
    }

    //Запуск HistoryActivity
    public void startHistoryActivity() {

        //Ести история не пустая
        if (!repository.getHistoryList().isEmpty()) {
            Intent intent = new Intent(mainActivity, HistoryActivity.class);
            mainActivity.startActivity(intent);
        } else {
            Toast.makeText(mainActivity, R.string.look_at_some_repositories
                    , Toast.LENGTH_SHORT).show();
        }
    }

    //Показать прогресБар
    public void showPB() {
        mainActivity.getProgressBar().setVisibility(View.VISIBLE);
    }

    //Спрятать прогресБар
    public void hidePB() {
        mainActivity.getProgressBar().setVisibility(View.INVISIBLE);
    }

}
