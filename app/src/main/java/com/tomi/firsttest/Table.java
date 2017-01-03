package com.tomi.firsttest;


import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class Table {
    TableLayout table;
    Context context;
   // ArrayList<ArrayList<String>> tableRows = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> tableColumns = new ArrayList<ArrayList<String>>();






    public Table(TableLayout table, Context context){
        this.table = table;
        this.context = context;

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

        tableColumns.add(timesOfClasses);
        tableColumns.add(Monday);
        tableColumns.add(Thuesday);
        tableColumns.add(Wednesday);
        tableColumns.add(Thursday);
        tableColumns.add(Friday);

    }


    public void dayTable(){
        Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
        table.setStretchAllColumns(true);
        table.bringToFront();

        if(day < 2 || day > 6)day = 2;
        //for(String column: tableColumns.get(day)){
        for(int i = 1; i<15; i++){


                TableRow tr = new TableRow(context);

                    TextView view = new TextView(context);
                    //view.setText("test day: " + day);
                    view.setText(tableColumns.get(day).get(i));
                    tr.addView(view);

                table.addView(tr);
                }

    }
}
