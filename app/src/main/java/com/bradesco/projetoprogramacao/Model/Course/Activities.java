package com.bradesco.projetoprogramacao.model.course;

public class Activities{
    private int id, courseId, difficulty;
    private Page page;
    private String name, expectedOutput, desc;
    private boolean completed, expanded;

    public Activities(int id, String name, String desc, String expectedOutput, int difficulty, Page page, int courseId) {
        this.id = id;
        this.page = page;
        this.expectedOutput = expectedOutput;
        this.name = name;
        this.courseId = courseId;
        this.desc = desc;
        this.difficulty = difficulty;
        this.completed = false;
        this.expanded = false;
    }

    public Activities(String name, String desc, String expectedOutput, int difficulty, Page page) {
        this.difficulty = difficulty;
        this.page = page;
        this.name = name;
        this.expectedOutput = expectedOutput;
        this.desc = desc;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expand() {
        this.expanded = !this.expanded;
    }
}
