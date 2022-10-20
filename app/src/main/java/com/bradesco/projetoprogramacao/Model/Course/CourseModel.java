package com.bradesco.projetoprogramacao.Model.Course;

import java.util.ArrayList;

public class CourseModel {
    private int id;
    private String title, subTitle, introduction;
    private ArrayList<Chapters> chapters;
    private int difficulty;
    private boolean completed, expanded;

    public CourseModel(){}
    public CourseModel(String title, String subTitle, String introduction, int difficulty, boolean completed) {
        this.title = title;
        this.subTitle = subTitle;
        this.difficulty = difficulty;
        this.chapters = new ArrayList<>();
        this.completed = completed;
        this.introduction = introduction;
        this.expanded = false;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public ArrayList<Chapters> getChapters() {
        return chapters;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setChapters(ArrayList<Chapters> chapters) {
        this.chapters = chapters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void clicked(){
        this.expanded = !this.expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void addChapter(Chapters chapter){
        this.chapters.add(chapter);
    }
}
