package com.bradesco.projetoprogramacao.controller.courseFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentPageBinding;
import com.bradesco.projetoprogramacao.model.course.Page;

public class PageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.bradesco.projetoprogramacao.databinding.FragmentPageBinding binding = FragmentPageBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        Page page = CourseActivity.getPage();

        TextView chapterTitle = root.findViewById(R.id.page_chapterTitle);
        LinearLayout contentArea = root.findViewById(R.id.contentArea);

        TextView chPgNumber = root.findViewById(R.id.ChPgNumber);
        chPgNumber.setText(String.format(getResources().getString(R.string.ChPgNumber), CourseActivity.currentChapter + 1, CourseActivity.currentPage + 1));

        TextView chapterNumber = root.findViewById(R.id.ChapterNumber);
        if(CourseActivity.currentPage == 0){
            chapterNumber.setVisibility(View.VISIBLE);
            chapterNumber.setText(String.format(getResources().getString(R.string.ChapterN), CourseActivity.currentChapter + 1));
        }else{
            chapterNumber.setVisibility(View.GONE);
        }

        chapterTitle.setText(CourseActivity.getChapter().getTitle());

        TextView text = new TextView(getContext());

        text.setText(page.getParagraphs());
        contentArea.addView(text);

        CourseActivity.bottomAppBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.bottomMenuBar_foward){
                nextPage(root);
            }else if(item.getItemId() == R.id.bottomMenuBar_back){
                previousPage(root);
            }else{
                return false;
            }
            return true;
        });

        return root;
    }

    private void nextPage(View root){
        CourseActivity.currentPage++;
        if(CourseActivity.getChapter().getPages().size() == CourseActivity.currentPage) {
            if (!CourseActivity.nextChapter()) {
                if (!CourseActivity.course.getEndingQuestions().isEmpty()) {
                    Navigation.findNavController(root).navigate(R.id.action_pageFragment_to_questionFragment);
                } else {
                    CourseActivity.launchActivities(getContext());
                    Navigation.findNavController(root).navigate(R.id.action_pageFragment_to_courseEndFragment);
                }
                return;
            }
        }
        Navigation.findNavController(root).navigate(R.id.action_pageFragment_self);
    }

    private void previousPage(View root){
        if(CourseActivity.currentPage == 0){
            if(!CourseActivity.previousChapter()){
                Navigation.findNavController(root).navigate(R.id.action_pageFragment_to_coverFragment2, null, null, null);
                return;
            }
            CourseActivity.currentPage = CourseActivity.getChapter().getPages().size();
        }
        CourseActivity.currentPage--;
        Navigation.findNavController(root).navigate(R.id.action_pageFragment_self);
    }
}