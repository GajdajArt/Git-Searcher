package com.labralab.githubsearcher.presenters.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.models.HistoryItem;
import com.labralab.githubsearcher.models.Repository;
import com.labralab.githubsearcher.views.ResultActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 12.05.2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapterHolder> {

    List<HistoryItem> list = new ArrayList<>();

    @Override
    public HistoryAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item,
                parent, false);
        return new HistoryAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapterHolder holder, int position) {

        final HistoryItem item = list.get(position);

        Date date = new Date();
        date.setTime(item.getDate());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm    dd.MM.yyyy");

        holder.title.setText(item.getTitle());
        holder.date.setText(format.format(date));

        Picasso.get()
                .load(item.getLogoURL())
                .into(holder.logo);

        //Обработка нажатия на эллемент списка истории
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Запускаем ResultActivity понажатию на эллемент списка
                Intent intent = new Intent(view.getContext(), ResultActivity.class);
                intent.putExtra(ResultActivity.TITLE_KAY, item.getTitle());
                intent.putExtra(ResultActivity.REPO_SIDE_KAY, item.getRepo());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Метод для передачи данных в список
    public void setList(List<HistoryItem> data) {
        this.list = data;
        notifyDataSetChanged();
    }
}

class HistoryAdapterHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView date;
    ImageView logo;
    View view;

    public HistoryAdapterHolder(View itemView) {
        super(itemView);

        view = itemView;

        title = (TextView) itemView.findViewById(R.id.history_title);
        date = (TextView) itemView.findViewById(R.id.history_date);
        logo = (ImageView) itemView.findViewById(R.id.history_logo);

    }

}
