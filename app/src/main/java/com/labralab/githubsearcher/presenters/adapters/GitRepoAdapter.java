package com.labralab.githubsearcher.presenters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.models.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 11.05.2018.
 */

public class GitRepoAdapter extends RecyclerView.Adapter<GitRepoAdapterHolder> {

    List<Repository> list = new ArrayList<>();

    @Override
    public GitRepoAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_list_item,
                parent, false);
        return new GitRepoAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(GitRepoAdapterHolder holder, int position) {

        Repository item = list.get(position);

        holder.title.setText(item.getName());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Метод для передачи данных в список
    public void setList(List<Repository> data) {
        this.list = data;
        notifyDataSetChanged();
    }
}

class GitRepoAdapterHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView description;

    public GitRepoAdapterHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.repo_title);
        description = (TextView) itemView.findViewById(R.id.repo_description);

    }
}