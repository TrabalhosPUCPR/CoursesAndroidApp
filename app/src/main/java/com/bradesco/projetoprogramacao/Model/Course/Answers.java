package com.bradesco.projetoprogramacao.Model.Course;

public class Answers {
    private int id, questionId;
    private String text;

    public Answers(int questionId, String text) {
        this.questionId = questionId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
