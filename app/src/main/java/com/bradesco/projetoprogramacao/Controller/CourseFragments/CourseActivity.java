package com.bradesco.projetoprogramacao.Controller.CourseFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bradesco.projetoprogramacao.Model.Course.Course;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.Services.LocalServices.CourseService;
import com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding;
import com.google.android.material.bottomappbar.BottomAppBar;

public class CourseActivity extends AppCompatActivity {

    protected static int currentChapter, currentPage, courseId, currentQuestion, indexPosition;
    protected static Course course;
    protected static BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CourseService courseService = new CourseService(this);
        com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding binding = ActivityCourseBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        bottomAppBar = root.findViewById(R.id.course_bottom_menuBar);
        CourseActivity.bottomAppBar.setNavigationOnClickListener(view -> finish());
        CourseActivity.bottomAppBar.setOnMenuItemClickListener(null);
        courseId = getIntent().getIntExtra("id", -1);
        indexPosition = getIntent().getIntExtra("position", -1);
        course = courseService.get(courseId);
        currentChapter = 0;
        currentPage= 0;
        currentQuestion = 0;
    }
}