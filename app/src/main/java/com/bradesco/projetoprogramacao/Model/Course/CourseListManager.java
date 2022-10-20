package com.bradesco.projetoprogramacao.Model.Course;

import android.content.Context;

import com.bradesco.projetoprogramacao.Model.Database.CoursesDAO;
import com.bradesco.projetoprogramacao.Model.Database.DAO;

import java.util.ArrayList;
import java.util.List;

public class CourseListManager implements DAO<CourseModel> {

    private static final CourseListManager instance = new CourseListManager();
    private List<CourseModel> courses = new ArrayList<>();
    private CoursesDAO dao;

    public static synchronized CourseListManager getInstance(){
        return instance;
    }

    private CourseListManager(){}

    public static ArrayList<CourseModel> createDefaultCourses(){
        ArrayList<CourseModel> list = new ArrayList<>();
        ArrayList<Page> pages = new ArrayList<>();
        ArrayList<Chapters> chapters = new ArrayList<>();

        list.add(new CourseModel("Data Types", "Python basics", "This course we will take a look at the available data types in Python", 0, false));

        chapters.add(new Chapters("Numbers", "Types of different numbers"));
        chapters.add(new Chapters("Letters", "Types of different letters"));

        pages.add(new Page("In python, there are Integers, double, longs, etc"));
        pages.add(new Page("Very cool numbers in python"));
        chapters.get(0).setPages(pages);

        pages = new ArrayList<>();
        pages.add(new Page("In python, there are Strings and characters"));
        pages.add(new Page("Very cool letters in python"));
        chapters.get(1).setPages(pages);
        list.get(0).setChapters(chapters);
        return list;
    }

    public void loadDatabase(Context context){
        this.dao = new CoursesDAO(context);
        List<CourseModel> list = this.dao.getList();
        if(list != null){
            this.courses = list;
        }
    }

    @Override
    public boolean add(CourseModel object) {
        if(this.dao.add(object)){
            this.courses.add(object);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        if(this.dao.remove(this.courses.get(id).getId(), true)){
            this.courses.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean clearAll() {
        if(this.dao.clearAll()){
            this.courses = new ArrayList<>();
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(CourseModel object, int id) {
        if(this.dao.edit(object, this.courses.get(id).getId())){
            this.courses.set(id, object);
            return true;
        }
        return false;
    }

    @Override
    public List<CourseModel> getList() {
        return this.courses;
    }

    @Override
    public CourseModel get(int id) {
        return this.courses.get(id);
    }

    public ArrayList<CourseModel> getCompletedCourses(){
        ArrayList<CourseModel> completed = new ArrayList<>();
        for(CourseModel cm : this.courses){
            if(cm.isCompleted()){
                completed.add(cm);
            }
        }
        return completed;
    }

    public String getDifficultyName(int id){
        return this.dao.getDifficultiesName(this.courses.get(id).getId());
    }
}
