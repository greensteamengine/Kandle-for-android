package com.tomi.Kandle;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class Table implements Serializable {
    TableLayout table;
    Context context;
    Boolean onlyDay;
    Button tableSwitch;
    Button prev;
    Button next;
   // ArrayList<ArrayList<String>> tableRows = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> tableColumns = new ArrayList<ArrayList<String>>();
    Calendar calendar = Calendar.getInstance();
    int day =  ((calendar.get(Calendar.DAY_OF_WEEK)-calendar.MONDAY + 7) % 7) + 1;

    public ArrayList<ArrayList<String>> getWholeTable(){
        return tableColumns;
    }

    public int getDay(){
        return day;
    }

    public void setday(int day){
        this.day = day;
    }

    public boolean getOnlyDay(){
        return onlyDay;
    }

    public void setOnlyDay(boolean onlyDay){
        this.onlyDay = onlyDay;
    }

    public void setWholeTable(ArrayList<ArrayList<String>> tableColumns){
        this.tableColumns = tableColumns;
    }

    public Table(TableLayout table, Context context, Button tableSwitch, Button prev, Button next){
        this.table = table;
        this.context = context;
        this.onlyDay = true;
        this.tableSwitch = tableSwitch;
        this.prev = prev;
        this.next = next;



        /*
        ArrayList<String> daysOfWeek = new ArrayList<String>(Arrays.asList(
                "zaciatok", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok"));

        tableRows.add(daysOfWeek);

        int hour = 8;
        int minutes = 10;

        for(int i = 0; i < 14; i++){
            ArrayList<String> rowOfTable = new ArrayList<>();
            hour += (hour*60 + i*50)%60;
            minutes = (minutes+(i*50))%60;
            rowOfTable.add(hour + ":" + minutes);
            tableRows.add(rowOfTable);
        }
        */
        ArrayList<String> timesOfClasses = new ArrayList<String>(Arrays.asList(
                "zaciatok", "  8:10", "  9:00", " 9:50", "10:40", "11:30", "12:20",
                "13:10", "14:00", "14:50", "15:40", "16:30", "17:20", "18:10", "19:00"));
        ArrayList<String> Monday = new ArrayList<String>(Arrays.asList("Pondelok"));
        ArrayList<String> Thuesday = new ArrayList<String>(Arrays.asList("Utorok"));
        ArrayList<String> Wednesday = new ArrayList<String>(Arrays.asList("Streda"));
        ArrayList<String> Thursday = new ArrayList<String>(Arrays.asList("Štvrtok"));
        ArrayList<String> Friday = new ArrayList<String>(Arrays.asList("Piatok"));

        for(int i = 1; i < 16; i++){
            Monday.add("  ");
            Thuesday.add("  ");
            Wednesday.add("  ");
            Thursday.add("  ");
            Friday.add("  ");
        }

        tableColumns.add(timesOfClasses);
        tableColumns.add(Monday);
        tableColumns.add(Thuesday);
        tableColumns.add(Wednesday);
        tableColumns.add(Thursday);
        tableColumns.add(Friday);

    }



    public void modifyTable(Timetable timetable){
        //ArrayList<Lesson> lessons = timetable.getLessons();
        for(Lesson lesson: timetable.getLessons()){
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
                    tableColumns.get(numOfDay).set(i, " ");
                }
            }
        }
    }

    public void shiftDayBack(){
        if(day == 1)day = 5; else day -= 1;
    }
    public void shiftDayForward(){
        if(day == 5)day = 1; else day += 1;
    }

    public void createTable(Boolean change){
        if(change)changeView(tableSwitch, prev, next);
        table.removeAllViews();
        if(onlyDay)dayTable(); else weekTable();
    }

    private void changeView(Button tableSwitch, Button prev, Button next){
        onlyDay ^= true;
        /*
        if(onlyDay){
            tableSwitch.setText("Zobrazit cely tyzden");
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        else {
            tableSwitch.setText("Zobrazit iba den");
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        }
        */
    }

    private void dayTable() {

        table.setStretchAllColumns(true);
        table.bringToFront();

        tableSwitch.setText("Zobrazit cely tyzden");
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);


        if (day < 1 || day > 5) day = 1;
        //for(String column: tableColumns.get(id)){
        for (int i = 0; i < 15; i++) {

            TableRow tr = new TableRow(context);

            TextView viewTime = new TextView(context);
            viewTime.setText(tableColumns.get(0).get(i));
            viewTime.setTextColor(Color.BLACK);
            tr.addView(viewTime);

            TextView view = new TextView(context);
            //view.setText("test id: " + id);
            view.setText(tableColumns.get(day).get(i));
            view.setTextColor(Color.BLACK);
            tr.addView(view);
            if(tableColumns.get(day).get(i) == " "){
                view.setBackgroundColor(Color.GREEN);
            }else{
                view.setBackgroundColor(Color.WHITE);
            }

            table.addView(tr);
        }

    }

    private void weekTable(){
        table.setStretchAllColumns(true);
        table.bringToFront();


        tableSwitch.setText("Zobrazit iba den");
        prev.setVisibility(View.GONE);
        next.setVisibility(View.GONE);


        for(int i = 0; i<15; i++){


            TableRow tr = new TableRow(context);
            for(ArrayList<String> column: tableColumns){

                TextView view = new TextView(context);
                view.setText(column.get(i));
                if(column.get(i) == " "){
                    view.setBackgroundColor(Color.GREEN);
                }else{
                    view.setBackgroundColor(Color.WHITE);
                }
                view.setTextColor(Color.BLACK);
                tr.addView(view);
            }
            table.addView(tr);
            //view.setText("test id: " + id);
        }
    }
}
