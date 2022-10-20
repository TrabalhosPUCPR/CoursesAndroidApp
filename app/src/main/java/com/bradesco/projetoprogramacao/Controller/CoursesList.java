package com.bradesco.projetoprogramacao.Controller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bradesco.projetoprogramacao.Controller.CourseFragments.CourseActivity;
import com.bradesco.projetoprogramacao.Model.Course.CourseListManager;
import com.bradesco.projetoprogramacao.Model.Course.CourseModel;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.View.CoursesListAdapter;

import java.util.Objects;

public class CoursesList extends AppCompatActivity {

    RecyclerView rcvCourses;
    CoursesListAdapter coursesAdapter;
    ActivityResultLauncher<Intent> resultOpenCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Courses");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);

        resultOpenCourse = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK) {
                int index = result.getData().getIntExtra("index", -1);
                CourseModel course = CourseListManager.getInstance().get(index);
                course.setCompleted(true);
                CourseListManager.getInstance().edit(course, index);
                coursesAdapter.notifyItemChanged(index);
            }
        });
        createTodoRcvView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }

    void createTodoRcvView(){
        rcvCourses = findViewById(R.id.rcv_courses);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rcvCourses.setLayoutManager(llm);
        coursesAdapter = new CoursesListAdapter(CourseListManager.getInstance().getList(), this, resultOpenCourse);
        rcvCourses.setAdapter(coursesAdapter);
    }
}