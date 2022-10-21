package com.bradesco.projetoprogramacao.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.Model.Course.CourseListManager;
import com.bradesco.projetoprogramacao.R;

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
        coursesAmount.setText(String.valueOf(CourseListManager.getInstance().getCompletedCourses().size()));
        coursesAmount = findViewById(R.id.totalCoursesNumber);
        coursesAmount.setText(String.valueOf(CourseListManager.getInstance().getList().size()));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_activity_menubar, menu);
        return true;
    }
}