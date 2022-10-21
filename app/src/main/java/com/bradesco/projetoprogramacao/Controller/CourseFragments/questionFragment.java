package com.bradesco.projetoprogramacao.Controller.CourseFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.Model.QuestionModel;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentQuestionBinding;

import java.util.ArrayList;

public class questionFragment extends Fragment{

    private FragmentQuestionBinding binding;
    private Button answerButton;
    private boolean answered;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        TextView text = root.findViewById(R.id.pageType);
        text.setText(R.string.Question);
        text = root.findViewById(R.id.bigChapterNumber);
        text.setText(String.valueOf(CourseActivity.currentQuestion + 1));

        LinearLayout contentArea = root.findViewById(R.id.chapterTitleContainer);
        contentArea.setVisibility(View.GONE);
        contentArea = root.findViewById(R.id.chAndPgLabel_container);
        contentArea.setVisibility(View.GONE);

        contentArea = root.findViewById(R.id.contentArea);
        QuestionModel question = CourseActivity.course.getEndingQuestions().get(CourseActivity.currentQuestion);
        TextView content = new TextView(getContext());
        content.setText(question.getQuestionArea().getParagraphs());
        contentArea.addView(content);

        RadioGroup radioGroup = root.findViewById(R.id.answersRadioGroup);
        ArrayList<RadioButton> radioButtons = new ArrayList<>();
        for(String a : question.getAnswersWithLabel()){
            RadioButton button = new RadioButton(getContext());
            button.setText(a);
            radioButtons.add(button);
            radioGroup.addView(button);
        }
        this.answerButton = root.findViewById(R.id.questions_answer_btn);

        this.answerButton.setOnClickListener(view -> {
            if(answered){
                if (CourseActivity.currentQuestion == CourseActivity.course.getEndingQuestions().size() - 1){
                    Navigation.findNavController(root).navigate(R.id.action_questionFragment_to_courseEndFragment);
                }else{
                    CourseActivity.currentQuestion++;
                    Navigation.findNavController(root).navigate(R.id.action_questionFragment_self);
                }
                return;
            }
            for(int i = 0; i < radioButtons.size(); i++){
                if(radioButtons.get(i).getId() == radioGroup.getCheckedRadioButtonId()){
                    answer(i, question, root, radioButtons, radioGroup);
                    return;
                }
            }
        });
        CourseActivity.bottomAppBar.performHide();
        return root;
    }

    private void answer(int answer, QuestionModel questionModel, View root, ArrayList<RadioButton> radioButtons, RadioGroup group){
        TextView check = root.findViewById(R.id.answerResponseText);
        check.setVisibility(View.VISIBLE);
        if(questionModel.answer(answer)){
            check.setText(getActivity().getResources().getString(R.string.congratulationsMessage));
        }else {
            check.setText(String.format(getActivity().getResources().getString(R.string.incorrectMessage), questionModel.getCorrectAnswer()));
        }
        for(RadioButton rb : radioButtons){
            rb.setEnabled(false);
        }
        answered = true;
        group.setClickable(false);
        answerButton.setText(getActivity().getResources().getString(R.string.Continue));
        radioButtons.get(answer).setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
        radioButtons.get(questionModel.getCorrectAnswerIndex()).setBackgroundColor(getResources().getColor(R.color.correctAnswer));
    }
}