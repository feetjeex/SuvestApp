package com.example.suvestapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class TypeActivity extends AppCompatActivity {

    Cursor cursor;
    ProductAdapter adapter;
    DatabaseHelper db;
    String choice;
    GridView gridView;
    PreferenceHelper preferenceHelper = new PreferenceHelper("none", "none", "none", "none");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        gridView = findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new gridItemClickListener());
        // Gets the users choice using an Intent
        Intent intent = getIntent();
        choice = (String) intent.getSerializableExtra("choice");
        preferenceHelper.setType(choice);
        databaseInitializer(preferenceHelper);
    }

    public void buttonColor(View view) {

        //Creating the instance of PopupMenu
        PopupMenu popupColor = new PopupMenu(TypeActivity.this, view);
        //Inflating the Popup using xml file
        db = DatabaseHelper.getInstance(this);
        ArrayList<String> stringArrayList = removeDuplicates(db.stringDatabaseLookup(choice, "Color"));

        for(String item: stringArrayList) {
            popupColor.getMenu().add(item);
        }

        //registering popup with OnMenuItemClickListener
        popupColor.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(TypeActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                preferenceHelper.setColor(item.getTitle().toString());
                databaseInitializer(preferenceHelper);
                return true;
            }
        });

        popupColor.show();
    }

    public  ArrayList<String> removeDuplicates(ArrayList<String> list)
    {

        // Create a new LinkedHashSet
        Set<String> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }

    public void buttonRetailer (View view) {
        //Creating the instance of PopupMenu
        PopupMenu popupRetailer = new PopupMenu(TypeActivity.this, view);
        db = DatabaseHelper.getInstance(this);
        ArrayList<String> stringArrayList = removeDuplicates(db.stringDatabaseLookup(choice, "Retailer"));

        //Inflating the Popup using xml file
        for(String item: stringArrayList) {
            popupRetailer.getMenu().add(item);
        }

        //registering popup with OnMenuItemClickListener
        popupRetailer.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                preferenceHelper.setRetailer(item.getTitle().toString());
                databaseInitializer(preferenceHelper);
                return true;
            }
        });

        popupRetailer.show();
    }

    public void buttonSort(View view) {

        //Creating the instance of PopupMenu
        PopupMenu popupSort = new PopupMenu(TypeActivity.this, view);
        //Inflating the Popup using xml file
        popupSort.getMenuInflater().inflate(R.menu.popup_sort, popupSort.getMenu());

        //registering popup with OnMenuItemClickListener
        popupSort.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                preferenceHelper.setFormat(item.getTitle().toString());
                databaseInitializer(preferenceHelper);
                return true;
            }
        });

        popupSort.show();
    }

    public void buttonReset (View view) {
        preferenceHelper.preferenceHelperResetter();
        databaseInitializer(preferenceHelper);
    }

    public void databaseInitializer (PreferenceHelper preferenceHelper) {

        // Declaration and initialization of a db object, fetching the table dependent on the choice of the user
        db = DatabaseHelper.getInstance(this);
        cursor = db.selectAll(preferenceHelper);

        // Setting the number of screenshots
        TextView numberofscreenshots = findViewById(R.id.numberScreenshots);
        if (cursor != null) {
            numberofscreenshots.setText(String.valueOf(cursor.getCount()));
            // Declaration, initialization and assignment of a new ProductAdapter object
            adapter = new ProductAdapter(this, cursor);
            gridView.setAdapter(adapter);
        }
    }

    // Implements the gridItemClickListener feature
    private class gridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            GridView gridView = findViewById(R.id.gridview);
            SQLiteCursor clickedObject = (SQLiteCursor) parent.getItemAtPosition(position);
            Integer Id = clickedObject.getInt(0);
            String Type = clickedObject.getString(2);
            String Retailer = clickedObject.getString(3);
            String Color = clickedObject.getString(7);
            Float Price = clickedObject.getFloat(4);
            String Uri = clickedObject.getString(5);
            String URL = clickedObject.getString(6);
            String Timestamp = clickedObject.getString(8);

            Log.d("URL", "onItemClick: "+ URL);
            if (URL.equals("")) {
                Log.d("URL", "onItemClick: String is empty");
            }

            ArrayList<String> clickedObjectArrayList = new ArrayList<String>();
            clickedObjectArrayList.add(String.valueOf(Id));
            clickedObjectArrayList.add(Retailer);
            clickedObjectArrayList.add(String.valueOf(Price));
            clickedObjectArrayList.add(Uri);
            clickedObjectArrayList.add(Timestamp);
            clickedObjectArrayList.add(URL);
            clickedObjectArrayList.add(Color);
            clickedObjectArrayList.add(Type);


            // Using Intents to move from the MainActivity to ProfileActivity
            Intent intent = new Intent(TypeActivity.this, DetailActivity.class);
            intent.putStringArrayListExtra("clickedObject", clickedObjectArrayList);
            startActivity(intent);
        }
    }
}

