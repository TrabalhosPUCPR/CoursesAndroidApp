package com.bradesco.projetoprogramacao.Model.Course;

import com.bradesco.projetoprogramacao.Model.Question;

import java.util.ArrayList;

public class Course {
    private int id;
    private String title, subTitle, introduction;
    private ArrayList<Chapters> chapters;
    private ArrayList<Question> endingQuestions;
    private int difficulty;
    private boolean completed, expanded;

    public Course(){
        this.chapters = new ArrayList<>();
        this.endingQuestions = new ArrayList<>();
    }
    public Course(String title, String subTitle, String introduction, int difficulty) {
        this.title = title;
        this.subTitle = subTitle;
        this.difficulty = difficulty;
        this.chapters = new ArrayList<>();
        this.endingQuestions = new ArrayList<>();
        this.completed = false;
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

    public void addEndingQuestion(Question question){
        this.endingQuestions.add(question);
    }

    public ArrayList<Question> getEndingQuestions() {
        return endingQuestions;
    }

    public void setEndingQuestions(ArrayList<Question> endingQuestions) {
        this.endingQuestions = endingQuestions;
    }

    // DEFAULT COURSES
    // DEFAULT PAGES
    public static Course createDefault_DataTypes(){
        Course dataTypes = new Course("Data Types", "Python basics", "This course we will take a look at the available data types in Python", 0);
        ArrayList<Page> pages = new ArrayList<>();
        ArrayList<Chapters> chapters = new ArrayList<>();

        // add chapters
        chapters.add(new Chapters("Numbers", "Types of different numbers"));
        chapters.add(new Chapters("Letters", "Types of different letters"));

        // add pages to first chapter
        pages.add(new Page("In python, there are Integers, double, longs, etc"));
        pages.add(new Page("Very cool numbers in python"));
        chapters.get(0).setPages(pages);

        // add pages to second chapter
        pages = new ArrayList<>();
        pages.add(new Page("In python, there are Strings and characters"));
        pages.add(new Page("Very cool letters in python"));
        chapters.get(1).setPages(pages);

        // set chapters to course
        dataTypes.setChapters(chapters);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(new Page("The data '1.8' is of which type?")));
        questions.get(0).addAnswer("Integer");
        questions.get(0).addAnswer("String");
        questions.get(0).addCorrectAnswer("Float");
        questions.get(0).addAnswer("Boolean");

        questions.add(new Question(new Page("The data 'potato' is of which type?")));
        questions.get(1).addAnswer("Integer");
        questions.get(1).addCorrectAnswer("String");
        questions.get(1).addAnswer("Float");
        questions.get(1).addAnswer("Boolean");

        dataTypes.setEndingQuestions(questions);
        return dataTypes;
    }
}
