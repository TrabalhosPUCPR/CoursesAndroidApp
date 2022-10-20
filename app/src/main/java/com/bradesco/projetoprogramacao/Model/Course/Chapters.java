package com.bradesco.projetoprogramacao.Model.Course;

import java.util.ArrayList;

public class Chapters {
    private int id;
    private String title, description;
    private ArrayList<Page> pages;

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
}
