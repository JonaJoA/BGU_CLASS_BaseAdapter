package com.jonajoapps.notes_baseadapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    /**
     * the adapter that will connect our SQL DB to the listview
     */
    private CustomAdapter myAdapter;

    /**
     * The model
     * in this case we store our objects in SQL DataBase
     */
    private ArrayList<LineObject> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View
        ListView listView = (ListView) findViewById(R.id.myList);
        Button add = (Button) findViewById(R.id.addButton);
        final EditText input = (EditText) findViewById(R.id.inputLine);

        model = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Here we handle a list view item click
                Log.e(TAG, "ListView Item position:" + position + " was clicked");
            }
        });

        /**
         * Here is where we add new item to the Model Array
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = input.getText().toString();
                if (str.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Cannot add empty string", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Create an object
                LineObject obj = new LineObject(str);

                //add the object to the Array
                model.add(0, obj);

                //Tell the adapter to refresh the view
                myAdapter.notifyDataSetChanged();
            }
        });


        //Create the adapter
        myAdapter = new CustomAdapter();

        //connect the adapter to the ListView
        listView.setAdapter(myAdapter);
    }

    private class CustomAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;

        public CustomAdapter() {
            layoutInflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return model.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.e("GetView", "position " + position);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.singel_line, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView date = (TextView) convertView.findViewById(R.id.date);


            //attache the model to the view
            title.setText(model.get(position).title);
            date.setText(model.get(position).date.toString());

            return convertView;
        }
    }

}
