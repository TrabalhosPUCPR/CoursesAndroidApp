package com.bradesco.projetoprogramacao.Model.Course;

public class Page {
    private int id, chapterId;
    private String paragraphs;

    public Page() {}

    /**
     * Constructor of the Page object, id and courseId should only be defined when adding it to database
     * @param paragraphs WIP - Text
     */
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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
