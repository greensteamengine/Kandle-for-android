package com.tomi.Kandle;


        import android.util.Log;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.Serializable;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.logging.Level;
        import java.util.logging.Logger;

public class Parser implements Serializable{

    private ArrayList<Timetable> allTimetables;
    private Table table;
    private Timetable currentTimetable;// = new Timetable();
    private ThreadInternet thread;

    private Boolean parsingNow = false;
    private Boolean haveSomethingToSave = false;


    public Parser(Table table){
        allTimetables = new ArrayList<>();
        this.table = table;

    }

    public Timetable getConcreteTimetable(int index){
        return allTimetables.get(index);
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

    public void setAllTimetables(ArrayList<Timetable> allTimetables){
        this.allTimetables = allTimetables;
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
       // this.table.createTable(change);
    }


    /*
    when mor possiblities, then get them all and show
    TODO show menu for choosing possiblities...
     */
    public void printPossibleChoises(ArrayList<String> linesOfHtml, String type){

        ArrayList<String> possiblities = new ArrayList<>();

        for(String line: linesOfHtml){
            if(line.contains("<ul class=\"vysledky_hladania\">")){
                if(line.contains("</ul>")) break;
                if(line.contains("<li><a href=")){
                    possiblities.add(line.substring(line.indexOf(type+'/')+type.length()+1, line.indexOf("\">")));
                }
            }
        }
        if(possiblities.isEmpty()){
            Log.v("No choice found", "no choises found");
        }else{
            for(String s: possiblities){
                Log.v("More choises", s);
            }
        }
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
            Boolean finish = false;

            while (words[pos].length() != 2 && !finish) {
                if ((words[pos].length() == 2) && (words[pos].charAt(1) == '.')) {
                    break;
                }
                nameOfLecture = nameOfLecture.concat(words[pos] + " ");
                pos++;
            }
            Log.v("class name", nameOfLecture);

            while (pos < words.length - 1) {
                String lecturer = "";
                lecturer = lecturer.concat(words[pos] + " ");
                pos++;
                lecturer = lecturer.concat(words[pos]);
                pos++;
                lecturers.add(lecturer);

                Log.v("lecturer name", lecturer);
            }
        return new Lesson(day, from, to, room, typeOfLecture, nameOfLecture, lecturers);
    }



    public Timetable getTimetable(ArrayList<String> linesOfHtml,String  name, String type){

        currentTimetable = new Timetable(name, type);

        for(String line: linesOfHtml){
            if(line.contains("/rozvrh/duplikovat")){
                name = line.substring(line.indexOf(type+'/')+type.length()+1, line.indexOf("/rozvrh/duplikovat"));

                String urlString2 = "https://candle.fmph.uniba.sk/"+type+"/"+name+".txt";

                ThreadInternet thread = new ThreadInternet();
                thread.urlSet(urlString2);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<String> textFormat =  thread.getDataFromInternet();

                for(String lineTextOfLecture: textFormat){
                    currentTimetable.addToTable(parseLineOfText(lineTextOfLecture));
                }
            }
        }
        return currentTimetable;
    }



    public Timetable parseHTML(String  name, String type){

        parsingNow = true;

        currentTimetable = new Timetable(name, type);

        Log.v("parser", "parsing");

        String search = "";
        switch(type){

            case("ucitelia"): search = "showTeachers"; break;
            case("miestnosti"):search = "showRooms"; break;
            default: System.out.println("wrong type, can not add to parse HTML");

        }
        try {
            String urlString = "https://candle.fmph.uniba.sk/?"+search+"="+name;

            thread =  new ThreadInternet();
            thread.urlSet(urlString);
            thread.start();
            thread.join();

            ArrayList<String> linesOfHtml =  thread.getDataFromInternet();


            Log.v("parsing....", "before loop, legth of htmllines = " + linesOfHtml.size());

            for(String line: linesOfHtml){

                if(line.contains("<title>")){
                    if(line.contains("Rozvrh - Rozvrh")){
                        haveSomethingToSave = false;
                        printPossibleChoises(linesOfHtml, type);
                        break;
                    }else{
                        table.clearTable();
                        currentTimetable = getTimetable(linesOfHtml, name, type);
                        table.modifyTable(currentTimetable);
                        haveSomethingToSave = true;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failiure!");
            Log.v("Failiure!", "something "+ex.toString());
            parsingNow = false;
        }
        parsingNow = false;
        return currentTimetable;
    }

    public void saveTable(){
            for(Timetable table: allTimetables){
                if(table.getName() == currentTimetable.getName()) haveSomethingToSave = false;
            }
            if(haveSomethingToSave) allTimetables.add(currentTimetable);
    }
}
