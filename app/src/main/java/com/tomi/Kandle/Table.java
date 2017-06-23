package com.tomi.Kandle;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;



public class Table implements Serializable {
    TableLayout tableLayout;
    Context context;
    Calendar calendar = Calendar.getInstance();

    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    Spinner moznosti_spinner;
    AutoCompleteTextView searchText;
    Button buttonSearch;

    Button tableSwitch;
    Button prev;
    Button next;
    Button save;
    Button rychla_volba;
    ScrollView scrollView;



    private ArrayList<ArrayList<String>> tableColumns = new ArrayList<ArrayList<String>>();
    private int day =  ((calendar.get(Calendar.DAY_OF_WEEK)-calendar.MONDAY + 7) % 7) + 1;
    private Boolean onlyDay;
    private Boolean searchingMode = false;
    private Timetable currentTimetable;

    public boolean isSearchMode(){
        return searchingMode;
    }

    public void setSearchMode(boolean sm){
        this.searchingMode = sm;
    }

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
    Activity activity;

    public Table(Activity activity){
        this.activity = activity;

        this.layout1 = (LinearLayout) activity.findViewById(R.id.linear1);
        scrollView = (ScrollView) activity.findViewById(R.id.scrollView);
        this.layout2 = (LinearLayout) activity.findViewById(R.id.linear2);
        this.layout3 = (LinearLayout) activity.findViewById(R.id.linear3);
        this.moznosti_spinner =(Spinner) activity.findViewById(R.id.moznosti_spinner);
        this.searchText = (AutoCompleteTextView)activity.findViewById(R.id.editText);
        this.buttonSearch = (Button)activity.findViewById(R.id.vyhladat);
        this.tableLayout =  (TableLayout)activity.findViewById(R.id.mTlayout);
        this.context = activity.getApplicationContext();
        this.onlyDay = true;
        this.tableSwitch = (Button)activity.findViewById(R.id.tyzden);
        this.prev = (Button)activity.findViewById(R.id.prev);
        this.next = (Button)activity.findViewById(R.id.next);
        this.save = (Button)activity.findViewById(R.id.save);
        this.rychla_volba = (Button)activity.findViewById(R.id.rychla_volba2);


        tableColumns = giveEmptyTable();
    }


    void hideButtons(){
        if(searchingMode){

            layout1.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);

            //searchText.showDropDown();

            searchingMode = false;
        }else{
            layout1.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            searchingMode = true;
        }

    }

    ArrayList<String> possibleChoices;

    public void setCoices(ArrayList<String> choices){
        possibleChoices =  choices;
    }
    /*
    void hideButtons(){
        if(searchingMode){
            layout1.setVisibility(View.INVISIBLE);
            moznosti_spinner.setVisibility(View.INVISIBLE);
            searchText.setVisibility(View.INVISIBLE);
            buttonSearch.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            if(onlyDay){
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                tableSwitch.setVisibility(View.VISIBLE);
            }

            searchingMode = false;
        }else{
            layout1.setVisibility(View.VISIBLE);
            moznosti_spinner.setVisibility(View.VISIBLE);
            searchText.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            //if(onlyDay){
                prev.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                tableSwitch.setVisibility(View.INVISIBLE);
            //}
            searchingMode = true;
        }

    }
    */

    /*
    public Table(TableLayout table, Context context, Button tableSwitch, Button prev, Button next){
        this.moznosti_spinner =(Spinner) activity.findViewById(R.id.moznosti_spinner);;
        this.searchText = (EditText)activity.findViewById(R.id.editText);;
        this.buttonSearch = (Button)activity.findViewById(R.id.vyhladat);;
        this.tableLayout =  (TableLayout)activity.findViewById(R.id.mTlayout);
        this.context = activity.getApplicationContext();
        this.onlyDay = true;
        this.tableSwitch = (Button)activity.findViewById(R.id.tyzden);
        this.prev = (Button)activity.findViewById(R.id.prev);
        this.next = (Button)activity.findViewById(R.id.next);
        save = (Button)activity.findViewById(R.id.save);
        rychla_volba = (Button)activity.findViewById(R.id.rychla_volba);


        tableColumns = giveEmptyTable();

    }
    */



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






    public ArrayList<ArrayList<String>> giveEmptyTable(){
        ArrayList<ArrayList<String>> newtTable = new ArrayList<ArrayList<String>>();

        ArrayList<String> timesOfClasses = new ArrayList<String>(Arrays.asList(
                "...", "  8:10", "  9:00", "  9:50", "10:40", "11:30", "12:20",
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
    }

    public void hideShow(){
        searchText.post(new Runnable() {
            public void run() {

                hideButtons();
            }
        });
    }

    public void modifyAutoComplete(final ArrayList<String>  possiblities){


        //searchText.setAdapter();
        //ArrayAdapter<String> adapter = (ArrayAdapter<String>)searchText.getAdapter();
        //adapter.clear();
        //adapter.addAll(possiblities);



        searchText.post(new Runnable() {
            public void run() {
                //myParser.expandAutoComplete(htmlString);
                //AutoCompleteTextView searchText = (AutoCompleteTextView)activity.findViewById(R.id.editText);
                //ArrayAdapter<String> adapter = (ArrayAdapter<String>)searchText.getAdapter();
                //adapter.clear();
                //adapter.addAll(possiblities);
                possibleChoices.clear();
                possibleChoices.addAll(possiblities);

                for(String pos: possiblities){
               //     adapter.add(pos);
                    Log.v("added posibility",pos);
                }
            }
        });
        //searchText.showDropDown();
        //textView.setAdapter(adapter);

        //ArrayAdapter<String> adapter = textView.getAdapter()

    }


    public void modifyTable(Timetable timetable){

        tableColumns = giveEmptyTable();
        currentTimetable = timetable;

        for(Lesson lesson: timetable.getLessons()){
            String day = lesson.getDay();
            int from = lesson.getSerialNumberOfStart();
            int to = lesson.getSerialNumberOfEnd();
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

    public Lesson getLesson(Integer begining, Integer day){
        return (currentTimetable != null) ? currentTimetable.getLesson(begining, day) : null;
    }

    public Lesson getOngoingLesson(int start, Integer day){
        return (currentTimetable != null) ? currentTimetable.getOngoingLesson(start, day) : null;
    }


    public void shiftDayBack(){
        if(day == 1)day = 5; else day -= 1;
    }

    public void shiftDayForward(){
        if(day == 5)day = 1; else day += 1;
    }

    public void createTable(Boolean change, Timetable timetable){

        if(timetable !=null)modifyTable(timetable);
        if(change)changeView();
        tableLayout.removeAllViews();
        if(onlyDay)dayTable(); else weekTable();
    }

    private void changeView(){
        onlyDay ^= true;

    }




    private void dayTable() {

        tableLayout.setStretchAllColumns(true);
        tableLayout.bringToFront();

        tableSwitch.setText("Celý týždeň");
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);


        if (day < 1 || day > 5) day = 1;

        TableRow tr = new TableRow(context);

        TextView viewEdge = new TextView(context);
        viewEdge.setText(tableColumns.get(0).get(0));
        viewEdge.setTextColor(Color.BLACK);
        viewEdge.setBackgroundColor(Color.GRAY);
        tr.addView(viewEdge);

        TextView viewDay = new TextView(context);
        viewDay.setText(tableColumns.get(day).get(0));
        viewDay.setTextColor(Color.BLACK);
        viewDay.setBackgroundColor(Color.GRAY);
        tr.addView(viewDay);
        tableLayout.addView(tr);

        Lesson current = null;
        Lesson prewCurrent = null;
        for (int i = 1; i < 15; i++) {

            tr = new TableRow(context);

            TextView viewTime = new TextView(context);
            viewTime.setText(tableColumns.get(0).get(i));
            viewTime.setTextColor(Color.BLACK);
            viewTime.setBackgroundColor(Color.GRAY);
            tr.addView(viewTime);

            /*
            //Lesson current = getLesson(i, day);

            if(currentTimetable != null){
                current=  currentTimetable.getLesson(i, day);
            }
            */
            current = getLesson(i, day);


            //if(current!=null){

           // }

            TextView room = new TextView(context);
            TextView name = new TextView(context);

            if(current != null){

                prewCurrent = current;
                room.setText(current.getRoom());
                room.setBackgroundColor(Color.GRAY);
                room.setTextColor(Color.BLACK);


                name.setText(current.getName());
                name.setTextColor(Color.BLACK);
                name.setBackgroundColor(Color.GRAY);
            }

            if (tableColumns.get(day).get(i).trim().length() > 0){
                room.setBackgroundColor(Color.GRAY);
                name.setBackgroundColor(Color.GRAY);
                responseToast(tr, prewCurrent);
            }
            tr.addView(room);
            tr.addView(name);

            tableLayout.addView(tr);
        }
    }

    private void weekTable(){
        tableLayout.setStretchAllColumns(true);

        tableLayout.bringToFront();


        tableSwitch.setText("Zobraziť iba deň");
        prev.setVisibility(View.GONE);
        next.setVisibility(View.GONE);


        for(int i = 0; i<15; i++){

            TableRow tr = new TableRow(context);

            for(int j = 0; j<6;j++){
                Lesson prewCurrent = null;
            //for(ArrayList<String> column: tableColumns){

                TextView view = new TextView(context);

                if(i == 0){
                    view.setText(tableColumns.get(j).get(i).substring(0, 2));
                }else{
                    Lesson current =  getLesson(i, j);


                    //responseToast(view, current);
                    if(current != null){
                        view.setText(current.room);
                    }
                    prewCurrent = getOngoingLesson(i,j);

                    responseToast(view, prewCurrent);
                    if(j == 0){
                        view.setText(tableColumns.get(j).get(i));
                    }
                   //
                }

                if (tableColumns.get(j).get(i).trim().length() > 0){
                    view.setBackgroundColor(Color.GRAY);
                   // view.setText(tableColumns.get(j).get(i));


                }//else{
                //    view.setBackgroundColor(Color.WHITE);
               // }
                //if(tableColumns.get(j).get(i) == " "){
                //}else{
                //}
                view.setTextColor(Color.BLACK);
                tr.addView(view);
            }
            tableLayout.addView(tr);
        }
    }

    private StringBuffer getResponseText( Lesson lesson){
        StringBuffer responseText = new StringBuffer();
        responseText.append(lesson.getName() + " (" + lesson.getType() + ")\n");
        responseText.append(lesson.getRoom() +"\n");
        responseText.append( lesson.getFrom() + " - " + lesson.getTo());
        return responseText;
    }

    private void responseToast(TableRow tr, final Lesson lesson){
         if(lesson != null) {
             tr.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(View v) {
                 Toast.makeText(context, getResponseText(lesson), Toast.LENGTH_SHORT).show();
                 }
             });
         }
    }

    private void responseToast(TextView tv, final Lesson lesson) {
        if (lesson != null) {
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(context, getResponseText(lesson), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
