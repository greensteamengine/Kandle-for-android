package com.tomi.Kandle;

import java.io.Serializable;
import java.util.ArrayList;

public class Timetable implements Serializable {

    ArrayList<Lesson> lessons;
    String nameOfTable;
    String typeOfTable;

    public Timetable(String name, String type){
        lessons = new ArrayList<>();
        this.nameOfTable = name;
        this.typeOfTable = type;
    }

    public ArrayList<Lesson> getLessons(){
        return lessons;
    }

    public void addToTable(Lesson lesson){
        this.lessons.add(lesson);
    }

    public String getName(){
        return nameOfTable;
    }

    public String getType(){
        return typeOfTable;
    }

}
