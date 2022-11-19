package com.bradesco.projetoprogramacao.controller.courseFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.databinding.FragmentQuestionBinding;
import com.bradesco.projetoprogramacao.model.course.Question;

import java.util.ArrayList;

public class questionFragment extends Fragment{

    private Button answerButton;
    private boolean answered;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.bradesco.projetoprogramacao.databinding.FragmentQuestionBinding binding = FragmentQuestionBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        TextView text = root.findViewById(R.id.ChapterNumber);
        text.setText(String.format(getResources().getString(R.string.QuestionN), CourseActivity.currentQuestion + 1));

        LinearLayout contentArea = root.findViewById(R.id.chapterTitleContainer);
        contentArea.setVisibility(View.GONE);
        TextView chPhNumber = root.findViewById(R.id.ChPgNumber);
        chPhNumber.setVisibility(View.GONE);

        contentArea = root.findViewById(R.id.contentArea);
        Question question = CourseActivity.getQuestion();
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
                CourseActivity.nextQuestion(root);
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

    private void answer(int answer, Question question, View root, ArrayList<RadioButton> radioButtons, RadioGroup group){
        TextView check = root.findViewById(R.id.answerResponseText);
        check.setVisibility(View.VISIBLE);
        if(question.answer(answer)){
            check.setText(getActivity().getResources().getString(R.string.congratulationsMessage));
        }else {
            check.setText(String.format(getActivity().getResources().getString(R.string.incorrectMessage), question.getCorrectAnswer()));
        }
        for(RadioButton rb : radioButtons){
            rb.setEnabled(false);
        }
        answered = true;
        group.setClickable(false);
        answerButton.setText(getActivity().getResources().getString(R.string.Continue));
        radioButtons.get(answer).setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
        radioButtons.get(question.getCorrectAnswerIndex()).setBackgroundColor(getResources().getColor(R.color.correctAnswer));
    }
}