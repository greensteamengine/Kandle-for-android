package com.tomi.firsttest;


import java.util.ArrayList;

class Lesson {

    String day, from, to, room, type, name;
    ArrayList<String> lecturers;

    public Lesson(String day, String from, String to, String room, String type, String name, ArrayList<String> lecturers){

        this.day = day;
        this.from = from;
        this.to = to;
        this.room = room;
        this.type = type;
        this.name = name;
        this.lecturers = lecturers;

    }
}
