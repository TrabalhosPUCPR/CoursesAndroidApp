package com.bradesco.projetoprogramacao.Controller.CourseFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bradesco.projetoprogramacao.Model.Course.CourseListManager;
import com.bradesco.projetoprogramacao.Model.Course.Course;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding;
import com.google.android.material.bottomappbar.BottomAppBar;

public class CourseActivity extends AppCompatActivity {

    protected static int currentChapter, currentPage, courseIndex, currentQuestion;
    protected static Course course;
    protected static BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding binding = ActivityCourseBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        bottomAppBar = root.findViewById(R.id.course_bottom_menuBar);
        CourseActivity.bottomAppBar.setNavigationOnClickListener(view -> finish());
        CourseActivity.bottomAppBar.setOnMenuItemClickListener(null);
        courseIndex = getIntent().getIntExtra("index", -1);
        course = CourseListManager.getInstance().get(courseIndex);
        currentChapter = 0;
        currentPage= 0;
        currentQuestion = 0;
    }
}