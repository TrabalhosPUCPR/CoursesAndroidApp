package com.bradesco.projetoprogramacao.controller.courseFragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.controller.playActivityFragments.PlayActivities;
import com.bradesco.projetoprogramacao.databinding.ActivityCourseBinding;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.course.Chapters;
import com.bradesco.projetoprogramacao.model.course.Course;
import com.bradesco.projetoprogramacao.model.course.Page;
import com.bradesco.projetoprogramacao.model.course.Question;
import com.bradesco.projetoprogramacao.model.services.localServices.ActivitiesService;
import com.bradesco.projetoprogramacao.model.services.localServices.CourseService;
import com.google.android.material.bottomappbar.BottomAppBar;

public class CourseActivity extends AppCompatActivity {

    protected static int currentChapter, currentPage, currentQuestion, currentActivity;
    protected static int indexPosition, courseId;
    protected static Course course;
    static boolean finished;
    static BottomAppBar bottomAppBar;
    static ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CourseService courseService = new CourseService(this);
        ActivityCourseBinding binding = ActivityCourseBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        bottomAppBar = root.findViewById(R.id.course_bottom_menuBar);
        binding.courseBottomMenuBar.setNavigationOnClickListener(view -> finish());
        binding.courseBottomMenuBar.setOnMenuItemClickListener(null);
        binding.courseBottomMenuBar.setNavigationIconTint(getResources().getColor(R.color.black));
        courseId = getIntent().getIntExtra("id", -1);
        course = courseService.get(courseId);
        indexPosition = getIntent().getIntExtra("position", -1);
        currentChapter = 0;
        currentPage= 0;
        currentQuestion = 0;
        currentActivity = 0;
        finished = false;

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK){
                ActivitiesService service = new ActivitiesService(this);
                Activities activities = service.get(result.getData().getIntExtra("id", -1));
                activities.setCompleted(true);
                service.edit(activities, activities.getId());
                service.close();
            }
        });

        courseService.close();
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
    static protected boolean hasNextActivity(){
        return course.getActivities().size() > currentActivity;
    }
    static protected Activities getActivities(){
        return course.getActivities().get(currentActivity);
    }
    static protected void launchActivities(Context context){
        while (hasNextActivity()){
            Intent intent = new Intent(context, PlayActivities.class);
            intent.putExtra("id", getActivities().getId());
            intent.putExtra("type", 2);
            resultLauncher.launch(intent);
            currentActivity++;
        }
    }
}