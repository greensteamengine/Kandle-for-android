package com.tomi.Kandle;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    Calendar calendar = Calendar.getInstance();

    Button tableSwitch;
    Button prev;
    Button next;

    private ArrayList<ArrayList<String>> tableColumns = new ArrayList<ArrayList<String>>();
    private int day =  ((calendar.get(Calendar.DAY_OF_WEEK)-calendar.MONDAY + 7) % 7) + 1;
    private Boolean onlyDay;

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
        */

        tableColumns = giveEmptyTable();

    }

    public ArrayList<ArrayList<String>> giveEmptyTable(){
        ArrayList<ArrayList<String>> newtTable = new ArrayList<ArrayList<String>>();

        ArrayList<String> timesOfClasses = new ArrayList<String>(Arrays.asList(
                "zaciatok", "  8:10", "  9:00", " 9:50", "10:40", "11:30", "12:20",
                "13:10", "14:00", "14:50", "15:40", "16:30", "17:20", "18:10", "19:00"));
        ArrayList<String> Monday = new ArrayList<String>(Arrays.asList("Pondelok"));
        ArrayList<String> Thuesday = new ArrayList<String>(Arrays.asList("Utorok"));
        ArrayList<String> Wednesday = new ArrayList<String>(Arrays.asList("Streda"));
        ArrayList<String> Thursday = new ArrayList<String>(Arrays.asList("Štvrtok"));
        ArrayList<String> Friday = new ArrayList<String>(Arrays.asList("Piatok"));

        for(int i = 1; i < 16; i++){
            Monday.add(" ");
            Thuesday.add(" ");
            Wednesday.add(" ");
            Thursday.add(" ");
            Friday.add(" ");
        }

        newtTable.add(timesOfClasses);
        newtTable.add(Monday);
        newtTable.add(Thuesday);
        newtTable.add(Wednesday);
        newtTable.add(Thursday);
        newtTable.add(Friday);

        return newtTable;

    }



    public void clearTable(){

        Log.v("clearing", "should be clear");


        for(int i = 1; i < tableColumns.size(); i++){
            for(int j = 1; j < tableColumns.size(); j++){
                tableColumns.get(i).set(j, " ");
            }
        }

       // for (ArrayList<String> day : tableColumns)
       // {
         //   for (String classes : day)
          //  {
          //      classes = "  ";
          //  }
       // }
    }




    public void modifyTable(Timetable timetable){

       // tableColumns.
       // clearTable();
        tableColumns = giveEmptyTable();

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
                    tableColumns.get(numOfDay).set(i, lesson.getRoom());
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

    public void createTable(Boolean change, Timetable timetable){
    //public void createTable(Boolean change){
        if(timetable !=null)modifyTable(timetable);
        if(change)changeView();
        table.removeAllViews();
        if(onlyDay)dayTable(); else weekTable();
    }

    private void changeView(){
        onlyDay ^= true;

    }

    private void dayTable() {

        //clearTable();

        table.setStretchAllColumns(true);
        table.bringToFront();

        tableSwitch.setText("Zobrazit cely tyzden");
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);


        if (day < 1 || day > 5) day = 1;
        for (int i = 0; i < 15; i++) {

            TableRow tr = new TableRow(context);

            TextView viewTime = new TextView(context);
            viewTime.setText(tableColumns.get(0).get(i));
            viewTime.setTextColor(Color.BLACK);
            tr.addView(viewTime);

            TextView view = new TextView(context);

            view.setText(tableColumns.get(day).get(i));

            view.setTextColor(Color.BLACK);
            tr.addView(view);

            if(tableColumns.get(day).get(i) == " "){
                view.setBackgroundColor(Color.WHITE);
            }else{
                view.setBackgroundColor(Color.GREEN);
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
                    view.setBackgroundColor(Color.WHITE);
                }else{
                    view.setBackgroundColor(Color.GREEN);
                }
                view.setTextColor(Color.BLACK);
                tr.addView(view);
            }
            table.addView(tr);
        }
    }
}
