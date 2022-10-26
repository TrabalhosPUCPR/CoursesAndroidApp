package com.bradesco.projetoprogramacao.Services.LocalServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.Model.Course.Chapters;
import com.bradesco.projetoprogramacao.Model.Course.Page;

import java.util.ArrayList;

public class ChaptersService extends Service<Chapters> {

    public ChaptersService(@Nullable Context context) {
        super(context, "chapter",
                new String[]{"id", "name", "desc", "courseId"},
                new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT NOT NULL", "TEXT NOT NULL", "INTEGER NOT NULL"});
    }

    private ContentValues getValues(Chapters chapters){
        ContentValues values = new ContentValues();
        values.put(this.columns[1], chapters.getTitle());
        values.put(this.columns[2], chapters.getDescription());
        values.put(this.columns[3], chapters.getCourseId());
        return values;
    }

    @Override
    public boolean add(Chapters chapter) {
        chapter.setId((int) this.insert(getValues(chapter)));
        PageService pageService = new PageService(context);
        for(Page page : chapter.getPages()){
            page.setChapterId(chapter.getId());
            pageService.add(page);
        }
        return chapter.getId() > 0;
    }

    @Override
    public boolean remove(int id) {
        super.remove(id);
        PageService pageService = new PageService(context);
        for(Page page: pageService.getAllWithChapterId(id)){
            pageService.remove(page.getId());
        }
        return true;
    }

    @Override
    public boolean edit(Chapters chapter, int id) {
        return this.update(getValues(chapter), id);
    }

    @Override
    public Chapters get(int id) {
        Cursor c = this.getReadableDb().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[0] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            return cursorToChapter(c);
        }
        c.close();
        return null;
    }

    public ArrayList<Chapters> getAllWithCourseId(int id){
        ArrayList<Chapters> list = new ArrayList<>();
        Cursor c = getReadableDb().rawQuery("SELECT * FROM " + this.TABLE_NAME + " WHERE " + this.columns[3] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            do{
                list.add(cursorToChapter(c));
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }

    private Chapters cursorToChapter(Cursor c){
        Chapters chapters = new Chapters(c.getString(1), c.getString(2));
        chapters.setCourseId(c.getInt(2));
        chapters.setId(c.getInt(0));
        PageService pageService = new PageService(context);
        chapters.setPages(pageService.getAllWithChapterId(c.getInt(0)));
        return chapters;
    }
}
