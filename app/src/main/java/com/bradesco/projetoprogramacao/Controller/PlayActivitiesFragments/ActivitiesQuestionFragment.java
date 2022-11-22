package com.bradesco.projetoprogramacao.controller.playActivitiesFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.ActivityActivitiesQuestionFragmentBinding;
import com.bradesco.projetoprogramacao.model.course.Activities;
import com.bradesco.projetoprogramacao.model.services.localServices.ActivitiesService;
import com.bradesco.projetoprogramacao.model.services.localServices.CourseService;

public class ActivitiesQuestionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActivityActivitiesQuestionFragmentBinding binding = ActivityActivitiesQuestionFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ActivitiesService service = new ActivitiesService(this.getContext());
        CourseService courseService = new CourseService(this.getContext());
        binding.setActivity(service.get(PlayActivities.activityId));

        binding.setCourseName(courseService.get(binding.getActivity().getCourseId()).getTitle());

        binding.questionTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.setPage(binding.getActivity().getPage());

        return root;
    }
}