package com.bradesco.projetoprogramacao.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.controller.sandBox.SandboxActivity;
import com.bradesco.projetoprogramacao.model.services.LocalServices.CourseService;

import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_person);

        Button button = findViewById(R.id.btn_option1_optionsMenu);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, CoursesList.class);
            startActivity(intent);
        });
        button = findViewById(R.id.btn_option2_optionsMenu);
        button.setOnClickListener(view -> {
            // TODO: 10/21/2022 activities 
        });
        button = findViewById(R.id.btn_option3_optionsMenu);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, SandboxActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        TextView coursesAmount = findViewById(R.id.completedCoursesNumber);
        CourseService courseService = new CourseService(this);
        coursesAmount.setText(String.valueOf(courseService.getCompletedCourses().size()));
        coursesAmount = findViewById(R.id.totalCoursesNumber);
        coursesAmount.setText(String.valueOf(courseService.size()));
        if(courseService.size() == courseService.getCompletedCourses().size()){
            findViewById(R.id.completedStars).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.completedStars).setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_activity_menubar, menu);
        return true;
    }
}