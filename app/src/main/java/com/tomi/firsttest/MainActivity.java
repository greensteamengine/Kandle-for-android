package com.tomi.firsttest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Spinner moznosti_spinner;
    ListView rychla_volba;
    Button buttonVolbaDialog;
    Button celyTyzden;

    Button buttonOpenDialog;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;

    ListView dialog_ListView;

    String[] listContent = {
            "January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rychla_volba = new ListView(this);
        final Context context = getApplicationContext();

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
        /*
        TableLayout prices = (TableLayout)findViewById(R.id.mTlayout);
        prices.setStretchAllColumns(true);
        prices.bringToFront();
        for(int i = 0; i < 15; i++){

            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText("text");
            TextView c2 = new TextView(this);
            c2.setText("text");
            TextView c3 = new TextView(this);
            c3.setText("text");
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            prices.addView(tr);

        }

        */

        celyTyzden = (Button) findViewById(R.id.tyzden);
        celyTyzden.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /*
                String[] row = {"ROW1", "ROW2", "Row3", "Row4", "Row 5", "Row 6",
                        "Row 7"};
                String[] column = {"COLUMN1", "COLUMN2", "COLUMN3", "COLUMN4",
                        "COLUMN5", "COLUMN6"};
                int rl = row.length;
                int cl = column.length;

                Log.d("--", "R-Lenght--" + rl + "   " + "C-Lenght--" + cl);

                ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
                TableLayout tableLayout = createTableLayout(row, column, rl, cl);
                HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.horizontalView);

                //hsv.removeAllViews();
                hsv.addView(tableLayout);
               // sv.addView(hsv);
                sv.removeAllViews();
                setContentView(sv);
                */
                TableLayout layout = (TableLayout)findViewById(R.id.mTlayout);

                Table table = new Table(layout, context);
                table.dayTable();

                /*
                TableLayout prices = (TableLayout)findViewById(R.id.mTlayout);
                prices.setStretchAllColumns(true);
                prices.bringToFront();
                for(int i = 0; i < 13; i++) {
                    TableRow tr = new TableRow(c);
                    for (int j = 0; j < 6; j++) {
                        TextView view = new TextView(c);
                        view.setText("text: i=" + i + " j=" + j);
                        tr.addView(view);
                    }
                    prices.addView(tr);
                }
                */
                    /*
                    TableRow tr =  new TableRow(c);
                    TextView c1 = new TextView(c);
                    c1.setText("text");
                    TextView c2 = new TextView(c);
                    c2.setText("text");
                    TextView c3 = new TextView(c);
                    c3.setText("text");
                    tr.addView(c1);
                    tr.addView(c2);
                    tr.addView(c3);
                    prices.addView(tr);
                    */


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