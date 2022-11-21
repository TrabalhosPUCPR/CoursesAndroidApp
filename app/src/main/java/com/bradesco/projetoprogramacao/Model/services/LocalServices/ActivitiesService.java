package com.bradesco.projetoprogramacao.model.services.localServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.model.course.Activities;

public class ActivitiesService extends Service<Activities> {

    public ActivitiesService(@Nullable Context context) {
        super(context, "activities",
                new String[]{"id", "name", "description", "answer", "difficulty", "pageId", "courseId", "completed"},
                new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT NOT NULL", "TEXT NOT NULL", "TEXT NOT NULL", "INTEGER NOT NULL", "INTEGER NOT NULL", "INTEGER NOT NULL", "INTEGER NOT NULL"});
    }

    @Override
    public boolean add(Activities object) {
        PageService pageService = new PageService(context);
        pageService.add(object.getPage());
        ContentValues values = new ContentValues();
        values.put(this.columns[1], object.getName());
        values.put(this.columns[2], object.getDesc());
        values.put(this.columns[3], object.getExpectedOutput());
        values.put(this.columns[4], object.getDifficulty());
        values.put(this.columns[5], object.getPage().getId());
        values.put(this.columns[6], object.getCourseId());
        values.put(this.columns[7], object.isCompleted());
        object.setId((int) this.insert(values));
        return object.getId() > 0;
    }

    @Override
    public boolean edit(Activities object, int id) {
        ContentValues values = new ContentValues();
        values.put(this.columns[1], object.getName());
        values.put(this.columns[2], object.getDesc());
        values.put(this.columns[3], object.getExpectedOutput());
        values.put(this.columns[4], object.getDifficulty());
        values.put(this.columns[7], object.isCompleted());
        return this.update(values, id);
    }

    @Override
    public Activities get(int id) {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[0] + "=" + id, null);
        if(c.moveToFirst()){
            PageService pageService = new PageService(context);
            Activities a = new Activities(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4), pageService.get(c.getInt(6)), c.getInt(6));
            a.setCompleted(c.getInt(7) == 1);
            c.close();
            return a;
        }
        c.close();
        return null;
    }
}
