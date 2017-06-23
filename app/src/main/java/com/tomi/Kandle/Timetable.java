package com.tomi.Kandle;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Timetable implements Serializable {

    ArrayList<Lesson> lessons;
    String nameOfTable;
    String typeOfTable;
    boolean checked;

    public Timetable(String name, String type){
        lessons = new ArrayList<>();
        this.nameOfTable = name;
        this.typeOfTable = type;
        this.checked = false;
    }

    public void check(Boolean check){
        this.checked = check;
    }

    public Boolean isChecked(){
        return checked;
    }

    public ArrayList<Lesson> getLessons(){
        return lessons;
    }

    public Lesson getLesson(Integer begining, Integer day){

            for(Lesson lesson:lessons) {
                //String dayString = lesson.getDay();
                Integer dayOfLesson = lesson.getDayOfLesson();
                /*
                switch (dayString) {
                    case ("Po"):
                        dayOfLesson = 1;
                        break;
                    case ("Ut"):
                        dayOfLesson = 2;
                        break;
                    case ("St"):
                        dayOfLesson = 3;
                        break;
                    case ("Št"):
                        dayOfLesson = 4;
                        break;
                    case ("Pi"):
                        dayOfLesson = 5;
                        break;
                }
                */
                if (lesson.getSerialNumberOfStart().equals(begining) && dayOfLesson.equals(day)) {
                    return lesson;
                }
            }
        return null;
    }

    public Lesson getOngoingLesson(int start, Integer day){

        for(Lesson lesson:lessons) {
            //String dayString = lesson.getDay();
            Integer dayOfLesson = lesson.getDayOfLesson();

            if (lesson.getSerialNumberOfStart() <= start &&
                    lesson.getSerialNumberOfEnd() >= start &&
                    dayOfLesson.equals(day)) {
                return lesson;
            }
        }
        return null;
    }

    public void addToTable(Lesson lesson){
        this.lessons.add(lesson);
    }

    public String getName(){
        return nameOfTable;
    }

    public void setName(String name){
        Log.v("timetable class", "setting name");
        this.nameOfTable = name;
    }
    public String getType(){
        return typeOfTable;
    }
}
