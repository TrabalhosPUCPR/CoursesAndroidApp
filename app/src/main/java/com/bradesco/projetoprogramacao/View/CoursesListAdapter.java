package com.bradesco.projetoprogramacao.View;

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

import com.bradesco.projetoprogramacao.Controller.CourseFragments.CourseActivity;
import com.bradesco.projetoprogramacao.Model.Course.CourseListManager;
import com.bradesco.projetoprogramacao.Model.Course.Course;
import com.bradesco.projetoprogramacao.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CoursesListAdapter extends RecyclerView.Adapter<CoursesListAdapter.CoursesHolder> {

    Context context;
    List<Course> courses;
    ActivityResultLauncher<Intent> resultOpen;

    public CoursesListAdapter(List<Course> courses, Context context, ActivityResultLauncher<Intent> resultOpen) {
        this.context = context;
        this.courses = courses;
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
            holder.completeCheckMark.setImageResource(R.drawable.ic_action_check);
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
            intent.putExtra("index", position);
            this.resultOpen.launch(intent);
        });
        holder.difficulty.setText(CourseListManager.getInstance().getDifficultyName(position));
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
