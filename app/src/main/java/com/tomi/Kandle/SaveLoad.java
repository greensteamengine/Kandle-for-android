package com.tomi.Kandle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class SaveLoad {
    static String parserFileName = "CandleSaveFile";
    static String tableFileName = "CandleSaveFile";

    public static void saveState(Bundle savedInstanceState, MyParser parser, Table table){
        savedInstanceState.putSerializable("myParser", parser);

        savedInstanceState.putSerializable("allTables", parser.giveAllTimetables());
        savedInstanceState.putSerializable("currentTable", parser.getTable().getWholeTable());
        savedInstanceState.putSerializable("tableLayout", parser.getCurrentTimetable());
        savedInstanceState.putBoolean("onlyDay", parser.getTable().getOnlyDay());
        savedInstanceState.putInt("id", parser.getTable().getDay());

        savedInstanceState.putBoolean("searchMode", table.isSearchMode());
    }

    public static void loadState(Bundle savedInstanceState, MyParser parser, Table table){
        parser = ((MyParser)savedInstanceState.getSerializable("myParser"));
        // if(parser != null && table != null) {

        parser.setAllTimetables((ArrayList<Timetable>) savedInstanceState.getSerializable("allTables"));
        parser.getTable().setWholeTable((ArrayList<ArrayList<String>>)savedInstanceState.getSerializable("currentTable"));
        parser.setCurrentTimetable((Timetable) savedInstanceState.getSerializable("tableLayout"));
        parser.getTable().setOnlyDay(savedInstanceState.getBoolean("onlyDay"));
        parser.getTable().setday(savedInstanceState.getInt("id"));

        table.setSearchMode(!savedInstanceState.getBoolean("searchMode"));
        table.hideButtons();
        parser.draw(false);
    }

    public static void saveData(Context context, MyParser parser, Table table){
        //String parserFileName = "CandleSaveFile";
        //String tableFileName = "CandleSaveFile";
        FileOutputStream outputStream1;
        FileOutputStream outputStream2;

        try {
            File f1 = new File(parserFileName);
            File f2 = new File(tableFileName);

            outputStream1 = context.openFileOutput(parserFileName, Context.MODE_PRIVATE);
            outputStream2 = context.openFileOutput(tableFileName, Context.MODE_PRIVATE);
            ObjectOutputStream os1 = new ObjectOutputStream(outputStream1);
            ObjectOutputStream os2 = new ObjectOutputStream(outputStream2);
            os1.writeObject(parser);
            os2.writeObject(table);
            outputStream1.close();
            outputStream2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO doesn't work, may look at it...
    public static MyParser loadParser(Context context){
        try {
        FileInputStream fis1 = context.openFileInput(parserFileName);


        ObjectInputStream is1 = new ObjectInputStream(fis1);

            MyParser parser = (MyParser) is1.readObject();


        is1.close();

        fis1.close();
            return parser;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("load", "failed to load Parser");
        return null;
    }

    public static Table loadTable(Context context){
        try {

            FileInputStream fis2 = context.openFileInput(tableFileName);
            ObjectInputStream is2 = new ObjectInputStream(fis2);
            Table table = (Table) is2.readObject();
            is2.close();
            fis2.close();
            return table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("load", "failed to load Table");
        return null;
    }
}
