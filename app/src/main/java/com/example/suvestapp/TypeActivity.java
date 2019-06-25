package com.example.suvestapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class TypeActivity extends AppCompatActivity {

    // Initializes the global variables
    Cursor cursor;
    ProductAdapter adapter;
    DatabaseHelper db;
    String choice;
    GridView gridView;

    // Creates a new PreferenceHelper object
    PreferenceHelper preferenceHelper = new PreferenceHelper("none", "none", "none", "none");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // Setting the UI elements, and assigns Listeners to the GridView gridView
        gridView = findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new gridItemClickListener());
        gridView.setOnItemLongClickListener(new gridItemLongClickListener());

        // Gets the users choice of type using an Intent
        Intent intent = getIntent();
        choice = (String) intent.getSerializableExtra("choice");
        preferenceHelper.setType(choice);

        // Calls the databaseInitializer method using the preferenceHelper object
        databaseInitializer(preferenceHelper);
    }

    public void buttonColor(View view) {

        // Allows the user to filter on the color of the products
        //Creating the instance of PopupMenu
        PopupMenu popupColor = new PopupMenu(TypeActivity.this, view);

        //Inflating the Popup using xml file
        // Requests the colors available of one particular type of clothing and removes duplicate entries
        db = DatabaseHelper.getInstance(this);
        ArrayList<String> stringArrayList = removeDuplicates(db.stringDatabaseLookup(choice, "Color"));

        // Adds all the colors contained in the stringArrayList
        for(String item: stringArrayList) {
            popupColor.getMenu().add(item);
        }

        // Registering when the user makes a choice in the PopupMenu popupColor
        popupColor.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                // When the user makes a choice, sets this preference in the PreferenceHelper object
                // Reloads the GridView using the updated preferences
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

        // Adds the elements to the set
        set.addAll(list);

        // Clear the list
        list.clear();

        // Adds the elements of set with no duplicates to the list
        list.addAll(set);

        // Returns the list
        return list;
    }

    public void buttonRetailer (View view) {

        // Allows the user to filter on the retailer of the products
        //Creating the instance of PopupMenu
        PopupMenu popupRetailer = new PopupMenu(TypeActivity.this, view);

        //Inflating the Popup using xml file
        // Requests the retailers available of one particular type of clothing and removes duplicate entries
        db = DatabaseHelper.getInstance(this);
        ArrayList<String> stringArrayList = removeDuplicates(db.stringDatabaseLookup(choice, "Retailer"));

        // Adds all the retailers contained in the stringArrayList
        for(String item: stringArrayList) {
            popupRetailer.getMenu().add(item);
        }

        // Registering when the user makes a choice in the PopupMenu popupRetailer
        popupRetailer.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                // When the user makes a choice, sets this preference in the PreferenceHelper object
                // Reloads the GridView using the updated preferences
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

        //Inflating the Popup using the xml file
        popupSort.getMenuInflater().inflate(R.menu.popup_sort, popupSort.getMenu());

        // Registering when the user makes a choice in the PopupMenu popupRetailer
        popupSort.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                // When the user makes a choice, sets this preference in the PreferenceHelper object
                // Reloads the GridView using the updated preferences
                preferenceHelper.setFormat(item.getTitle().toString());
                databaseInitializer(preferenceHelper);
                return true;
            }
        });

        popupSort.show();
    }

    public void buttonReset (View view) {

        // When pressed, resets all preferences except for the type, in the current preferenceHelper object
        // Reloads the GridView using the updated preferences
        preferenceHelper.preferenceHelperResetter();
        databaseInitializer(preferenceHelper);
    }

    public void databaseInitializer (PreferenceHelper preferenceHelper) {

        // Method used to fill the GridView with screenshots, using the PreferenceHelper object to filter which screenshots are shown
        // Declaration and initialization of a db object, fetching the table dependent on the choice of the user
        db = DatabaseHelper.getInstance(this);
        cursor = db.selectAll(preferenceHelper);

        // Setting the UI element counting the number of results of the query
        TextView numberofscreenshots = findViewById(R.id.numberScreenshots);

        if (cursor != null) {
            numberofscreenshots.setText(String.valueOf(cursor.getCount()));

            // Declaration, initialization and assignment of a new ProductAdapter object
            adapter = new ProductAdapter(this, cursor);
            gridView.setAdapter(adapter);
        }
    }

    private class gridItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // Deletes a particular entry on a longItemPress by the user
            SQLiteCursor clickedObject = (SQLiteCursor) parent.getItemAtPosition(position);
            Integer Id = clickedObject.getInt(0);

            // Calls the deleter functionality using the Id extracted
            db.deleter(Id);

            // Reloads the GridView using the updated preferences
            databaseInitializer(preferenceHelper);
            return true;
        }
    }

    private class gridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Implements the gridItemClickListener functionality
            GridView gridView = findViewById(R.id.gridview);

            // Stores the users' choice in an SQLiteCursor
            SQLiteCursor clickedObject = (SQLiteCursor) parent.getItemAtPosition(position);

            // Extracts all the relevant fields from the SQLiteCursor
            Integer Id = clickedObject.getInt(0);
            String Type = clickedObject.getString(2);
            String Retailer = clickedObject.getString(3);
            String Color = clickedObject.getString(7);
            Float Price = clickedObject.getFloat(4);
            String Uri = clickedObject.getString(5);
            String URL = clickedObject.getString(6);
            String Timestamp = clickedObject.getString(8);

            // Adds all the extracted information the an ArrayList of Strings
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
            // Adds the ArrayList of Strings to the intent
            Intent intent = new Intent(TypeActivity.this, DetailActivity.class);
            intent.putStringArrayListExtra("clickedObject", clickedObjectArrayList);
            startActivity(intent);
        }
    }
}

