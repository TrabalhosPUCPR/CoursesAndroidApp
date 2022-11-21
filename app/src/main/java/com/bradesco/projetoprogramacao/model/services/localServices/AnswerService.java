package com.bradesco.projetoprogramacao.model.services.localServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.model.course.Answers;

import java.util.ArrayList;

public class AnswerService extends Service<Answers>{
    public AnswerService(@Nullable Context context) {
        super(context, "answers", new String[]{"id", "desc", "questionId"}, new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT NOT NULL", "INTEGER NOT NULL"});
    }

    @Override
    public boolean add(Answers answer) {
        ContentValues values = new ContentValues();
        values.put(this.columns[1], answer.getText());
        values.put(this.columns[2], answer.getQuestionId());
        answer.setId((int) this.insert(values));
        return answer.getId() > 0;
    }

    @Override
    public boolean edit(Answers answer, int id) {
        ContentValues values = new ContentValues();
        values.put(this.columns[1], answer.getText());
        return this.update(values, id);
    }

    @Override
    public Answers get(int id) {
        Cursor c = getReadableDatabase().rawQuery("SELECT " + this.columns[1] + " FROM " + this.TABLE_NAME + " WHERE " + this.columns[0] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            Answers answers = new Answers(c.getInt(2), c.getString(1));
            answers.setId(c.getInt(0));
            c.close();
            return answers;
        }
        c.close();
        return null;
    }

    public ArrayList<Answers> getAllWithQuestionId(int id){
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[2] + "=?", new String[]{String.valueOf(id)});
        ArrayList<Answers> list = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Answers answers = new Answers(c.getInt(2), c.getString(1));
                answers.setId(c.getInt(0));
                list.add(answers);
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
