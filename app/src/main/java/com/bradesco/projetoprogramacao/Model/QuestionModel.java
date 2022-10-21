package com.bradesco.projetoprogramacao.Model;

import com.bradesco.projetoprogramacao.Model.Course.Page;

import java.util.ArrayList;

public class QuestionModel {
    private int id;
    private Page questionArea;
    private ArrayList<String> answers;
    private int correctAnswerIndex;

    public QuestionModel(Page questionArea) {
        this.questionArea = questionArea;
        this.answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Page getQuestionArea() {
        return questionArea;
    }

    public void setQuestionArea(Page questionArea) {
        this.questionArea = questionArea;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getAnswerWithLabel(int index){
        return ((char)(97 + index)) + ". " + answers.get(index);
    }

    public ArrayList<String> getAnswersWithLabel(){
        ArrayList<String> answers = new ArrayList<>();
        for(int i = 0; i < this.answers.size(); i++){
            answers.add(((char)(97 + i)) + ". " + this.answers.get(i));
        }
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answer){
        this.answers.add(answer);
    }

    public void addCorrectAnswer(String answer){
        this.answers.add(answer);
        correctAnswerIndex = this.answers.size()-1;
    }

    public void setCorrectAnswerIndex(int answerIndex){
        this.correctAnswerIndex = answerIndex;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public boolean answer(int answerIndex){
        return answerIndex == this.correctAnswerIndex;
    }

    public String getCorrectAnswer(){
        return this.answers.get(correctAnswerIndex);
    }
}
