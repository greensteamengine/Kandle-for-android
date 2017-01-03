package com.tomi.firsttest;


import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class Table {
    TableLayout table;
    Context context;
    Boolean onlyDay;
   // ArrayList<ArrayList<String>> tableRows = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> tableColumns = new ArrayList<ArrayList<String>>();
    Calendar calendar = Calendar.getInstance();
    int day =  ((calendar.get(Calendar.DAY_OF_WEEK)-calendar.MONDAY + 7) % 7) + 1;

    public Table(TableLayout table, Context context){
        this.table = table;
        this.context = context;
        this.onlyDay = true;

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
            Monday.add("|    ");
            Thuesday.add("|    ");
            Wednesday.add("|    ");
            Thursday.add("|    ");
            Friday.add("|    ");
        }

        tableColumns.add(timesOfClasses);
        tableColumns.add(Monday);
        tableColumns.add(Thuesday);
        tableColumns.add(Wednesday);
        tableColumns.add(Thursday);
        tableColumns.add(Friday);

    }

    public void shiftDayBack(){
        if(day == 1)day = 5; else day -= 1;
    }
    public void shiftDayForward(){
        if(day == 5)day = 1; else day += 1;
    }

    public void createTable(){
        table.removeAllViews();
        if(onlyDay)dayTable(); else weekTable();
    }

    public void changeView(Button tableSwitch, Button prev, Button next){
        onlyDay ^= true;
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
    }

    private void dayTable(){

        table.setStretchAllColumns(true);
        table.bringToFront();

        if(day < 1 || day > 5)day = 1;
        //for(String column: tableColumns.get(day)){
        for(int i = 0; i<15; i++){


                TableRow tr = new TableRow(context);

                    TextView view = new TextView(context);
                    //view.setText("test day: " + day);
                    view.setText(tableColumns.get(day).get(i));
                    tr.addView(view);

                table.addView(tr);
                }

    }

    private void weekTable(){
        table.setStretchAllColumns(true);
        table.bringToFront();
        for(int i = 0; i<15; i++){


            TableRow tr = new TableRow(context);
            for(ArrayList<String> column: tableColumns){

                TextView view = new TextView(context);
                view.setText(column.get(i));
                tr.addView(view);
            }
            table.addView(tr);
            //view.setText("test day: " + day);
        }
    }
}
