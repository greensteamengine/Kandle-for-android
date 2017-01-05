package com.tomi.firsttest;


        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.logging.Level;
        import java.util.logging.Logger;
      //  import org.jsoup.Jsoup;

public class Parser {
    private ArrayList<Timetable> ucitel;
    private ArrayList<Timetable> miestnost;

    public Parser(){
        ucitel    = new ArrayList<>();
        miestnost = new ArrayList<>();

    }

  //  public void saveTable(String type, Timetable table){

   // }

    public Timetable parseHTML(String  name, String type){

        Timetable currentTimetable = new Timetable(name, type);

        Timetable myTable = new Timetable(name, type);
        String search = "";
        switch(type){

            case("ucitelia"): search = "showTeachers"; break;
            case("miestnosti"):search = "showRooms"; break;
            default: System.out.println("wrong type, can not add to parse HTML");

        }
        try {


            URL url = new URL("https://candle.fmph.uniba.sk/?"+search+"="+name);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
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

                                URL url2 = new URL("https://candle.fmph.uniba.sk/"+type+"/"+n+".txt");
                                InputStream is2 = url2.openStream();
                                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                                String line2;

                                String day = "", from = "", to = "", room = "", typeOfLecture = "", nameOfLecture = "";
                                ArrayList<String> lecturers = new ArrayList<>();

                                while ((line2 = br2.readLine()) != null) {
                                    nameOfLecture = "";

                                    System.out.println(line2);
                                    String[] words = line2.split("\\s+");
                                    // List<String> wordList = Arrays.asList(words);

                                    day = words[0];
                                    //day = wordList.

                                    from = words[1];
                                    to = words[3];
                                    room = words[6];
                                    typeOfLecture = words[7];

                                    // nameOfLecture = "";
                                    int pos = 8;
                                    System.out.println(day+" "+from+" - "+to+" "+room+" "+typeOfLecture);
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
                                currentTimetable.addTable(new Lesson(day, from, to, room, typeOfLecture, nameOfLecture, lecturers));
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failiure!");
        }
        return currentTimetable;
    }

    public void saveTable(Timetable timetable){
        String type = timetable.getType();

        switch(type){

            case("ucitelia"): ucitel.add(timetable); break;
            case("miestnosti"):miestnost.add(timetable); break;
            default: System.out.println("wrong type, can not add");

        }
    }
}
