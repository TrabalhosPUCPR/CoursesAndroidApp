package com.bradesco.projetoprogramacao.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bradesco.projetoprogramacao.Model.Course.Chapters;
import com.bradesco.projetoprogramacao.Model.Course.CourseListManager;
import com.bradesco.projetoprogramacao.Model.Course.Course;
import com.bradesco.projetoprogramacao.Model.Course.Page;
import com.bradesco.projetoprogramacao.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class CoursesDAO extends SQLiteOpenHelper implements DAO<Course> {

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

    private static final String TABLE_QUESTIONS = "questions";
    private static final String QUESTIONS_ID = "id";
    private static final String QUESTIONS_CORRECT_INDEX = "correctAnswer";
    private static final String QUESTIONS_PAGE_ID = "pageId";
    private static final String QUESTION_COURSE_ID = "chapterId";

    private static final String TABLE_ANSWERS = "answers";
    private static final String ANSWERS_ID = "id";
    private static final String ANSWERS_TEXT = "text";
    private static final String ANSWERS_QUESTION_ID = "question_id";

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
        query += PAGES_CHAPTER_ID + "  INTEGER,";
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

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS + "(";
        query += QUESTIONS_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,";
        query += QUESTIONS_CORRECT_INDEX + " INTEGER NOT NULL,";
        query += QUESTIONS_PAGE_ID + " INTEGER NOT NULL,";
        query += QUESTION_COURSE_ID + " INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWERS + "(";
        query += ANSWERS_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,";
        query += ANSWERS_TEXT + " TEXT NOT NULL,";
        query += ANSWERS_QUESTION_ID + " INTEGER NOT NULL);";
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

        for(Course course : CourseListManager.createDefaultCourses()){
            add(course, sqLiteDatabase);
        }
    }

    private void addChapters(Course course, SQLiteDatabase sqLiteDatabase){
        for(Chapters c : course.getChapters()){
            ContentValues values = new ContentValues();
            values.put(CHAPTERS_NAME, c.getTitle());
            values.put(CHAPTERS_DESCRIPTION, c.getDescription());
            values.put(CHAPTERS_COURSE_ID, course.getId());
            long chapterId = sqLiteDatabase.insert(TABLE_CHAPTERS, null, values);
            c.setId((int) chapterId);
            for(Page p : c.getPages()){
                values = new ContentValues();
                values.put(PAGES_CHAPTER_ID, chapterId);
                values.put(PAGES_TEXT, p.getParagraphs());
                long pageId = sqLiteDatabase.insert(TABLE_PAGES, null, values);
                p.setId((int) pageId);
            }
        }
    }

    private void addQuestions(Course course, SQLiteDatabase sqLiteDatabase){
        for(Question question : course.getEndingQuestions()){
            ContentValues values = new ContentValues();
            values.put(PAGES_TEXT, question.getQuestionArea().getParagraphs());
            long pageId = sqLiteDatabase.insert(TABLE_PAGES, null, values);
            question.getQuestionArea().setId((int) pageId);

            values = new ContentValues();
            values.put(QUESTIONS_PAGE_ID, (int) pageId);
            values.put(QUESTIONS_CORRECT_INDEX, question.getCorrectAnswerIndex());
            values.put(QUESTION_COURSE_ID, course.getId());
            long questionId = sqLiteDatabase.insert(TABLE_QUESTIONS, null, values);
            question.setId((int) questionId);

            for(String answer : question.getAnswers()){
                values = new ContentValues();
                values.put(ANSWERS_TEXT, answer);
                values.put(ANSWERS_QUESTION_ID, questionId);
                sqLiteDatabase.insert(TABLE_ANSWERS, null, values);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO: 10/20/2022
    }
    @Override
    public boolean add(Course course){
        SQLiteDatabase db = getWritableDatabase();
        return add(course, db);
    }

    public boolean add(Course course, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COURSES_COL_TITLE, course.getTitle());
        values.put(COURSES_COL_SUBTITLE, course.getSubTitle());
        values.put(COURSES_COL_DESCRIPTION, course.getIntroduction());
        values.put(COURSES_COL_COMPLETED, course.isCompleted());
        values.put(COURSES_COL_DIFFICULTY_ID, course.getDifficulty());

        long id = db.insert(TABLE_COURSES, null, values);
        course.setId((int) id);

        addChapters(course, db);
        addQuestions(course, db);
        return id > 0;
    }

    @Override
    public boolean remove(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(TABLE_COURSES, COURSES_COL_ID + "=?", new String[]{String.valueOf(id)});
        return count > 0;
    }

    public boolean remove(int id, boolean removeAllFromIt) {
        SQLiteDatabase db = getWritableDatabase();
        if (removeAllFromIt){
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CHAPTERS + "WHERE " + CHAPTERS_COURSE_ID + "=" + id, null);
            if(c.moveToFirst()){
                do{
                    db.delete(TABLE_PAGES, PAGES_CHAPTER_ID + "=?", new String[]{String.valueOf(c.getInt(0))});
                } while (c.moveToNext());
            }
            db.delete(TABLE_CHAPTERS, CHAPTERS_COURSE_ID + "=?", new String[]{String.valueOf(id)});
            c.close();
            c = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + "WHERE " + QUESTION_COURSE_ID + "=" + id, null);
            if(c.moveToFirst()){
                do{
                    db.delete(TABLE_ANSWERS, ANSWERS_QUESTION_ID + "=?", new String[]{String.valueOf(c.getInt(0))});
                } while (c.moveToNext());
            }
            c.close();
            db.delete(TABLE_QUESTIONS, QUESTION_COURSE_ID + "=?", new String[]{String.valueOf(id)});
        }
        int count = db.delete(TABLE_COURSES, COURSES_COL_ID + "=?", new String[]{String.valueOf(id)});
        return count > 0;
    }

    @Override
    public boolean clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(TABLE_COURSES, null, null);
        return count > 0;
    }

    @Override
    public boolean edit(Course course, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COURSES_COL_TITLE, course.getTitle());
        values.put(COURSES_COL_SUBTITLE, course.getSubTitle());
        values.put(COURSES_COL_DESCRIPTION, course.getIntroduction());
        values.put(COURSES_COL_COMPLETED, course.isCompleted());
        values.put(COURSES_COL_DIFFICULTY_ID, course.getDifficulty());

        int count = db.update(TABLE_COURSES, values, COURSES_COL_ID + "=?", new String[]{String.valueOf(id)});
        return count > 0;
    }

    @Override
    public List<Course> getList() {
        ArrayList<Course> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSES, null);
        if(c.moveToFirst()){
            do{
                list.add(get(c.getInt(0), db));
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }

    @Override
    public Course get(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return get(id, db);
    }

    public Course get(int id, SQLiteDatabase db){
        Course course = new Course();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSES + " WHERE "+ COURSES_COL_ID + "=" + id + ";", null);
        if(c.moveToFirst()){
            course.setId(c.getInt(0));
            course.setTitle(c.getString(1));
            course.setSubTitle(c.getString(2));
            course.setIntroduction(c.getString(3));
            course.setCompleted(c.getInt(4) != 0);
            course.setDifficulty(c.getInt(5));

            Cursor c2 = db.rawQuery("SELECT * FROM " + TABLE_CHAPTERS + " WHERE " + CHAPTERS_COURSE_ID + "=" + c.getInt(0), null);
            if(c2.moveToFirst()){
                do{
                    Chapters chapters = new Chapters(c2.getString(1), c2.getString(2));
                    chapters.setId(c2.getInt(0));
                    Cursor c3 = db.rawQuery("SELECT * FROM " + TABLE_PAGES + " WHERE " + PAGES_CHAPTER_ID + "=" + c2.getInt(0), null);
                    if(c3.moveToFirst()) {
                        do {
                            Page page = new Page(c3.getString(2));
                            chapters.addPage(page);
                            page.setId(c3.getInt(0));
                        } while (c3.moveToNext());
                    }
                    course.addChapter(chapters);
                    c3.close();
                }while (c2.moveToNext());
            }
            c2.close();
            c2 = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " WHERE " + QUESTION_COURSE_ID + "=" + c.getInt(0), null);
            if(c2.moveToFirst()){
                do{
                    Page page = new Page();
                    Cursor c4 = db.rawQuery("SELECT " + PAGES_TEXT + " FROM " + TABLE_PAGES + " WHERE " + PAGES_ID+"="+c2.getInt(2), null);
                    if (c4.moveToFirst()){
                        page = new Page(c4.getString(0));
                    }
                    c4.close();
                    Question question = new Question(page);
                    question.setId(c2.getInt(0));
                    question.setCorrectAnswerIndex(c2.getInt(1));
                    Cursor c3 = db.rawQuery("SELECT * FROM " + TABLE_ANSWERS + " WHERE " + ANSWERS_QUESTION_ID + "=" + question.getId(), null);
                    if(c3.moveToFirst()) {
                        do {
                            question.addAnswer(c3.getString(1));
                        } while (c3.moveToNext());
                    }
                    course.addEndingQuestion(question);
                    c3.close();
                }while (c2.moveToNext());
            }
            c2.close();
        }
        c.close();
        return course;
    }

    public String getDifficultiesName(int id){
        SQLiteDatabase db = getReadableDatabase();
        String name = "ERROR";

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DIFFICULTIES + " WHERE "+ DIFFICULTIES_ID + "=" + id + ";", null);
        if(c.moveToFirst()){
            name = c.getString(1);
        }
        c.close();
        return name;
    }
}
