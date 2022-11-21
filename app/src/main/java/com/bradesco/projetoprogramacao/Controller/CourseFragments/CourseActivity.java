package com.bradesco.projetoprogramacao.controller.courseFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding;
import com.bradesco.projetoprogramacao.model.course.Chapters;
import com.bradesco.projetoprogramacao.model.course.Course;
import com.bradesco.projetoprogramacao.model.course.Page;
import com.bradesco.projetoprogramacao.model.course.Question;
import com.bradesco.projetoprogramacao.model.services.localServices.CourseService;
import com.google.android.material.bottomappbar.BottomAppBar;

public class CourseActivity extends AppCompatActivity {

    protected static int currentChapter;
    protected static int currentPage;
    protected static int currentQuestion;
    protected static int indexPosition;
    protected static int courseId;
    protected static Course course;
    static boolean finished;
    static BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CourseService courseService = new CourseService(this);
        com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding binding = ActivityCourseBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        bottomAppBar = root.findViewById(R.id.course_bottom_menuBar);
        binding.courseBottomMenuBar.setNavigationOnClickListener(view -> finish());
        binding.courseBottomMenuBar.setOnMenuItemClickListener(null);
        courseId = getIntent().getIntExtra("id", -1);
        course = courseService.get(courseId);
        indexPosition = getIntent().getIntExtra("position", -1);
        currentChapter = 0;
        currentPage= 0;
        currentQuestion = 0;
        finished = false;

        setContentView(root);
    }

    static boolean nextChapter(){
        currentPage = 0;
        currentChapter++;
        return course.getChapters().size() > currentChapter;
    }
    static boolean previousChapter(){
        if(currentChapter == 0){
            return false;
        }
        currentChapter--;
        return true;
    }
    static protected Chapters getChapter() {
        return course.getChapter(currentChapter);
    }
    static protected Page getPage() {
        return getChapter().getPages().get(currentPage);
    }
    static protected Question getQuestion(){
        return course.getEndingQuestions().get(currentQuestion);
    }
}