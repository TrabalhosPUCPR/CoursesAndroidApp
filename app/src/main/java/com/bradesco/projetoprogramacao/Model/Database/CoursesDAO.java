package com.bradesco.projetoprogramacao.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.Model.Course.Chapters;
import com.bradesco.projetoprogramacao.Model.Course.CourseListManager;
import com.bradesco.projetoprogramacao.Model.Course.CourseModel;
import com.bradesco.projetoprogramacao.Model.Course.Page;

import java.util.ArrayList;
import java.util.List;

public class CoursesDAO extends SQLiteOpenHelper implements DAO<CourseModel> {

    private static final int DB_VERSION = 1;

    private static final String TABLE_COURSES = "courses";
    private static final String COURSES_COL_ID = "id";
    private static final String COURSES_COL_TITLE = "title";
    private static final String COURSES_COL_SUBTITLE = "subtitle";
    private static final String COURSES_COL_DESCRIPTION = "description";
    private static final String COURSES_COL_COMPLETED = "completed";
    private static final String COURSES_COL_DIFFICULTY_ID = "difficulty";

    private static final String TABLE_PAGES = "pages";
    private static final String PAGES_ID = "id";
    private static final String PAGES_TEXT = "text";
    private static final String PAGES_CHAPTER_ID = "chapterId";

    private static final String TABLE_CHAPTERS = "chapters";
    private static final String CHAPTERS_ID = "id";
    private static final String CHAPTERS_NAME = "name";
    private static final String CHAPTERS_DESCRIPTION = "description";
    private static final String CHAPTERS_COURSE_ID = "courseId";

    private static final String TABLE_DIFFICULTIES = "difficulties";
    private static final String DIFFICULTIES_ID = "id";
    private static final String DIFFICULTIES_NAME = "name";

    public CoursesDAO(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES + "(";
        query += COURSES_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        query += COURSES_COL_TITLE + " TEXT NOT NULL,";
        query += COURSES_COL_SUBTITLE + " TEXT NOT NULL,";
        query += COURSES_COL_DESCRIPTION + " TEXT NOT NULL,";
        query += COURSES_COL_COMPLETED + " INTEGER NOT NULL,";
        query += COURSES_COL_DIFFICULTY_ID + " INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_PAGES + "(";
        query += PAGES_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,";
        query += PAGES_CHAPTER_ID + "  INTEGER NOT NULL,";
        query += PAGES_TEXT + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_DIFFICULTIES + "(";
        query += DIFFICULTIES_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,";
        query += DIFFICULTIES_NAME + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_CHAPTERS + "(";
        query += CHAPTERS_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,";
        query += CHAPTERS_NAME + " TEXT NOT NULL,";
        query += CHAPTERS_DESCRIPTION + " TEXT NOT NULL,";
        query += CHAPTERS_COURSE_ID + " INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(query);

        ContentValues values = new ContentValues();
        values.put(DIFFICULTIES_NAME, "Easy");
        sqLiteDatabase.insert(TABLE_DIFFICULTIES, null, values);
        values = new ContentValues();
        values.put(DIFFICULTIES_NAME, "Medium");
        sqLiteDatabase.insert(TABLE_DIFFICULTIES, null, values);
        values = new ContentValues();
        values.put(DIFFICULTIES_NAME, "Advanced");
        sqLiteDatabase.insert(TABLE_DIFFICULTIES, null, values);

        for(CourseModel course : CourseListManager.createDefaultCourses()){
            values = new ContentValues();
            values.put(COURSES_COL_TITLE, course.getTitle());
            values.put(COURSES_COL_SUBTITLE, course.getSubTitle());
            values.put(COURSES_COL_DESCRIPTION, course.getIntroduction());
            values.put(COURSES_COL_COMPLETED, course.isCompleted());
            values.put(COURSES_COL_DIFFICULTY_ID, course.getDifficulty());
            long id = sqLiteDatabase.insert(TABLE_COURSES, null, values);

            for(Chapters c : course.getChapters()){
                values = new ContentValues();
                values.put(CHAPTERS_NAME, c.getTitle());
                values.put(CHAPTERS_DESCRIPTION, c.getDescription());
                values.put(CHAPTERS_COURSE_ID, id);
                long chapterId = sqLiteDatabase.insert(TABLE_CHAPTERS, null, values);
                c.setId((int) id);
                for(Page p : c.getPages()){
                    values = new ContentValues();
                    values.put(PAGES_CHAPTER_ID, chapterId);
                    values.put(PAGES_TEXT, p.getParagraphs());
                    sqLiteDatabase.insert(TABLE_PAGES, null, values);
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO: 10/20/2022
    }

    @Override
    public boolean add(CourseModel course) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COURSES_COL_TITLE, course.getTitle());
        values.put(COURSES_COL_SUBTITLE, course.getSubTitle());
        values.put(COURSES_COL_DESCRIPTION, course.getIntroduction());
        values.put(COURSES_COL_COMPLETED, course.isCompleted());
        values.put(COURSES_COL_DIFFICULTY_ID, course.getDifficulty());

        long id = db.insert(TABLE_COURSES, null, values);
        course.setId((int) id);

        for(Chapters c : course.getChapters()){
            values = new ContentValues();
            values.put(CHAPTERS_NAME, c.getTitle());
            values.put(CHAPTERS_DESCRIPTION, c.getDescription());
            id = db.insert(TABLE_CHAPTERS, null, values);
            c.setId((int) id);
            for(Page p : c.getPages()){
                values = new ContentValues();
                values.put(PAGES_CHAPTER_ID, id);
                values.put(PAGES_TEXT, p.getParagraphs());
                db.insert(TABLE_PAGES, null, values);
            }
        }
        db.close();
        return id > 0;
    }

    @Override
    public boolean remove(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(TABLE_COURSES, COURSES_COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return count > 0;
    }

    public boolean remove(int id, boolean removeChaptersPages) {
        SQLiteDatabase db = getWritableDatabase();
        if (removeChaptersPages){
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CHAPTERS + "WHERE " + CHAPTERS_COURSE_ID + "=" + id, null);
            if(c.moveToFirst()){
                do{
                    db.delete(TABLE_PAGES, PAGES_CHAPTER_ID + "=?", new String[]{String.valueOf(c.getInt(0))});
                } while (c.moveToNext());
            }
            db.delete(TABLE_CHAPTERS, CHAPTERS_COURSE_ID + "=?", new String[]{String.valueOf(id)});
        }
        int count = db.delete(TABLE_COURSES, COURSES_COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return count > 0;
    }

    @Override
    public boolean clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(TABLE_COURSES, null, null);
        db.close();
        return count > 0;
    }

    @Override
    public boolean edit(CourseModel course, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COURSES_COL_TITLE, course.getTitle());
        values.put(COURSES_COL_SUBTITLE, course.getSubTitle());
        values.put(COURSES_COL_DESCRIPTION, course.getIntroduction());
        values.put(COURSES_COL_COMPLETED, course.isCompleted());
        values.put(COURSES_COL_DIFFICULTY_ID, course.getDifficulty());

        int count = db.update(TABLE_COURSES, values, COURSES_COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return count > 0;
    }

    @Override
    public List<CourseModel> getList() {
        ArrayList<CourseModel> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSES, null);
        if(c.moveToFirst()){
            do{
                CourseModel courseModel = new CourseModel(c.getString(1), c.getString(2), c.getString(3), c.getInt(4), c.getInt(5) != 0);
                courseModel.setId(c.getInt(0));

                Cursor c2 = db.rawQuery("SELECT * FROM " + TABLE_CHAPTERS + " WHERE " + CHAPTERS_COURSE_ID + "=" + c.getInt(0), null);
                if(c2.moveToFirst())
                    do{
                        Chapters chapters = new Chapters(c2.getString(1), c2.getString(2));
                        chapters.setId(c2.getInt(0));

                        Cursor c3 = db.rawQuery("SELECT * FROM " + TABLE_PAGES + " WHERE " + PAGES_CHAPTER_ID + "=" + c2.getInt(0), null);
                        if(c3.moveToFirst()) {
                            do {
                                Page page = new Page(c3.getString(2));
                                chapters.addPage(page);
                            } while (c3.moveToNext());
                        }
                        courseModel.addChapter(chapters);

                    }while (c2.moveToNext());

                list.add(courseModel);
            }while (c.moveToNext());
        }
        db.close();
        return list;
    }

    @Override
    public CourseModel get(int id) {
        SQLiteDatabase db = getReadableDatabase();
        CourseModel course = new CourseModel();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSES + " WHERE "+ COURSES_COL_ID + "=" + id + ";", null);
        if(c.moveToFirst()){
            course.setId(c.getInt(0));
            course.setTitle(c.getString(1));
            course.setSubTitle(c.getString(2));
            course.setIntroduction(c.getString(3));
            course.setCompleted(c.getInt(4) != 0);
            course.setDifficulty(c.getInt(5));
        }
        db.close();
        return course;
    }

    public String getDifficultiesName(int id){
        SQLiteDatabase db = getReadableDatabase();
        CourseModel course = new CourseModel();
        String name = "";

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DIFFICULTIES + " WHERE "+ DIFFICULTIES_ID + "=" + id + ";", null);
        if(c.moveToFirst()){
            name = c.getString(1);
        }
        db.close();
        return name;
    }
}
