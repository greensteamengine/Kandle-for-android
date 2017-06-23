package com.tomi.Kandle;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class MyParser implements Serializable{


    private String type;
    private String name;

    private String firstUrlString;

    String urlForTxt;

    private static ArrayList<Timetable> allTimetables;
    private static Table table;
    private static Timetable currentTimetable;

    public MyParser( Table table){
        this.allTimetables = new ArrayList<>();
        this.table = table;
    }

    private Boolean parsingNow = false;
    private Boolean haveSomethingToSave = false;

    boolean morePossiblities;

    //ArrayList<String> htmlString;


    public void setData(String type, String name){
        String searchType;
        this.name = name;
        this.type = type;
        System.out.println("type and name: "+type+" "+name);
        switch(type){



            case("Vyucujuci"):this.type = "ucitelia"; searchType = "showTeachers"; break;
            case("Miestnost"):this.type = "miestnosti";searchType = "showRooms"; break;
            default: System.out.println("wrong type, can not add to parse HTML"); searchType = "";

        }

        firstUrlString = "https://candle.fmph.uniba.sk/?"+searchType+"="+name;
    }

    public void hideButtons(){
        table.hideButtons();
    }


    public Timetable getConcreteTimetable(int index){
        return allTimetables.get(index);
    }

    public void setAllTimetables(ArrayList<Timetable> allTimetables){
        this.allTimetables = allTimetables;
    }

    public Timetable getCurrentTimetable(){
        return currentTimetable;
    }

    public void setCurrentTimetable(Timetable table){
        this.currentTimetable = table;
        haveSomethingToSave = true;
    }

    public ArrayList<Timetable> giveAllTimetables(){
        return allTimetables;
    }

    public Table getTable(){
        return table;
    }


    public Boolean IsParsing(){
        return parsingNow;
    }

    public void draw(boolean change){
        this.table.clearTable();
        this.table.createTable(change, this.currentTimetable);
    }

    public String getUrlForFristHtml(){
        return firstUrlString;
    }

    //tato metod sa bude dat prerobit na zobrazovanie predbeznych vysledkov
    //public ArrayList<String> returnPossibleChoises(ArrayList<String> linesOfHtml, String type){
    //method needs html code in bufferreader
    public ArrayList<String> returnPossibleChoises(ArrayList<String> htmlStrings) throws IOException {

        ArrayList<String> possiblities = new ArrayList<>();

        //htmlString = getStingsFromBuffer(bufferedReader);
        boolean go = false;
        for(String line: htmlStrings){
            //Log.v("htmlString", line);
            if(line.contains("<ul class=\"vysledky_hladania\">")) {
                go = true;
            }
            if(line.contains("</ul>")) break;
            if(line.contains("<li><a href=") && go){
                String posib = line.substring(line.indexOf(type+'/')+type.length()+1, line.indexOf("\">"));
                possiblities.add(posib);
                Log.v("pssibility", posib);
            }
        }
        return possiblities;
    }



    public Lesson parseLineOfText(String lineTextOfLecture) {
        String day = "", from = "", to = "", room = "", typeOfLecture = "", nameOfLecture = "";
        ArrayList<String> lecturers = new ArrayList<>();

        String[] words = lineTextOfLecture.split("\\s+");

        day = words[0];
        from = words[1];
        to = words[3];
        room = words[6];
        typeOfLecture = words[7];

        int pos = 8;
        System.out.println(day + " " + from + " - " + to + " " + room + " " + typeOfLecture);

        while (words[pos].length() != 2 || words[pos].charAt(1) != '.') {

            nameOfLecture = nameOfLecture.concat(words[pos] + " ");
            pos++;
        }

        while (pos < words.length - 1) {
            String lecturer = "";
            lecturer = lecturer.concat(words[pos] + " ");
            pos++;
            lecturer = lecturer.concat(words[pos]);
            pos++;
            lecturers.add(lecturer);

        }
        return new Lesson(day, from, to, room, typeOfLecture, nameOfLecture, lecturers);
    }

    public ArrayList<String> getStingsFromBuffer(BufferedReader bufferedReader) throws IOException{
        ArrayList<String> htmlString  = new ArrayList<>();
        String ln;

        while ((ln=bufferedReader.readLine()) != null) {
            htmlString.add(ln);
        }
        return htmlString;
    }

    public void expandAutoComplete(ArrayList<String> htmlStrings) throws IOException {

        Log.v("myParser","returnPossibleChoises");

        ArrayList<String> possiblities = returnPossibleChoises(htmlStrings);
        table.modifyAutoComplete(possiblities);


    }

    public void parsehtml(ArrayList<String> htmlStrings) throws IOException {
       // htmlString  = new ArrayList<>();
        //htmlString = getStingsFromBuffer(bufferedReader);

        for(String line: htmlStrings){

            if(line.contains("<title>")){
                if(line.contains("Rozvrh - Rozvrh")){
                    haveSomethingToSave = false;
                    morePossiblities = true;
                    return;
                }else{
                    urlForTxt = getUrl(htmlStrings, type);
                    morePossiblities = false;
                    haveSomethingToSave = true;
                }
            }
        }
    }



    //public void parsetxt(BufferedReader bufferedReader) throws IOException {
    public void parsetxt(ArrayList<String> htmlStrings) throws IOException {
       // htmlString  = htmlStrings;
        //String ln;

       // while ((ln=bufferedReader.readLine()) != null) {

        //    htmlString.add(ln);
        //}
            currentTimetable = new Timetable(name, type);
        for(String lineTextOfLecture: htmlStrings){
            Log.v("line",lineTextOfLecture);
            currentTimetable.addToTable(parseLineOfText(lineTextOfLecture));
        }
        //saveTable();

        table.modifyTable(currentTimetable);
    }


    public String getUrl(ArrayList<String> linesOfHtml, String type){

        for(String line: linesOfHtml){
            if(line.contains("/rozvrh/duplikovat")){
                name = line.substring(line.indexOf(type+'/')+type.length()+1, line.indexOf("/rozvrh/duplikovat"));

                urlForTxt = "https://candle.fmph.uniba.sk/"+type+"/"+name+".txt";
            }
        }
        return urlForTxt;
    }



    public String getUrlForTxt(){
        return urlForTxt;
    }

    public Timetable textToClasses(ArrayList<String> textFormat){


                for(String lineTextOfLecture: textFormat){
                    currentTimetable.addToTable(parseLineOfText(lineTextOfLecture));
                }


        return currentTimetable;
    }


    public boolean moreResults(){
        return morePossiblities;
    }



    public void saveTable(){
        for(Timetable table: allTimetables){
            if(table.getName().equals(currentTimetable.getName())) haveSomethingToSave = false;
        }
        if(haveSomethingToSave){

            allTimetables.add(currentTimetable);
        }
    }


}
