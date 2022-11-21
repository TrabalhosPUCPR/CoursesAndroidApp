package com.bradesco.projetoprogramacao.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivitiesListAdapterBinding;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.services.localServices.DifficultyService;

import java.util.List;

public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ActivitiesViewHolder> {

    List<Activities> activities;
    Context context;

    public ActivitiesListAdapter(List<Activities> activities, Context context) {
        this.activities = activities;
        this.context = context;
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivitiesListAdapterBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activities_list_adapter, parent, false);

        //ActivitiesListAdapterBinding binding = ActivitiesListAdapterBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ActivitiesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position) {
        holder.binding.setActivities(this.activities.get(position));
        DifficultyService difficultyService = new DifficultyService(context);
        holder.binding.adapterTextDifficulty.setText(difficultyService.get(this.activities.get(position).getDifficulty()));
        holder.binding.adapterBtnPlay.setOnClickListener(view -> {
            // TODO: 11/21/2022 play activity
        });
        if(activities.get(position).isCompleted()){
            holder.binding.adapterCompleteCheckMark.setVisibility(View.VISIBLE);
        }else{
            holder.binding.adapterCompleteCheckMark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ActivitiesViewHolder extends RecyclerView.ViewHolder{
        ActivitiesListAdapterBinding binding;
        TextView textView;
        public ActivitiesViewHolder(ActivitiesListAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
