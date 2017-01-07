package com.tomi.firsttest;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
//public class MainActivity extends FragmentActivity implements DownloadCallback {

   // public class MainActivity extends FragmentActivity{

            Spinner moznosti_spinner;
    ListView rychla_volba;
    Button buttonVolbaDialog;
    Button tableSwitch;
    Button buttonPrevious;
    Button buttonNext;
    Button buttonSearch;
    EditText searchText;

     Context context;


    public Boolean amIConnectedToInternet() {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    /*
   // @Override
  /  public void updateFromDownload(String result) {
        // Update your UI here based on result of download.
   // }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }
    */
    /*
    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                ...
                break;
            case Progress.CONNECT_SUCCESS:
                ...
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                ...
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                ...
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                ...
                break;
        }
    }


    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        rychla_volba = new ListView(this);

        final TableLayout layout = (TableLayout)findViewById(R.id.mTlayout);
        final Table table = new Table(layout, context);

        table.createTable();


        //TODO make save load
        final Parser parser = new Parser();

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
                Intent myIntent = new Intent(MainActivity.this,
                        ListViewCheckboxesActivity.class);
                startActivity(myIntent);
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
                    case("Miestnosti"):type="miestnosti"; break;
                    default: type="ucitelia"; System.out.println("wrong type, can not add");

                }
                if(amIConnectedToInternet()){
                    Log.v("connecttion: ", "ok");



                    parser.parseHTML(searchName, type);
                }else{
                    Log.v("connecttion", "problem");
                }

                buttonSearch.setText("id"+type);



            }
        });







        tableSwitch = (Button) findViewById(R.id.tyzden);
        buttonPrevious = (Button) findViewById(R.id.prev);
        buttonNext = (Button) findViewById(R.id.next);

        buttonPrevious.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.shiftDayBack();
                table.createTable();
            }
        });

        buttonNext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.shiftDayForward();
                table.createTable();
            }
        });

        tableSwitch.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                table.changeView(tableSwitch, buttonPrevious, buttonNext);
                table.createTable();
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

    /*
    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
        // TODO Auto-generated method stub
        super.onPrepareDialog(id, dialog, bundle);

        switch(id) {
            case CUSTOM_DIALOG_ID:
                //
                break;
        }

    }
    */
    public void makeCellEmpty(TableLayout tableLayout, int rowIndex, int columnIndex) {
        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView) tableRow.getChildAt(columnIndex);

        // make it black
        textView.setBackgroundColor(Color.BLACK);
    }

    public void setHeaderTitle(TableLayout tableLayout, int rowIndex, int columnIndex) {

        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView) tableRow.getChildAt(columnIndex);

        textView.setText("Hello");
    }

    private TableLayout createTableLayout(String [] rv, String [] cv,int rowCount, int columnCount) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < rowCount; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.BLACK);

            for (int j= 0; j < columnCount; j++) {
                // 4) create textView
                TextView textView = new TextView(this);
                //  textView.setText(String.valueOf(j));
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1 + s2;
                int id = Integer.parseInt(s3);
                Log.d("TAG", "-___>"+id);
                if (i ==0 && j==0){
                    textView.setText("0==0");
                } else if(i==0){
                    Log.d("TAAG", "set Column Headers");
                    textView.setText(cv[j-1]);
                }else if( j==0){
                    Log.d("TAAG", "Set Row Headers");
                    textView.setText(rv[i-1]);
                }else {
                    textView.setText(""+id);
                    // check id=23
                    if(id==23){
                        textView.setText("ID=23");

                    }
                }

                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }

            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        return tableLayout;
    }

}