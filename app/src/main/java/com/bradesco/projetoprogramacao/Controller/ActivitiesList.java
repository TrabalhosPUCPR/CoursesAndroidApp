package com.bradesco.projetoprogramacao.controller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.services.localServices.ActivitiesService;
import com.bradesco.projetoprogramacao.view.ActivitiesListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivitiesList extends AppCompatActivity {

    ActivitiesService service;
    ActivityResultLauncher<Intent> resultLauncher;
    ActivitiesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_list);

        this.service = new ActivitiesService(this);
        List<Activities> activities = service.getList();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Activities");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);

        createRcvView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }

    void createRcvView(){
        RecyclerView rcvActivities = findViewById(R.id.rcv_activities);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rcvActivities.setLayoutManager(llm);
        adapter = new ActivitiesListAdapter(service.getList(), this);
        rcvActivities.setAdapter(adapter);
    }
}