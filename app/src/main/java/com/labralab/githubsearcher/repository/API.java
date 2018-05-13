package com.labralab.githubsearcher.repository;

import com.labralab.githubsearcher.models.Organization;
import com.labralab.githubsearcher.models.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by pc on 12.05.2018.
 */

public interface API {

    /**
     * Запрос для поиска Организации.
     * По факту это не поис а доступ к конкретной учетной записи, но в макете из задания, похоже,
     * реализован именно такой вариант. Для совершения класического поиска необходимо немного
     * изменить строку запроса.
     */
    @GET("/orgs/{login}")
    Call<Organization> getOrgs(@Path("login") String user);

    /**
     * Запрос для получения списка всех открытих репозиториев
     */
    @GET("/orgs/{login}/repos")
    Call<List<Repository>> getRepo(@Path(value = "login", encoded = true) String login);
}
