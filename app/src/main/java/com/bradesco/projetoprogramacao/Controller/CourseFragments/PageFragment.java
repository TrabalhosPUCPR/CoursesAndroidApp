package com.bradesco.projetoprogramacao.Controller.CourseFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.Model.Course.Page;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentPageBinding;

public class PageFragment extends Fragment {

    private FragmentPageBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        Page page = CourseActivity.course.getChapters().get(CourseActivity.currentChapter).getPages().get(CourseActivity.currentPage);

        TextView chapterTitle = root.findViewById(R.id.page_chapterTitle);
        TextView courseTitle = root.findViewById(R.id.page_CourseTitle);
        LinearLayout contentArea = root.findViewById(R.id.contentArea);

        TextView pageNumber = root.findViewById(R.id.pageNumber);
        TextView chapterNumber = root.findViewById(R.id.chapterNumber);
        pageNumber.setText(String.valueOf(CourseActivity.currentPage + 1));
        chapterNumber.setText(String.valueOf(CourseActivity.currentChapter + 1));

        LinearLayout ch = root.findViewById(R.id.showBigChapterNumberLayout);
        if(CourseActivity.currentPage == 0){
            ch.setVisibility(View.VISIBLE);
            TextView number = root.findViewById(R.id.bigChapterNumber);
            number.setText(String.valueOf(CourseActivity.currentChapter + 1));
        }else{
            ch.setVisibility(View.GONE);
        }

        chapterTitle.setText(CourseActivity.course.getChapters().get(CourseActivity.currentChapter).getTitle());
        courseTitle.setText(CourseActivity.course.getTitle());

        TextView text = new TextView(getContext());

        text.setText(page.getParagraphs());
        contentArea.addView(text);

        CourseActivity.bottomAppBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.bottomMenuBar_foward){
                onNextClicked(root);
            }else if(item.getItemId() == R.id.bottomMenuBar_back){
                onReturnClicked(root);
            }else{
                return false;
            }
            return true;
        });

        return root;
    }

    private void onNextClicked(View root){
        CourseActivity.currentPage++;
        if(CourseActivity.course.getChapters().get(CourseActivity.currentChapter).getPages().size() == CourseActivity.currentPage){
            CourseActivity.currentChapter++;
            CourseActivity.currentPage = 0;
            if(CourseActivity.course.getChapters().size() == CourseActivity.currentChapter){
                // end course
                CourseActivity.currentChapter = 0;
                if(CourseActivity.course.getEndingQuestions().isEmpty()){
                    Navigation.findNavController(root).navigate(R.id.action_pageFragment_to_courseEndFragment);
                }else{
                    Navigation.findNavController(root).navigate(R.id.action_pageFragment_to_questionFragment);
                }
                return;
            }
        }
        Navigation.findNavController(root).navigate(R.id.action_pageFragment_self);
    }

    private void onReturnClicked(View root){
        if(CourseActivity.currentPage == 0){
            if(CourseActivity.currentChapter == 0){
                Navigation.findNavController(root).navigate(R.id.action_pageFragment_to_coverFragment2, null, null, null);
                return;
            }
            CourseActivity.currentChapter--;
            CourseActivity.currentPage = CourseActivity.course.getChapters().get(CourseActivity.currentChapter).getPages().size() - 1;
        }else{
            CourseActivity.currentPage--;
        }
        Navigation.findNavController(root).navigate(R.id.action_pageFragment_self);
    }
}