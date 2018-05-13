package com.labralab.githubsearcher.presenters.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.models.Organization;
import com.labralab.githubsearcher.repository.SearchRepository;
import com.labralab.githubsearcher.views.ResultActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pc on 11.05.2018.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapterHolder> {

    List<Organization> list = new ArrayList<>();
    @Inject
    SearchRepository repository;

    @Override
    public CompanyAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        App.getAppComponents().inject(this);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_item, parent, false);
        return new CompanyAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyAdapterHolder holder, int position) {

        final Organization org = list.get(position);

        holder.title.setText(org.getLogin());
        holder.place.setText(org.getLocation());
        holder.webCite.setText(org.getBlog());

        Picasso.get()
                .load(org.getAvatarUrl())
                .into(holder.logo);

        //Обработка нажатия на эллемент списка
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Сохраняем запрос в истории
                repository.saveInHistory(org);

                //Запускаем ResultActivity понажатию на эллемент списка
                Intent intent = new Intent(view.getContext(), ResultActivity.class);
                intent.putExtra(ResultActivity.TITLE_KAY, org.getLogin());
                intent.putExtra(ResultActivity.REPO_SIDE_KAY, org.getPublicRepos());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Метод для передачи данных в список
    public void setList(List<Organization> data){
        this.list = data;
        notifyDataSetChanged();
    }
}

class CompanyAdapterHolder extends RecyclerView.ViewHolder  {

    TextView title;
    TextView place;
    TextView webCite;
    ImageView logo;
    View view;



    public CompanyAdapterHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.company_title);
        place = (TextView) itemView.findViewById(R.id.company_place);
        webCite = (TextView) itemView.findViewById(R.id.company_web);
        logo = (ImageView) itemView.findViewById(R.id.company_logo);
        view = itemView;


    }
}