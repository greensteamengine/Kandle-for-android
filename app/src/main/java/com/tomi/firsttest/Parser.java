package com.tomi.firsttest;


        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.util.Log;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.Reader;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.logging.Level;
        import java.util.logging.Logger;

        import javax.net.ssl.HttpsURLConnection;
//  import org.jsoup.Jsoup;

public class Parser {
    private ArrayList<Timetable> ucitel;
    private ArrayList<Timetable> miestnost;

    ThreadInternet thread;

    public Parser(){
        ucitel    = new ArrayList<>();
        miestnost = new ArrayList<>();

    }


    String text = "";
    HttpURLConnection http = null;
    InputStream is = null;
    String nickUrl = "";
    int len = 0;





  //  public void saveTable(String type, Timetable table){

   // }


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

            ArrayList<String> al =  thread.getDataFromInternet();
            for(String s: al){
                Log.v("line: ", s);
            }

          //  InputStream is = url.openStream();

            InputStream is = thread.getIS();

           // InputStream is = getHtml(urlString);

            //String data = thread.getDataFromInternet();
            //System.out.println(data);
            //Log.v("code", data);
            Log.v("code ", "data buffer comes");

            //BufferedReader br = new BufferedReader(new InputStreamReader(is));
            BufferedReader br = thread.returnBuffer();
          //  br = new BufferedReader(is);

           // Log.v("html", t);
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



        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failiure!");
            Log.v("Failiure!", "something"+ex.toString());
        }
        //thread.interrupt();

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
