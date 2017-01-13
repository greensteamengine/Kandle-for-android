package com.tomi.Kandle;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.view.View;
import android.widget.TableLayout;
import android.content.Intent;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Spinner moznosti_spinner;
    EditText searchText;
    Button buttonSearch;

    ListView rychla_volba;
    Button buttonVolbaDialog1;
    Button buttonVolbaDialog2;
    Button tableSwitch;
    Button buttonPrevious;
    Button buttonNext;
    Button searchMode;


    Button buttonSave;


    Context context;

    //TODO make save load
    TableLayout layout;
    static Table table;
    MyParser parser;
    MyThread thread;

    public Boolean amIConnectedToInternet() {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

       // if(parser != null && table != null){
            savedInstanceState.putSerializable("myParser", parser);

            savedInstanceState.putSerializable("allTables", parser.giveAllTimetables());
            savedInstanceState.putSerializable("currentTable", parser.getTable().getWholeTable());
            savedInstanceState.putSerializable("tableLayout", parser.getCurrentTimetable());
            savedInstanceState.putBoolean("onlyDay", parser.getTable().getOnlyDay());
            savedInstanceState.putInt("id", parser.getTable().getDay());

            savedInstanceState.putBoolean("searchMode", table.isSearchMode());

       // }
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
       // }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        rychla_volba = new ListView(this);
        layout = (TableLayout)findViewById(R.id.mTlayout);

        buttonVolbaDialog1 = (Button) findViewById(R.id.rychla_volba1);
        buttonVolbaDialog2 = (Button) findViewById(R.id.rychla_volba2);
        buttonSearch = (Button) findViewById(R.id.vyhladat);
        buttonPrevious = (Button) findViewById(R.id.prev);
        buttonNext = (Button) findViewById(R.id.next);
        tableSwitch = (Button) findViewById(R.id.tyzden);
        buttonSave = (Button) findViewById(R.id.save);
        searchMode = (Button) findViewById(R.id.searchMode);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

       // tableLayout = new Table(layout, context, tableSwitch, buttonPrevious, buttonNext);
        table = new Table(this);
        table.hideButtons();
        parser = new MyParser(table);
       // thread = new MyThread(parser);
        parser.draw(false);



        searchMode.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                table.hideButtons();

            }
        });



        buttonVolbaDialog1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        ListViewCheckboxesActivity.class);
                myIntent.putExtra("myJoinedTable", parser.giveAllTimetables());
                startActivityForResult(myIntent, 3);
            }
        });

        buttonVolbaDialog2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent myIntent = new Intent(MainActivity.this,
                        ListViewCheckboxesActivity.class);
                myIntent.putExtra("myJoinedTable", parser.giveAllTimetables());
                //setResult(RESULT_OK, myIntent);

                startActivityForResult(myIntent, 3);

            }
        });








        //TODO finish this

        searchText   = (EditText)findViewById(R.id.editText);

        buttonSearch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                moznosti_spinner = (Spinner) findViewById(R.id.moznosti_spinner);
                String result = moznosti_spinner.getSelectedItem().toString();
                String searchName = searchText.getText().toString();
                table.hideButtons();
                
                if(amIConnectedToInternet()){
                    Log.v("connecttion: ", "ok");
                    parser.setData(result, searchName);
                    thread = new MyThread(parser);
                    thread.start();

                }else{
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
      //  Log.v("??????", "can i get through???????????");
      //  Log.v("data", "requestcode: "+requestCode+" resultCode: "+ resultCode);

        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {

           // Log.v("hahahah", "got hereeeee");

            parser.setAllTimetables((ArrayList<Timetable>) data.getExtras().getSerializable("myJoinedTable"));
            int id = data.getExtras().getInt("selected");
            Log.v("id arrived", " "+id);
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