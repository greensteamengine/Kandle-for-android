package com.tomi.firsttest;

        import java.util.ArrayList;

        import android.app.Activity;
        import android.content.Context;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoglayout);

        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

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

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.rychla_volba, volbaArrayList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Volba country = (Volba) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

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
                        Volba country = (Volba) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Volba country = countryList.get(position);
            holder.code.setText(" (" +  country.getType() + ")");
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Volba> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Volba country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

}
