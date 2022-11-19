package com.bradesco.projetoprogramacao.model.services.LocalServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.model.course.Chapters;
import com.bradesco.projetoprogramacao.model.course.Course;
import com.bradesco.projetoprogramacao.model.course.Question;

import java.util.ArrayList;

public class CourseService extends Service<Course> {

    public CourseService(@Nullable Context context) {
        super(context, "courses",
                new String[]{"id", "title", "subtitle", "description", "completed", "difficulty"},
                new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT NOT NULL", "TEXT NOT NULL", "TEXT NOT NULL", "INTEGER NOT NULL", "INTEGER NOT NULL"});
    }

    @Override
    public boolean onServiceCreate(SQLiteDatabase db) {
        ArrayList<Course> courses = Course.getDefaultCourses();
        for(Course course : courses){
            this.add(course);
        }
        return true;
    }

    private ContentValues getValues(Course course){
        ContentValues values = new ContentValues();
        values.put(this.columns[1], course.getTitle());
        values.put(this.columns[2], course.getSubTitle());
        values.put(this.columns[3], course.getIntroduction());
        values.put(this.columns[4], course.isCompleted());
        values.put(this.columns[5], course.getDifficulty());
        return values;
    }

    @Override
    public boolean add(Course course) {
        ContentValues values = getValues(course);
        ChaptersService chaptersService = new ChaptersService(context);
        QuestionService questionService = new QuestionService(context);
        course.setId((int) this.insert(values));
        ArrayList<Chapters> chapters = course.getChapters();
        for(Chapters chapter : chapters){
            chapter.setCourseId(course.getId());
            chaptersService.add(chapter);
        }
        for(Question question : course.getEndingQuestions()){
            question.setCourseId(course.getId());
            questionService.add(question);
        }
        return course.getId() > 0;
    }

    @Override
    public boolean remove(int id) {
        super.remove(id);
        ChaptersService chaptersService = new ChaptersService(context);
        QuestionService questionService = new QuestionService(context);
        for(Chapters c : chaptersService.getAllWithCourseId(id)){
            chaptersService.remove(c.getId());
        }
        for(Question q : questionService.getAllWithCourseId(id)){
            questionService.remove(q.getId());
        }
        return true;
    }

    @Override
    public boolean edit(Course course, int id) {
        return this.update(getValues(course), id);
    }

    @Override
    public Course get(int id) {
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[0] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            ChaptersService chaptersService = new ChaptersService(context);
            QuestionService questionService = new QuestionService(context);
            Course course = new Course(c.getString(1), c.getString(2), c.getString(3), c.getInt(5));
            course.setCompleted(c.getInt(4) != 0);
            course.setChapters(chaptersService.getAllWithCourseId(id));
            course.setEndingQuestions(questionService.getAllWithCourseId(id));
            course.setId(id);
            c.close();
            return course;
        }
        c.close();
        return null;
    }

    public ArrayList<Course> getCompletedCourses(){
        Cursor c = getWritableDatabase().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[4] + "=1", null);
        ArrayList<Course> list = new ArrayList<>();
        if(c.moveToFirst()){
            do {
                ChaptersService chaptersService = new ChaptersService(context);
                QuestionService questionService = new QuestionService(context);
                Course course = new Course(c.getString(1), c.getString(2), c.getString(3), c.getInt(5));
                course.setCompleted(c.getInt(4) != 0);
                course.setChapters(chaptersService.getAllWithCourseId(c.getInt(0)));
                course.setEndingQuestions(questionService.getAllWithCourseId(c.getInt(0)));
                course.setId(c.getInt(0));
                list.add(course);
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
