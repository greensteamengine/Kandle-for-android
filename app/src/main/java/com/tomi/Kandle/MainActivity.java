package com.tomi.Kandle;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    public class MainActivity extends  Application {
//public class MainActivity extends FragmentActivity implements DownloadCallback {

   // public class MainActivity extends FragmentActivity{

    Spinner moznosti_spinner;
    ListView rychla_volba;
    Button buttonVolbaDialog;
    Button tableSwitch;// = (Button) findViewById(R.id.tyzden);
    Button buttonPrevious;// = (Button) findViewById(R.id.prev);
    Button buttonNext;// = (Button) findViewById(R.id.next);
    Button buttonSearch;
    Button buttonSave;
    EditText searchText;

    Context context;//  = getApplicationContext();

    //int id;

    //TODO make save load
    TableLayout layout;// = (TableLayout)findViewById(R.id.mTlayout);
    Table table;// = new Table(layout, context, tableSwitch, buttonPrevious, buttonNext);
    Parser parser;// = new Parser(table);





    public Boolean amIConnectedToInternet() {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        if(parser != null && table != null){
           // savedInstanceState.putSerializable("teachers", parser.giveTeachers());
           // savedInstanceState.putSerializable("rooms", parser.giveRooms());
            savedInstanceState.putSerializable("allTables", parser.giveAllTimetables());
            savedInstanceState.putSerializable("currentTable", parser.getTable().getWholeTable());
            savedInstanceState.putBoolean("onlyDay", parser.getTable().getOnlyDay());
            savedInstanceState.putInt("id", parser.getTable().getDay());
            savedInstanceState.putSerializable("table", parser.getCurrentTimetable());
        }

        // etc.
        super.onSaveInstanceState(savedInstanceState);
    }
    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(parser != null && table != null) {
           /// parser.setTeachers((ArrayList<Timetable>) savedInstanceState.getSerializable("teachers"));
           // parser.setRooms((ArrayList<Timetable>) savedInstanceState.getSerializable("rooms"));
            parser.setAllTimetables((ArrayList<Timetable>) savedInstanceState.getSerializable("allTables"));
            parser.getTable().setWholeTable((ArrayList<ArrayList<String>>)savedInstanceState.getSerializable("currentTable"));
            parser.getTable().setOnlyDay(savedInstanceState.getBoolean("onlyDay"));
            parser.getTable().setday(savedInstanceState.getInt("id"));
            parser.setCurrentTimetable((Timetable) savedInstanceState.getSerializable("table"));
            table.createTable(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        rychla_volba = new ListView(this);

        //final TableLayout layout = (TableLayout)findViewById(R.id.mTlayout);
        layout = (TableLayout)findViewById(R.id.mTlayout);

        tableSwitch = (Button) findViewById(R.id.tyzden);
        buttonPrevious = (Button) findViewById(R.id.prev);
        buttonNext = (Button) findViewById(R.id.next);

        //final Table table = new Table(layout, context, tableSwitch, buttonPrevious, buttonNext);
        table = new Table(layout, context, tableSwitch, buttonPrevious, buttonNext);

        table.createTable(false);

         parser = new Parser(table);




        // final ScrollView sv = new ScrollView(this);
        //final ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        //ScrollView sv = new ScrollView(this);
        //final TableLayout ll = new TableLayout(this);
        //HorizontalScrollView hsv = new HorizontalScrollView(this);
        //final HorizontalScrollView hsv = new HorizontalScrollView(this);

        buttonVolbaDialog = (Button) findViewById(R.id.rychla_volba);
        buttonVolbaDialog.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Start NewActivity.class

                ArrayList<Timetable> joinedTable = new ArrayList<>();
                //joinedTable.addAll(parser.giveRooms());
               // joinedTable.addAll(parser.giveTeachers());



                Intent myIntent = new Intent(MainActivity.this,
                        ListViewCheckboxesActivity.class);
                myIntent.putExtra("myJoinedTable", parser.giveAllTimetables());
                //setResult(RESULT_OK, myIntent);

                startActivityForResult(myIntent, 3);



                //setResult(RESULT_OK, myIntent);
                //startActivity(myIntent);
               // myIntent.on


              //  if (requestCode == ActivityTwoRequestCode && resultCode == RESULT_OK && data != null) {
               //     num1 = data.getIntExtra(Number1Code);
               //     num2 = data.getIntExtra(Number2Code);
                //}

                //myIntent.putExtra("myJoinedTable", joinedTable);
                //myIntent.putExtra("selected", id);

            }
        });




        //TODO finish this
        buttonSearch = (Button) findViewById(R.id.vyhladat);
        searchText   = (EditText)findViewById(R.id.editText);
/*
        buttonSearch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String searchName = searchText.getText().toString();
             //   String type = ((String) moznosti_spinner.getIte.getSelectedItem());

              //  String id = moznosti_spinner.get(moznosti_spinner.getSelectedItemPosition()).getId();

                // String type = moznosti_spinner.get(moznosti_spinner.getSelectedItemPosition()).getId();

             //   parser.parseHTML(searchName, type);
            }
        });
        */



        buttonSearch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
          //      String id = ((String)moznosti_spinner.getSelectedItem());
              //  String id = moznosti_spinner.get(moznosti_spinner.getSelectedItemPosition()).getId();
               // String id = moznosti_spinne
             //

                moznosti_spinner = (Spinner) findViewById(R.id.moznosti_spinner);
                String result = moznosti_spinner.getSelectedItem().toString();
                String searchName = searchText.getText().toString();
               // String id = moznosti_spinner.;
              //  TextView textView = (TextView)moznosti_spinner.getSelectedView();
               // String wantid= textView.getResources().getResourceName(textView.getId());
             //
                String type;

                switch(result){

                    case("Vyucujuci"):type="ucitelia"; break;
                    case("Miestnost"):type="miestnosti"; break;
                    default: type="ucitelia"; System.out.println("wrong type, can not add");

                }
                if(amIConnectedToInternet()){
                    Log.v("connecttion: ", "ok");

                    if(!parser.IsParsing()){
                        parser.parseHTML(searchName, type);
                        table.createTable(false);
                    }
                }else{
                    Log.v("connection", "problem");
                }
                //table.createTable(false);
                //buttonSearch.setText("id"+type);
            }
        });




        buttonPrevious.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.shiftDayBack();
                table.createTable(false);
            }
        });

        buttonNext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.shiftDayForward();
                table.createTable(false);
            }
        });

        tableSwitch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
              //  table.changeView(tableSwitch, buttonPrevious, buttonNext);
                table.createTable(true);
            }
        });


        buttonSave = (Button) findViewById(R.id.save);
        buttonSave.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //  table.changeView(tableSwitch, buttonPrevious, buttonNext);
                parser.saveTable();
            }



        });




        Spinner spinner = (Spinner) findViewById(R.id.moznosti_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moznosti, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("??????", "can i get through???????????");
        Log.v("data", "requestcode: "+requestCode+" resultCode: "+ resultCode);

        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {

            Log.v("hahahah", "got hereeeee");
            //Bundle b = ;

            parser.setAllTimetables((ArrayList<Timetable>) data.getExtras().getSerializable("myJoinedTable"));// = data.getIntExtra(Number1Code);
            int id = data.getExtras().getInt("selected");//.getSerializable("selected");
            //parser.setAllTimetables();
            if(id != -1){
                parser.setCurrentTimetable(parser.getConcreteTimetable(id));
            }
            table.createTable(false);
        }
    }



}