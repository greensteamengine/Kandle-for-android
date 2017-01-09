package com.tomi.Kandle;

        import java.util.ArrayList;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.View.OnClickListener;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.AdapterView.OnItemClickListener;

public class ListViewCheckboxesActivity extends Activity {

    MyCustomAdapter dataAdapter = null;
    ArrayList<Timetable> joinedTable = new ArrayList<>();// (ArrayList<Timetable>)getIntent().getSerializableExtra("myJoinedTable");
    ArrayList<Boolean> checked = new ArrayList<>();
    int id = -1;
    Intent myIntent;
   // Boolean[] checked;

    //ArrayList<Timetable> joinedTable = new ArrayList<>();
    //joinedTable.addAll(parser.giveRooms());
    //joinedTable.addAll(parser.giveTeachers());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoglayout);

         myIntent = new Intent(ListViewCheckboxesActivity.this, MainActivity.class);
        //joinedTable.addAll(parser.giveRooms());
        //joinedTable.addAll(parser.giveTeachers());
        joinedTable = (ArrayList<Timetable>)getIntent().getSerializableExtra("myJoinedTable");
        setResult(RESULT_OK, myIntent);
        //checked = new Boolean[joinedTable.size()];
        for(int i = 0; i<joinedTable.size(); i++) {
        checked.add(false);
        }

        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();
       // nameButtonClick();

        //TODO send modified data back

        myIntent.putExtra("myJoinedTable", joinedTable);
        myIntent.putExtra("selected", id);
        //myIntent.putExtra()

    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Volba> volbaArrayList = new ArrayList<Volba>();
        Volba country = new Volba("vyucujuci","Rovan",false);
        volbaArrayList.add(country);
        country = new Volba("vyucujuci","Ostertag",true);
        volbaArrayList.add(country);
        country = new Volba("vyucujuci","Forisek",false);
        volbaArrayList.add(country);
        country = new Volba("vyucujuci","Sleziak",true);
        volbaArrayList.add(country);
        country = new Volba("miestnost","M-IV",true);
        volbaArrayList.add(country);
        country = new Volba("miestnost","F1",false);
        volbaArrayList.add(country);
        country = new Volba("miestnost","M-X",false);
        volbaArrayList.add(country);

        /*
        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.rychla_volba, volbaArrayList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        */
        dataAdapter = new MyCustomAdapter(this,
                R.layout.rychla_volba, joinedTable);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        ArrayAdapter myAdapter = new ArrayAdapter<>(this, R.layout.rychla_volba, joinedTable);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Timetable country = (Timetable) parent.getItemAtPosition(position);
                id = joinedTable.indexOf(country);
                myIntent.putExtra("id", ListViewCheckboxesActivity.this.id);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();

                Log.v("control", "pressed textview, id: "+id+" text: "+country.getName());

                finish();
            }
        });



    }
    /*
    private class MyCustomAdapter extends ArrayAdapter<Volba> {

    private ArrayList<Volba> countryList;

    public MyCustomAdapter(Context context, int textViewResourceId,
                           ArrayList<Volba> countryList) {
        super(context, textViewResourceId, countryList);
        this.countryList = new ArrayList<Volba>();
        this.countryList.addAll(countryList);
    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }
        */
        private class MyCustomAdapter extends ArrayAdapter<Timetable> {

            private ArrayList<Timetable> tableList;

            public MyCustomAdapter(Context context, int textViewResourceId,
                                   ArrayList<Timetable> tableList) {
                super(context, textViewResourceId, tableList);
                this.tableList = new ArrayList<Timetable>();
                this.tableList.addAll(tableList);
            }

            private class ViewHolder {
                TextView code;
                CheckBox name;
            }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.rychla_volba, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.type);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Timetable country = (Timetable) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                       // country.setSelected(cb.isChecked());
                        //checked.get(()) = ;
                        Log.v("selected timetable", country.getName()+" id: "+joinedTable.indexOf(country));
                         checked.set(joinedTable.indexOf(country), cb.isChecked());
                        //checked[] =
                    }
                });

                /*
                holder.code.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView cb = (TextView) v ;
                        ArrayList<Timetable> countryList = dataAdapter.tableList;
                        id = joinedTable.indexOf(cb.getText());
                        myIntent.putExtra("id", id);
                        finish();
                        //(ViewHolder)v.getClass().name;

                        Log.v("control", "pressed textview, id: "+id+" text: "+cb.getText());
                    }
                });
                */
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Timetable country = joinedTable.get(position);
           // holder.code.setText(" (" +  country.getType() + ")");
           // holder.name.setText(country.getName());
            holder.name.setText(country.getType());
            holder.code.setText(country.getName());
            //holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.deleteSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("Vymazali ste nasledujuce rozvrhy...\n");

                ArrayList<Timetable> countryList = dataAdapter.tableList;
                for(int i=0;i<countryList.size();i++){
                    Timetable country = joinedTable.get(i);
                    if(checked.get(i)){
                        joinedTable.remove(i);
                        checked.remove(i);
                   //     responseText.append("\n" + country.getName());
                    }
                    myIntent.putExtra("myJoinedTable", joinedTable);
                    //myIntent.putExtra("selected", id);
                }
                countryList = joinedTable;
                //for(Timetable timetable: joinedTable){
                  //  int index = joinedTable.indexOf(timetable);
                 //   if(){

                   // }
                //}

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                dataAdapter.notifyDataSetChanged();

            }
        });

    }

    //private void nameButtonClick() {
   ////     final Button myView = (TextView) findViewById(R.id.type);
   // }

}
