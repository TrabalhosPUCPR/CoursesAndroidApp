package com.bradesco.projetoprogramacao.controller.playActivityFragments;

import static com.bradesco.projetoprogramacao.controller.playActivityFragments.PlayActivities.type;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentActivityQuestionBinding;
import com.bradesco.projetoprogramacao.model.services.localServices.ActivitiesService;
import com.bradesco.projetoprogramacao.model.services.localServices.CourseService;

public class PlayQuestionFragment extends Fragment {

    private FragmentActivityQuestionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentActivityQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ActivitiesService service = new ActivitiesService(this.getContext());
        CourseService courseService = new CourseService(this.getContext());

        binding.questionTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.setActivity(service.get(PlayActivities.activityId));
        binding.setCourseName(courseService.get(binding.getActivity().getCourseId()).getTitle());
        service.close();
        courseService.close();

        if (type == 1){
            binding.txtViewTimetopractice.setVisibility(View.GONE);
        }else if (type == 2){
            binding.txtViewTimetopractice.setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}