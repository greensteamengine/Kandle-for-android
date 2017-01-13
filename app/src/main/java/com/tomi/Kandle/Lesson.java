package com.tomi.Kandle;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

class Lesson implements Serializable {

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

    public String getDay(){
        return day;
    }

    public String getFrom(){
        return from;
    }

    public String getTo(){
        return to;
    }

    public String getRoom(){
        return room;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getLecturers(){
        return lecturers;
    }

    public Integer getSerialNumberOfStart(){
        String[] times =  from.split(":");
        Integer from;
         from = (((Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]))-490)/50)+1;
        Log.v("from", day+from.toString());
                return from;
    }

    public int getSerialNumberOfEnd(){
        String[] times =  to.split(":");
        Integer to;
        to = (((Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]))-490)/50)+1;
        Log.v("to", day+to.toString());
        return to;
    }
}
