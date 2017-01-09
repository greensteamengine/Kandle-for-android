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



    /*
    public void modifyTable(ArrayList<ArrayList<String>> tableColumns){

        clearTable();

        //ArrayList<Lesson> lessons = timetable.getLessons();
        for(Lesson lesson: lessons){
            String day = lesson.getDay();
            int from = lesson.getSerialNumberOfStart()+1;
            int to = lesson.getSerialNumberOfEnd()+1;
            int numOfDay = 0;
            switch(day){
                case("Po"): numOfDay =1;break;
                case("Ut"): numOfDay =2;break;
                case("St"): numOfDay =3;break;
                case("Št"): numOfDay =4;break;
                case("Pi"): numOfDay =5;break;
            }
            if(numOfDay!=0){
                for(int i = from; i<= to; i++){
                    tableColumns.get(numOfDay).set(i, lesson.getRoom());
                }
            }
        }
    }
*/
}
