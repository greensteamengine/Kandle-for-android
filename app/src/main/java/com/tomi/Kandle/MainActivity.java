package com.tomi.Kandle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.view.View;
import android.widget.TableLayout;
import android.content.Intent;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    //deklaracia Buttonov ktore pouzije aplikacia
    Spinner moznosti_spinner;
    //EditText searchText;
    AutoCompleteTextView searchText;
    Button buttonSearch;
    ListView rychla_volba;
    Button archiv1;
    Button archiv2;
    Button tableSwitch;
    Button buttonPrevious;
    Button buttonNext;
    Button searchMode;

    //Handler mainHandler = new Handler(this.getMainLooper());

    Button buttonSave;

    //trieda Table sa stara o kreslenie tabulky
    static Table table;

    Context context;
    TableLayout layout;
    MyParser parser;
    MyThread thread;

    static final ArrayList<String> possibleChoices = new ArrayList<String>();

    //testuje pripojenie
    public Boolean amIConnectedToInternet() {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() );
    }

    //uklada informacie, lebo sa pri zmene orientacii vymazu...
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        SaveLoad.saveState(savedInstanceState, parser, table);

       // if(parser != null && table != null){
        /*
            savedInstanceState.putSerializable("myParser", parser);

            savedInstanceState.putSerializable("allTables", parser.giveAllTimetables());
            savedInstanceState.putSerializable("currentTable", parser.getTable().getWholeTable());
            savedInstanceState.putSerializable("tableLayout", parser.getCurrentTimetable());
            savedInstanceState.putBoolean("onlyDay", parser.getTable().getOnlyDay());
            savedInstanceState.putInt("id", parser.getTable().getDay());

            savedInstanceState.putBoolean("searchMode", table.isSearchMode());
            */

       // }
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SaveLoad.loadState(savedInstanceState, parser, table);
        /*
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
        */
       // }
    }
    /*
    @Override
    protected void onStop(){
        super.onStop();
        SaveLoad.saveData(context, parser, table);
    }

    @Override
    public void onResume() {
        super.onStart();
        MyParser mp = SaveLoad.loadParser(context);
        if(mp!=null){
            parser = mp;
        }
        Table tab = SaveLoad.loadTable(context);
        if(tab!=null){
            table = tab;
        }
    }
    */



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //bugged autoCompleteTextView hint color - trying to fix based on
        // https://issuetracker.google.com/issues/36911171#c8
        super.onCreate(savedInstanceState);

        //searchText.setHintTextColor(Color.BLACK);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);

        context = getApplicationContext();
        rychla_volba = new ListView(this);
        layout = (TableLayout)findViewById(R.id.mTlayout);

        archiv1 = (Button) findViewById(R.id.rychla_volba1);
        archiv2 = (Button) findViewById(R.id.rychla_volba2);

        buttonSearch = (Button) findViewById(R.id.vyhladat);
        buttonPrevious = (Button) findViewById(R.id.prev);
        buttonNext = (Button) findViewById(R.id.next);
        tableSwitch = (Button) findViewById(R.id.tyzden);
        buttonSave = (Button) findViewById(R.id.save);
        searchMode = (Button) findViewById(R.id.searchMode);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        table = new Table(this);
        table.setCoices(possibleChoices);
        table.hideButtons();
        parser = new MyParser(table);
        parser.draw(false);



        searchMode.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.hideButtons();
            }
        });


        archiv1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent myIntent = new Intent(MainActivity.this,
                        ListViewCheckboxesActivity.class);

                myIntent.putExtra("myJoinedTable", parser.giveAllTimetables());
                startActivityForResult(myIntent, 3);
            }
        });

        archiv2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent myIntent = new Intent(MainActivity.this,
                        ListViewCheckboxesActivity.class);

                myIntent.putExtra("myJoinedTable", parser.giveAllTimetables());
                startActivityForResult(myIntent, 3);
            }
        });


        searchText   = (AutoCompleteTextView)findViewById(R.id.editText);
        //final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_dropdown_item_1line, possibleChoices);
        //searchText.setAdapter(adapter2);

        //ArrayAdapter<String> adapter = (ArrayAdapter<String>)searchText.getAdapter();
        //adapter.clear();

        //
        //adapter.addAll(possiblities);
        //for(String pos: possiblities){
        //    adapter.add(pos);
        //    Log.v("added posibility",pos);
        //}

        searchText.setOnClickListener(new Button.OnClickListener() {



            public void onClick(View arg0) {
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.super.getApplication(),
                        android.R.layout.simple_dropdown_item_1line, possibleChoices);
                //adapter2.clear();


                //List<String> arrayList = new ArrayList<>(); // this will be your arraylist
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
                searchText.setHintTextColor(Color.BLACK);
                //searchText.setHintTextColor(Color.BLUE);




                linkedHashSet.addAll(possibleChoices);
                possibleChoices.clear();
                possibleChoices.addAll(linkedHashSet);

               // adapter2.addAll(possibleChoices);

                searchText.setAdapter(adapter2);
                Log.v("reamining choices","it comes here");
                for(String pos: possibleChoices){
                    //     adapter.add(pos);
                    Log.v("still left",pos);
                }
                searchText.showDropDown();
                Log.v("num of items", String.valueOf(adapter2.getCount()));

            }
        });




        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onItemClick(AdapterView<?> av, View arg1, int index,
                                    long arg3) {
                String nameOfChoice = av.getItemAtPosition(index).toString();
                Log.v("clicked", nameOfChoice );

                String result = moznosti_spinner.getSelectedItem().toString();

                    parser.setData(result, nameOfChoice);

                ConcreteNameThread concreteThread = new ConcreteNameThread(parser);
                concreteThread.start();
                //Toast.makeText(getActivity(), "auto", Toast.LENGTH_LONG).show();
               // Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

               // String name  = map.get("Name");
               // String number = map.get("Phone");
               // atvPlaces.setText(""+name+"<"+number+">");

            }
        });

        buttonSearch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                moznosti_spinner = (Spinner) findViewById(R.id.moznosti_spinner);
                String result = moznosti_spinner.getSelectedItem().toString();
                String searchName = searchText.getText().toString();
                //table.hideButtons();

                if(amIConnectedToInternet()){
                    Log.v("connecttion: ", "ok");
                    parser.setData(result, searchName);
                    thread = new MyThread(parser);
                    thread.start();

                }else{
                    Toast.makeText(getApplicationContext(),
                            "no connection",
                            Toast.LENGTH_SHORT).show();
                    Log.v("connection", "problem");
                }
            }
        });


        buttonPrevious.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.shiftDayBack();
                parser.draw(false);
            }
        });


        buttonNext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.shiftDayForward();
                parser.draw(false);
            }
        });

        tableSwitch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                parser.draw(true);
            }
        });


        buttonSave.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                parser.saveTable();
            }



        });


        moznosti_spinner = (Spinner) findViewById(R.id.moznosti_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moznosti, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        moznosti_spinner.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.v("back pressed", "search mode is "+table.isSearchMode());
            if(!table.isSearchMode()){
                Log.v("got here" ,"why you no work");
                table.hideButtons();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {

            parser.setAllTimetables((ArrayList<Timetable>) data.getExtras().getSerializable("myJoinedTable"));
            int id = data.getExtras().getInt("selected");
            if(id != -1){
                parser.setCurrentTimetable(parser.getConcreteTimetable(id));
                if(table.isSearchMode()){
                    table.hideButtons();
                }
            }
            parser.draw(false);
        }
    }
}