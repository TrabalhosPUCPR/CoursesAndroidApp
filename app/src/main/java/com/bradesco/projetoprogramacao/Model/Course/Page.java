package com.bradesco.projetoprogramacao.Model.Course;

public class Page {
    private int id;
    private String paragraphs;

    public Page() {}

    public Page(String paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(String paragraphs) {
        this.paragraphs = paragraphs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
