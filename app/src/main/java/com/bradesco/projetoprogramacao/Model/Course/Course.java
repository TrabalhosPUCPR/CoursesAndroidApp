package com.bradesco.projetoprogramacao.model.course;

import java.util.ArrayList;

public class Course {
    private int id;
    private String title, subTitle, introduction;
    private ArrayList<Chapters> chapters;
    private ArrayList<Question> endingQuestions;
    private ArrayList<Activities> activities;
    private int difficulty;
    private boolean completed, expanded;

    public Course(){
        this.chapters = new ArrayList<>();
        this.endingQuestions = new ArrayList<>();
        this.activities = new ArrayList<>();
    }
    public Course(String title, String subTitle, String introduction, int difficulty) {
        this.title = title;
        this.subTitle = subTitle;
        this.difficulty = difficulty;
        this.chapters = new ArrayList<>();
        this.endingQuestions = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.completed = false;
        this.introduction = introduction;
        this.expanded = false;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public ArrayList<Chapters> getChapters() {
        return chapters;
    }

    public Chapters getChapter(int index){
        return this.chapters.get(index);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setChapters(ArrayList<Chapters> chapters) {
        this.chapters = chapters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void clicked(){
        this.expanded = !this.expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void addChapter(Chapters chapter){
        this.chapters.add(chapter);
    }

    public void addEndingQuestion(Question question){
        this.endingQuestions.add(question);
    }

    public ArrayList<Question> getEndingQuestions() {
        return endingQuestions;
    }

    public void setEndingQuestions(ArrayList<Question> endingQuestions) {
        this.endingQuestions = endingQuestions;
    }

    public ArrayList<Activities> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activities> activities) {
        this.activities = activities;
    }

    public static ArrayList<Course> getDefaultCourses(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(createDefault_Introduction());
        courses.add(createDefault_DataTypes());
        courses.add(createDefault_Loops());

        return courses;
    }

    public static Course createDefault_Introduction(){
        Course intro = new Course("Introduction", "Welcome to puppy code!", "In this course, you will understand the basics of the app", 1);

        ArrayList<Chapters> chapters = new ArrayList<>();

        chapters.add(new Chapters("PuppyCode", "In this chapter, you will understand what PuppyCode is"));
        chapters.add(new Chapters("Programming Languages", ""));
        chapters.add(new Chapters("Python programming language", "Here, you will learn a very basic usage of python on PuppyCode app and why we use it"));
        chapters.add(new Chapters("Completing an activity", "Here, you will understand how to play activities available in courses"));
        chapters.add(new Chapters("Final notes", ""));

        // chapter 1
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(new Page("Welcome to PuppyCode!\nHere, you will be able to learn various different things about programming and even improve your ability!"));
        pages.add(new Page("PuppyCode provides space for you to read various courses with a vast amount of content and practice them with many different quizzes and activities"));
        pages.add(new Page("We hope you learn a lot from them and have fun doing so!"));
        chapters.get(0).setPages(pages);

        //chapter 2
        pages = new ArrayList<>();
        pages.add(new Page("Programming languages are notations to write programs, the way we communicate with a computer to command it to do something\n\nThere are many of these, a few examples are Python, Java, C++, etc."));
        pages.add(new Page("For one to understand them, a few concepts must be known, like what are variables, conditionals, etc."));
        pages.add(new Page("You can say variables are data holders that can change at any time, they can hold numbers, words, any many other things.\nAnd conditionals are checks we do to verify if something is true or false"));
        chapters.get(1).setPages(pages);

        //chapter 3
        pages = new ArrayList<>();
        pages.add(new Page("Python is a very efficient and simple programming language, you can do an infinite amount of things with ease"));
        pages.add(new Page("To declare a variable, we first give its name, and with equals symbol, give its value, for example: \"var = 5\"\nHere, the variable var, is a number with its value being 5"));
        pages.add(new Page("Before going to the next chapter, it will be useful to know what command is used to print something in the terminal, in python, the command is print(), and inside the parenthesis, we put the variables we want to print, for example, when var is equals to 5, like in the last page, and we send the command print(var), the number 5 will appear in the terminal"));
        chapters.get(2).setPages(pages);

        //chapter 4
        pages = new ArrayList<>();
        pages.add(new Page("Activities normally appear at the end of courses, they are there so you can put in practice what you've learned"));
        pages.add(new Page("To do them, you must print in the terminal, using the area to code, exactly what is written under \"Expected Output\", once you do se, a button will appear to allow you to complete and exit the activity"));
        chapters.get(3).setPages(pages);

        //chapter 5
        pages = new ArrayList<>();
        pages.add(new Page("Learning to program takes a considerable amount of time and patience, so if you find difficulty completing an activity, don't feel discouraged!\nIf you find something difficult, always remember to do more research and search online for more answers or restart the courses available here, always go after more knowledge"));
        pages.add(new Page("Thank you for using PuppyCode, and wish you good studies!"));
        chapters.get(4).setPages(pages);

        //activity
        ArrayList<Activities> activities = new ArrayList<>();
        Page activityPage = new Page("Using the print function, print \"Hello World\" in the terminal, simply type print(), and inside the parenthesis, between two \", type Hello World. This way, you will understand the basics of completing an activity.");
        activities.add(new Activities("Printing", "Learn how to print in the terminal and complete an activity!", "Hello World\n", 1, activityPage));
        intro.setActivities(activities);
        intro.setChapters(chapters);
        return intro;
    }

    // DEFAULT COURSES
    public static Course createDefault_DataTypes(){
        Course dataTypes = new Course("Data Types", "Python basics", "This course we will take a look at the available data types in Python", 1);
        ArrayList<Page> pages = new ArrayList<>();
        ArrayList<Chapters> chapters = new ArrayList<>();

        // add chapters
        chapters.add(new Chapters("Numbers", "Types of different numbers"));
        chapters.add(new Chapters("Letters", "Types of different letters"));

        // add pages to first chapter
        pages.add(new Page("In python, there are Integers, double, longs, etc"));
        pages.add(new Page("Very cool numbers in python"));
        chapters.get(0).setPages(pages);

        // add pages to second chapter
        pages = new ArrayList<>();
        pages.add(new Page("In python, there are Strings and characters"));
        pages.add(new Page("Very cool letters in python"));
        chapters.get(1).setPages(pages);

        // set chapters to course
        dataTypes.setChapters(chapters);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(new Page("The data '1.8' is of which type?")));
        questions.get(0).addAnswer("Integer");
        questions.get(0).addAnswer("String");
        questions.get(0).addCorrectAnswer("Float");
        questions.get(0).addAnswer("Boolean");

        questions.add(new Question(new Page("The data 'potato' is of which type?")));
        questions.get(1).addAnswer("Integer");
        questions.get(1).addCorrectAnswer("String");
        questions.get(1).addAnswer("Float");
        questions.get(1).addAnswer("Boolean");

        dataTypes.setEndingQuestions(questions);
        return dataTypes;
    }

    public static Course createDefault_Loops(){
        Course loops = new Course("Loops", "Simplifing Code with Loops", "In this course, you will learn why and how to loop in python", 2);

        ArrayList<Chapters> chapters = new ArrayList<>();
        chapters.add(new Chapters("Introduction", "Looping  in python"));
        chapters.add(new Chapters("While Loops", "Imperative Loops: while command"));
        chapters.add(new Chapters("For loops", "Imperative Loops: for command"));
        chapters.add(new Chapters("Recursive functions", "Recursive loops"));

        ArrayList<Page> pages = new ArrayList<>();
        pages.add(new Page("Looping is a extremely common act when programming an application, it is used to repeat many commands many times, so it can be used to make things a lot easier"));
        pages.add(new Page("There are plenty of ways to loop, but in general, there are two types: Imperative and Recursive loops"));
        pages.add(new Page("Imperative loops consist on using commands like 'for' and 'while' to iterate over a set of commands many times!"));
        pages.add(new Page("Recursive loops consists on calling the same function over and over again!"));
        chapters.get(0).setPages(pages);

        pages = new ArrayList<>();
        pages.add(new Page("A while loop runs a block of code multiple times until its condition fails, so a while loop with a condition of X < 5, and you add X by 1 inside the loop, the code will run 5 times, up until X is higher or equal than 5!"));
        chapters.get(1).setPages(pages);

        pages = new ArrayList<>();
        pages.add(new Page("A for loop is commonly used to iterate over a list of objects and run a block of code on top of it, this type of for loop can also be called 'enhanced for'"));
        pages.add(new Page("There is another style of for loop different from its enhanced type, although its not exactly available in python, it can be good to know, it works just the same as a while loop, but you can initialize a variable when starting the loop, and perform a command at the end of every iteration, up until its condition fails"));
        chapters.get(2).setPages(pages);

        pages = new ArrayList<>();
        pages.add(new Page("Recursion consists on calling a function while being inside that same function. But, so it can eventually leave its loop, there must a condition inside the function where, when its condition is met, its leaves the function without calling it again"));
        chapters.get(3).setPages(pages);

        loops.setChapters(chapters);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(new Page("How many times will this code print 'Hello'?\nnumber = 5\nwhile number > 0:\n\tprint('hello')\nnumber -= 1")));
        questions.get(0).addAnswer("3");
        questions.get(0).addAnswer("9");
        questions.get(0).addAnswer("It will never stop");
        questions.get(0).addAnswer("0");
        questions.get(0).addCorrectAnswer("5");

        questions.add(new Question(new Page("What is the difference between an enhanced for loop and a while loop?")));
        questions.get(1).addCorrectAnswer("For is better used for lists and arrays, and While is used to run code when a condition is met multiple times");
        questions.get(1).addAnswer("No difference");
        questions.get(1).addAnswer("A while loop consists on calling its own function over and over again, know as a Recursive loop, a for loop it not, known as a Imperative loop");
        questions.get(1).addAnswer("A while loop is better at iterating over an entire list and array");
        questions.get(1).addAnswer("A for loop consists on calling its own function over and over again, know as a Recursive loop, a while loop it not, known as a Imperative loop");

        loops.setEndingQuestions(questions);

        ArrayList<Activities> activities = new ArrayList<>();
        Page page = new Page("Using loops, one can print many lines using very few lines of code, with the knowledge you obtained in this course, print the numbers from 1 to 5 using a simple while loop");
        activities.add(new Activities("While Looping", "Simple activity to practice while loops", "12345", 1, page));

        loops.setActivities(activities);

        return loops;
    }
}
