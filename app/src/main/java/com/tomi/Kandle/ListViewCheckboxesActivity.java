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
    ArrayList<Timetable> joinedTable = new ArrayList<>();

    int id = -1;
    Intent myIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoglayout);

         myIntent = new Intent(ListViewCheckboxesActivity.this, MainActivity.class);
        joinedTable = (ArrayList<Timetable>)getIntent().getSerializableExtra("myJoinedTable");
        setResult(RESULT_OK, myIntent);


        displayListView();

        checkButtonClick();

        //TODO send modified data back

        myIntent.putExtra("myJoinedTable", joinedTable);
        myIntent.putExtra("selected", id);
    }

    private void displayListView() {

        dataAdapter = new MyCustomAdapter(this,
                R.layout.rychla_volba, joinedTable);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Timetable timetable = (Timetable) parent.getItemAtPosition(position);
                int idOfTable = joinedTable.indexOf(timetable);
                myIntent.putExtra("id", ListViewCheckboxesActivity.this.id);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + timetable.getName(),
                        Toast.LENGTH_LONG).show();

                myIntent.putExtra("selected", idOfTable);

                Log.v("control", "pressed textview, id: "+id+" text: "+timetable.getName());

                finish();
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Timetable> {

        private ArrayList<Timetable> tableList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Timetable> tableList) {
            super(context, textViewResourceId, tableList);
            this.tableList = new ArrayList<>();
            this.tableList.addAll(tableList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.rychla_volba, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.type);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Timetable country = (Timetable) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " inputStream " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                       // if(table.is)
                        Log.v("selected timetable", country.getName() + " index of country: " + joinedTable.indexOf(country));
                        joinedTable.get(joinedTable.indexOf(country)).check(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Timetable timetable = joinedTable.get(position);
            holder.code.setText(" ("+timetable.getType()+") ");
            holder.name.setText(timetable.getName());
            holder.name.setTag(timetable);

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

                    for(int j = 0; j< joinedTable.size(); j++){
                        if(joinedTable.get(j).checked){
                            joinedTable.remove(joinedTable.get(j));
                        }
                    }

                myIntent.putExtra("myJoinedTable", joinedTable);

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                dataAdapter.notifyDataSetChanged();
           // }
        }
    });
    }
}
