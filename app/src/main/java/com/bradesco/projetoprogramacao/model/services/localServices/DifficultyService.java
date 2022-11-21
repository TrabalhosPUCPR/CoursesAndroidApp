package com.bradesco.projetoprogramacao.model.services.localServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DifficultyService extends Service<String> {

    public DifficultyService(@Nullable Context context) {
        super(context, "difficulties", new String[]{"id", "desc"}, new String[]{"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT NOT NULL"});
    }

    @Override
    public boolean add(String name) {
        ContentValues values = new ContentValues();
        values.put(this.columns[1], name);
        this.insert(values);
        return true;
    }

    @Override
    public boolean edit(String name, int id) {
        ContentValues values = new ContentValues();
        values.put(this.columns[1], name);
        return this.update(values, id);
    }

    @Override
    public String get(int id) {
        Cursor c = getReadableDatabase().rawQuery("SELECT " + this.columns[1] + " FROM " + this.TABLE_NAME + " WHERE " + this.columns[0] + "=?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            return c.getString(0);
        }
        c.close();
        return null;
    }

    @Override
    public boolean onServiceCreate(SQLiteDatabase db) {
        this.add("Easy");
        this.add("Medium");
        this.add("Advanced");
        return true;
    }
}
