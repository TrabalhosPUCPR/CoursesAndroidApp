package com.bradesco.projetoprogramacao.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.controller.codeRunner.SandboxActivity;
import com.bradesco.projetoprogramacao.model.services.localServices.CourseService;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button startLearning = findViewById(R.id.optMenu_start_learning);
        startLearning.setOnClickListener(view -> {
            Intent intent = new Intent(this, CoursesList.class);
            startActivity(intent);
        });

        Button button = findViewById(R.id.btn_option1_optionsMenu);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, CoursesList.class);
            startActivity(intent);
        });
        button = findViewById(R.id.btn_option2_optionsMenu);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivitiesList.class);
            startActivity(intent);
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
            findViewById(R.id.optMenu_start_learning).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.completedStars).setVisibility(View.INVISIBLE);
            findViewById(R.id.optMenu_start_learning).setVisibility(View.VISIBLE);
        }
        courseService.close();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_activity_menubar, menu);
        return true;
    }
}