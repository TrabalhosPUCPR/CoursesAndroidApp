package com.bradesco.projetoprogramacao.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.controller.playActivityFragments.PlayActivities;
import com.bradesco.projetoprogramacao.databinding.ActivitiesListAdapterBinding;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.services.localServices.DifficultyService;

import java.util.List;

public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ActivitiesViewHolder> {

    List<Activities> activities;
    Context context;
    ActivityResultLauncher<Intent> resultLauncher;

    public ActivitiesListAdapter(List<Activities> activities, Context context, ActivityResultLauncher<Intent> resultLauncher) {
        this.activities = activities;
        this.context = context;
        this.resultLauncher = resultLauncher;
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivitiesListAdapterBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activities_list_adapter, parent, false);
        return new ActivitiesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position) {
        holder.binding.setActivities(this.activities.get(position));
        DifficultyService difficultyService = new DifficultyService(context);
        holder.binding.adapterTextDifficulty.setText(difficultyService.get(this.activities.get(position).getDifficulty()));
        holder.binding.adapterBtnPlay.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, PlayActivities.class);
            intent.putExtra("id", activities.get(position).getId());
            intent.putExtra("type", 1);
            resultLauncher.launch(intent);
        });
        if(activities.get(position).isCompleted()){
            holder.binding.adapterCompleteCheckMark.setVisibility(View.VISIBLE);
        }else{
            holder.binding.adapterCompleteCheckMark.setVisibility(View.GONE);
        }
        if(activities.get(position).isExpanded()){
            holder.binding.adapterExpandableArea.setVisibility(View.VISIBLE);
        }else{
            holder.binding.adapterExpandableArea.setVisibility(View.GONE);
        }

        holder.binding.adapterLayout.setOnClickListener(view -> {
            activities.get(position).expand();
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ActivitiesViewHolder extends RecyclerView.ViewHolder{
        ActivitiesListAdapterBinding binding;
        public ActivitiesViewHolder(ActivitiesListAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
