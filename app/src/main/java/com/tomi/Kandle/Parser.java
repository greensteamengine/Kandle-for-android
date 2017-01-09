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
//  import org.jsoup.Jsoup;

public class Parser implements Serializable{
   // private ArrayList<Timetable> ucitel;
   // private ArrayList<Timetable> miestnost;
    private ArrayList<Timetable> allTimetables;
    private Table table;
    private Boolean parsingNow = false;
    private Boolean haveSomethingToSave = false;

    Timetable currentTimetable;


    ThreadInternet thread;

    public Parser(Table table){
       // ucitel    = new ArrayList<>();
       // miestnost = new ArrayList<>();
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

   // public ArrayList<Timetable> giveTeachers(){
   //     return ucitel;
  //  }

    public ArrayList<Timetable> giveAllTimetables(){
        return allTimetables;
    }

    public void setAllTimetables(ArrayList<Timetable> allTimetables){
        this.allTimetables = allTimetables;
    }

    public Table getTable(){
        return table;
    }

    public void setTable(Table table){
        this.table = table;
        haveSomethingToSave = true;
    }
/*
    public void setTeachers(ArrayList<Timetable> teachers){
        this.ucitel = teachers;
    }

    public void setRooms(ArrayList<Timetable> rooms){
        this.miestnost = rooms;
    }

    public ArrayList<Timetable> giveRooms(){
        return miestnost;
    }
    */

    public Boolean IsParsing(){
        return parsingNow;
    }


    String text = "";
    HttpURLConnection http = null;
    InputStream is = null;
    String nickUrl = "";
    int len = 0;




    public static InputStream getHtml(String url) throws IOException {
        // Build and set timeout values for the request.
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        return in;
    }

    public void draw(boolean change){
        this.table.createTable(change, this.currentTimetable);
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

                //Log.v("parsing....", line);

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

    public Boolean canSave(){
        return haveSomethingToSave;
    }

    public void saveTable(){
       // String type = currentTimetable.getType();

       // switch(type){
         //   case("ucitelia"):
               // Log.v("save", "why you no save!!!!!!!!!!!!");
            for(Timetable table: allTimetables){
                if(table.getName() == currentTimetable.getName()) haveSomethingToSave = false;
            }
            if(haveSomethingToSave) allTimetables.add(currentTimetable); //break;

           // case("miestnosti"):
           //     for(Timetable table: miestnost){
            //        if(table.getName() == currentTimetable.getName()) haveSomethingToSave = false;
            //    }
            //    if(haveSomethingToSave) miestnost.add(currentTimetable); break;
       // }
    }

    /*
    public Timetable parseHTML(String  name, String type){

        // new Thread() {
        //     public void run() {

        Timetable currentTimetable = new Timetable(name, type);

        Timetable myTable = new Timetable(name, type);
        String search = "";
        switch(type){

            case("ucitelia"): search = "showTeachers"; break;
            case("miestnosti"):search = "showRooms"; break;
            default: System.out.println("wrong type, can not add to parse HTML");

        }
        try {

            String urlString = "https://candle.fmph.uniba.sk/?"+search+"="+name;

            //  thread.urlSet(urlString);


            // URL url = new URL("https://candle.fmph.uniba.sk/?"+search+"="+name);

            //  thread.run();
            thread =  new ThreadInternet();
            thread.urlSet(urlString);
            thread.start();

            Log.v("code ", "data buffer comes-------------------");

            ArrayList<String> linesOfHtml =  thread.getDataFromInternet();

            for(String line: linesOfHtml){
                if(line.contains("<title>")){
                    if(line.contains("Rozvrh - Rozvrh")){

                    }else{

                    }
                }
            }

            String line;

            Boolean start = false;
            Boolean oneSearchResult = false;


            while ((line = br.readLine()) != null) {
                if(line.contains("<title>")){
                    if(!line.contains("Rozvrh - Rozvrh")){
                        while ((line = br.readLine()) != null) {
                            if(line.contains("/rozvrh/duplikovat")){
                                String n = line.substring(line.indexOf(type+'/')+type.length()+1, line.indexOf("/rozvrh/duplikovat"));
                                System.out.println("_"+n+"_");

                                String urlString2 = "https://candle.fmph.uniba.sk/"+type+"/"+n+".txt";

                                ThreadInternet thread2 = new ThreadInternet();
                                thread2.urlSet(urlString2);
                                thread2.start();


                                InputStream is2 = getHtml(urlString2);

                                //InputStream is2 = thread.getIS();
                                // thread2.interrupt();

                                // URL url2 = new URL("https://candle.fmph.uniba.sk/"+type+"/"+n+".txt");
                                // InputStream is2 = url.openStream();
                                //  thread.run();



                                //BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                                BufferedReader br2 = thread2.returnBuffer();
                                String line2;

                                String id = "", from = "", to = "", room = "", typeOfLecture = "", nameOfLecture = "";
                                ArrayList<String> lecturers = new ArrayList<>();

                                while ((line2 = br2.readLine()) != null) {
                                    nameOfLecture = "";

                                    System.out.println(line2);
                                    String[] words = line2.split("\\s+");
                                    // List<String> wordList = Arrays.asList(words);

                                    id = words[0];
                                    //id = wordList.

                                    from = words[1];
                                    to = words[3];
                                    room = words[6];
                                    typeOfLecture = words[7];

                                    // nameOfLecture = "";
                                    int pos = 8;
                                    System.out.println(id+" "+from+" - "+to+" "+room+" "+typeOfLecture);
                                    Boolean finish = false;
                                    while(words[pos].length()!=2 && !finish){
                                        //  while(words[pos].length()!=2 ){
                                        if(words[pos].length() == 2){
                                            finish = (words[pos].charAt(1) == '.');
                                        }
                                        nameOfLecture = nameOfLecture.concat(words[pos]+" ");
                                        //System.out.println(words[pos]);
                                        //System.out.println(words[pos]);
                                        pos++;
                                    }
                                    System.out.println(nameOfLecture);

                                    while(pos<words.length-1){
                                        String lecturer = "";//words[pos];
                                        lecturer =  lecturer.concat(words[pos]+" ");
                                        pos++;
                                        lecturer = lecturer.concat(words[pos]);
                                        pos++;
                                        lecturers.add(lecturer);
                                        //  System.out.println("lecturer: " + lecturer);
                                    }
                                }
                                currentTimetable.addTable(new Lesson(id, from, to, room, typeOfLecture, nameOfLecture, lecturers));
                            }
                        }
                    }
                }
            }



        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failiure!");
            Log.v("Failiure!", "something"+ex.toString());
        }
        //thread.interrupt();

        return currentTimetable;

    }*/


}
