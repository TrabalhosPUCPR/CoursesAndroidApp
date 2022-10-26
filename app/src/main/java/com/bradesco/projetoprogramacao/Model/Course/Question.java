package com.bradesco.projetoprogramacao.Model.Course;

import java.util.ArrayList;

public class Question {
    private int id, courseId;
    private Page questionArea;
    private ArrayList<Answers> answers;
    private int correctAnswerIndex;

    public Question(Page questionArea) {
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

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public ArrayList<String> getAnswersWithLabel(){
        ArrayList<String> answers = new ArrayList<>();
        for(int i = 0; i < this.answers.size(); i++){
            answers.add(((char)(97 + i)) + ". " + this.answers.get(i).getText());
        }
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answer){
        this.answers.add(new Answers(this.id, answer));
    }

    public void addCorrectAnswer(String answer){
        this.answers.add(new Answers(this.id, answer));
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
        return this.answers.get(correctAnswerIndex).getText();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
