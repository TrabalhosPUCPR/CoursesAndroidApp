package com.bradesco.projetoprogramacao.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradesco.projetoprogramacao.controller.courseFragments.CourseActivity;
import com.bradesco.projetoprogramacao.model.course.Course;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.model.services.LocalServices.CourseService;
import com.bradesco.projetoprogramacao.model.services.LocalServices.DifficultyService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CoursesListAdapter extends RecyclerView.Adapter<CoursesListAdapter.CoursesHolder> {

    Context context;
    List<Course> courses;
    ActivityResultLauncher<Intent> resultOpen;

    public CoursesListAdapter(Context context, ActivityResultLauncher<Intent> resultOpen) {
        this.context = context;
        CourseService courseService = new CourseService(context);
        this.courses = courseService.getList();
        this.resultOpen = resultOpen;
    }

    @NonNull
    @Override
    public CoursesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.courses_list_adapter, parent, false);
        return new CoursesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesHolder holder, int position) {
        Course course = courses.get(position);
        holder.title.setText(course.getTitle());
        holder.subtitle.setText(course.getSubTitle());
        if(course.isCompleted()){
            holder.completeCheckMark.setVisibility(View.VISIBLE);
        }else{
            holder.completeCheckMark.setVisibility(View.GONE);
        }
        holder.description.setText(course.getIntroduction());
        if(course.isExpanded()){
            holder.expandableArea.setVisibility(View.VISIBLE);
        }else{
            holder.expandableArea.setVisibility(View.GONE);
        }
        holder.adapterLayout.setOnClickListener(view -> {
            course.clicked();
            notifyItemChanged(position);
        });
        holder.play.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, CourseActivity.class);
            intent.putExtra("id", course.getId());
            intent.putExtra("position", position);
            this.resultOpen.launch(intent);
        });
        DifficultyService difficultyService = new DifficultyService(this.context);
        holder.difficulty.setText(difficultyService.get(course.getDifficulty()));
    }

    public void updateCourseList(List<Course> courses, int updatedIndex){
        this.courses.get(updatedIndex).setCompleted(courses.get(updatedIndex).isCompleted());
    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public static class CoursesHolder extends RecyclerView.ViewHolder {

        TextView title, subtitle, description, difficulty;
        FloatingActionButton play;
        LinearLayout expandableArea, adapterLayout;
        ImageView completeCheckMark;

        public CoursesHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.adapter_title);
            this.subtitle = itemView.findViewById(R.id.adapter_subtitle);
            this.description = itemView.findViewById(R.id.adapter_description);
            this.difficulty = itemView.findViewById(R.id.adapter_text_difficulty);
            this.play = itemView.findViewById(R.id.adapter_btn_play);
            this.expandableArea = itemView.findViewById(R.id.adapter_ExpandableArea);
            this.completeCheckMark = itemView.findViewById(R.id.adapter_complete_checkMark);
            this.adapterLayout = itemView.findViewById(R.id.adapter_layout);
        }
    }
}
