package com.bradesco.projetoprogramacao.Model.Course;

import java.util.ArrayList;

public class Chapters {
    private int id, courseId;
    private String title, description;
    private ArrayList<Page> pages;

    /**
     * Constructor of a chapter object, id and courseId should be defined only after inserting it on
     * the database and pages are added in with addPage()
     * @param title title of the chapter
     * @param description description of the chapter
     */
    public Chapters(String title, String description) {
        this.title = title;
        this.description = description;
        this.pages = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addPage(Page page){
        this.pages.add(page);
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
