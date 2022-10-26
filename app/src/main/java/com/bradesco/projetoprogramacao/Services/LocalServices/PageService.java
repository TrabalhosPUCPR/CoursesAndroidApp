package com.bradesco.projetoprogramacao.Services.LocalServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.Model.Course.Page;

import java.util.ArrayList;

public class PageService extends Service<Page> {

    public PageService(@Nullable Context context) {
        super(context, "pages",
                new String[]{"id", "chapterId", "text"},
                new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER", "TEXT NOT NULL"});
    }

    private ContentValues getValues(Page page){
        ContentValues values = new ContentValues();
        values.put(this.columns[1], page.getChapterId());
        values.put(this.columns[2], page.getParagraphs());
        return values;
    }

    @Override
    public boolean add(Page page) {
        ContentValues values = getValues(page);
        page.setId((int) this.insert(values));
        return page.getId() > 0;
    }

    @Override
    public boolean edit(Page page, int id) {
        return this.update(getValues(page), id);
    }

    @Override
    public Page get(int id) {
        Cursor c = getReadableDb().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[0] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            Page page = new Page(c.getString(2));
            page.setChapterId(c.getInt(1));
            page.setId(c.getInt(0));
            return page;
        }
        c.close();
        return null;
    }

    public ArrayList<Page> getAllWithChapterId(int chapterId){
        ArrayList<Page> list = new ArrayList<>();
        Cursor c = getReadableDb().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[1] + "=?", new String[]{String.valueOf(chapterId)});
        if(c.moveToFirst()){
            do{
                Page page = new Page(c.getString(2));
                page.setChapterId(c.getInt(1));
                page.setId(c.getInt(0));
                list.add(page);
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
