package com.bradesco.projetoprogramacao.Controller.CourseFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentCoverBinding;

public class CoverFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.bradesco.projetoprogramacao.databinding.FragmentCoverBinding binding = FragmentCoverBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        TextView title = root.findViewById(R.id.chapter_title);
        TextView subtitle = root.findViewById(R.id.chapter_subtitle);
        title.setText(CourseActivity.course.getTitle());
        subtitle.setText(CourseActivity.course.getSubTitle());
        CourseActivity.bottomAppBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.bottomMenuBar_foward){
                onNextClicked(root);
            }else{
                onReturnClicked();
                return false;
            }
            return true;
        });

        return root;
    }

    private void onNextClicked(View root){
        Navigation.findNavController(root).navigate(R.id.action_coverFragment_to_pageFragment);
    }

    private void onReturnClicked(){
        getActivity().finish();
    }
}