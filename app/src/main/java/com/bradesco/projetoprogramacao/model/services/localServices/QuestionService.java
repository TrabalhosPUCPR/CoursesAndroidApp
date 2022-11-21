package com.bradesco.projetoprogramacao.model.services.localServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.model.course.Answers;
import com.bradesco.projetoprogramacao.model.course.Question;

import java.util.ArrayList;

public class QuestionService extends Service<Question> {

    public QuestionService(@Nullable Context context) {
        super(context, "questions",
                new String[]{"id", "correctIndex", "pageId", "courseId"},
                new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER NOT NULL", "INTEGER NOT NULL", "INTEGER NOT NULL"});
    }

    private ContentValues getValues(Question question){
        ContentValues values = new ContentValues();
        values.put(this.columns[1], question.getCorrectAnswerIndex());
        values.put(this.columns[2], question.getQuestionArea().getId());
        values.put(this.columns[3], question.getCourseId());
        return values;
    }

    @Override
    public boolean add(Question question) {
        PageService pageService = new PageService(this.context);
        pageService.add(question.getQuestionArea());
        long id = this.insert(getValues(question));
        question.setId((int) id);
        AnswerService answerService = new AnswerService(this.context);
        for(Answers answer : question.getAnswers()){
            answer.setQuestionId(question.getId());
            answerService.add(answer);
        }
        return true;
    }

    @Override
    public boolean edit(Question object, int id) {
        return false;
    }

    @Override
    public Question get(int id) {
        return null;
    }

    @Override
    public boolean remove(int id) {
        super.remove(id);
        PageService pageService = new PageService(this.context);
        pageService.remove(get(id).getQuestionArea().getId());
        return true;
    }

    public ArrayList<Question> getAllWithCourseId(int id){
        ArrayList<Question> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[3] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            do{
                PageService pageService = new PageService(this.context);
                Question question = new Question(pageService.get(c.getInt(2)));
                question.setCorrectAnswerIndex(c.getInt(1));
                AnswerService answerService = new AnswerService(this.context);
                question.setAnswers(answerService.getAllWithQuestionId(c.getInt(0)));
                list.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
