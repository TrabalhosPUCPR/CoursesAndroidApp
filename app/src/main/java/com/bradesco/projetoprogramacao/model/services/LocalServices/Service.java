package com.bradesco.projetoprogramacao.model.services.LocalServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Service<T> extends SQLiteOpenHelper implements ServicesInterface<T> {
    public final Context context;
    private static final int DB_VERSION = 1;
    public final String TABLE_NAME;
    public final String[] columns;
    public final String[] col_modifiers;

    /**
     * Constructor for the service
     * @param context context of the activity
     * @param TABLE_NAME name of the table to be created
     * @param columns columns to be added to the table, index 0 must be its primary key
     * @param col_modifiers modifiers to be added to the columns, must have same length as columns
     */
    public Service(@Nullable Context context, @NonNull String TABLE_NAME, @NonNull String[] columns, @NonNull String[] col_modifiers) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.TABLE_NAME = TABLE_NAME;
        this.columns = columns;
        this.col_modifiers = col_modifiers;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if(!tableExists()){
            createTable(sqLiteDatabase);
            if(!onServiceCreate(sqLiteDatabase)){
                throw new RuntimeException("Error: Could not add default values to " + TABLE_NAME);
            }
        }
    }

    private boolean tableExists(){
        Cursor c = getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + this.TABLE_NAME + "'", null);
        int i = c.getCount();
        c.close();
        return i == 1;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(SQLiteDatabase db){
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
        for(int i = 0; i < this.columns.length; i++){
            query.append(this.columns[i]).append(" ").append(this.col_modifiers[i]);
            if (i + 1 == this.columns.length){
                query.append(");");
            }else {
                query.append(",");
            }
        }
        db.execSQL(query.toString());
    }

    public boolean onServiceCreate(SQLiteDatabase db){
        return true;
    }

    /**
     * Adds the contentValues to the table in the database, should only be called inside the add() function of the service
     * @param values Values to be added
     * @return the id of the inserted value, returns -1 when it fails
     */
    public long insert(ContentValues values) {
        return getWritableDatabase().insert(this.TABLE_NAME, null, values);
    }

    /**
     * Update the row with the id with the contentValues provided, should only be called inside the edit() function of the service
     * @param values Values to be added
     * @param id primary key of the row to be edited
     * @return returns true if the update succeeded or false if not
     */
    public boolean update(ContentValues values, int id){
        return getWritableDatabase().update(this.TABLE_NAME, values, this.columns[0] + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    @Override
    public List<T> getList() {
        ArrayList<T> list = new ArrayList<>();
        Cursor c = getWritableDatabase().rawQuery("SELECT " + this.columns[0] + " FROM " + this.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                list.add(this.get(c.getInt(0)));
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }

    /**
     * Removes all rows in table
     * @return returns true if the clear succeeded or false if not
     */
    @Override
    public boolean clearAll() {
        int count = getWritableDatabase().delete(this.TABLE_NAME, null, null);
        return count > 0;
    }

    /**
     * Removes the row where its primary key is equal to id
     * @param id primary key of the row to be removed
     * @return returns true if the removal succeeded or false if not
     */
    @Override
    public boolean remove(int id) {
        return getWritableDatabase().delete(this.TABLE_NAME, this.columns[0] + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    public int size(){
        long count = DatabaseUtils.queryNumEntries(getReadableDatabase(), this.TABLE_NAME);
        return (int) count;
    }

    final public ArrayList<Integer> getPrimaryKeys(){
        ArrayList<Integer> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT " + this.columns[0] + " FROM " + this.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                list.add(c.getInt(0));
            }while (c.moveToNext());
        }
        return list;
    }
}
