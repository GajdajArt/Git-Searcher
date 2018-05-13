package com.labralab.githubsearcher.repository;

import android.provider.Settings;
import android.widget.Toast;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.models.HistoryItem;
import com.labralab.githubsearcher.models.Organization;
import com.labralab.githubsearcher.models.Repository;
import com.labralab.githubsearcher.presenters.MainPresenter;
import com.labralab.githubsearcher.presenters.ResultPresenter;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 12.05.2018.
 */

public class SearchRepository {

    final static String BASE_URL = "https://api.github.com";

    Retrofit retrofit;
    API api;
    Realm realm;

    public SearchRepository() {

        //Получаем Экземпляр Realm
        realm = Realm.getDefaultInstance();

        //Штука которая решает проблему интернет соединения на старых версиях
        OkHttpClient client = new OkHttpClient();
        try {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(new TLSSocketFactory())
                    .build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Создаем экземпляр Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);

    }


    //Получаем список Организаций передаем их в mainPresenter
    public void getCompanyList(final String title, final MainPresenter mainPresenter1) {

        Call<Organization> call = api.getOrgs(title);

        call.enqueue(new Callback<Organization>() {
            @Override
            public void onResponse(Call<Organization> call, Response<Organization> response) {

                List<Organization> list = new ArrayList<>();

                //Если список не пустой
                if (response.body() != null) {

                    list.add(response.body());
                    mainPresenter1.setData(list);
                    mainPresenter1.hidePB();

                } else {

                    mainPresenter1.hidePB();
                    mainPresenter1.nothingFoundHint(title);
                }

            }

            //Если возникла проблема
            @Override
            public void onFailure(Call<Organization> call, Throwable t) {
                mainPresenter1.hidePB();
                mainPresenter1.networkErrorHint();
            }
        });
    }

    //Получаем список репозиториев передаем их в mainPresenter
    public void getRepoList(String title, final ResultPresenter resultPresenter) {

        Call<List<Repository>> call = api.getRepo(title);

        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {

                //Если список не пуст
                if (response.body() != null) {
                    List<Repository> list = new ArrayList<>();

                    list = response.body();
                    resultPresenter.setData(list);

                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
            }
        });
    }

    //Сохраняем Организаю в истории поиска
    public void saveInHistory(Organization organization) {

        HistoryItem item = new HistoryItem();
        item.setTitle(organization.getLogin());
        item.setLogoURL(organization.getAvatarUrl());
        item.setDate(System.currentTimeMillis());
        item.setRepo(organization.getPublicRepos());

        realm.beginTransaction();
        realm.insert(item);
        realm.commitTransaction();

    }

    //Получаем список запросов из истории
    public List<HistoryItem> getHistoryList() {

        List<HistoryItem> list = realm.where(HistoryItem.class).findAll();
        return list;
    }

    //Очистка списка истории
    public void removeAllHistory() {

        realm.beginTransaction();
        realm.where(HistoryItem.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();

    }
}
