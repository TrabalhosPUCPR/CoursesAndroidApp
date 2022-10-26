package com.bradesco.projetoprogramacao.Controller.CourseFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentCourseEndBinding;

public class CourseEndFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCourseEndBinding binding = FragmentCourseEndBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        TextView title = root.findViewById(R.id.end_course_title);
        Button button = root.findViewById(R.id.end_course_return_btn);

        title.setText(CourseActivity.course.getTitle());
        button.setOnClickListener(view -> getActivity().finish());
        CourseActivity.bottomAppBar.setOnMenuItemClickListener(null);

        Intent intent = new Intent();
        intent.putExtra("id", CourseActivity.courseId);
        intent.putExtra("position", CourseActivity.indexPosition);
        getActivity().setResult(Activity.RESULT_OK, intent);

        CourseActivity.bottomAppBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.bottomMenuBar_back){
                onReturnClicked(root);
            }else{
                getActivity().finish();
                return false;
            }
            return true;
        });
        return root;
    }

    private void onReturnClicked(View root){
        CourseActivity.currentChapter = CourseActivity.course.getChapters().size() - 1;
        CourseActivity.currentPage = CourseActivity.course.getChapters().get(CourseActivity.currentChapter).getPages().size() - 1;
        Navigation.findNavController(root).navigate(R.id.action_courseEndFragment_to_pageFragment);
    }
}