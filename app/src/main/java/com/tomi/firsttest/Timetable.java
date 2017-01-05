package com.tomi.firsttest;

import java.util.ArrayList;

public class Timetable {

    ArrayList<Lesson> lessons;
    String nameOfTable;
    String typeOfTable;

    public Timetable(String name, String type){
        lessons = new ArrayList<>();
        this.nameOfTable = name;
        this.typeOfTable = type;
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

    public void addTable(Lesson l){
        lessons.add(l);
    }

}
