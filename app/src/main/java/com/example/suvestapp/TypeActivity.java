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

/** TypeActivity
 *
 * Class used to display screenshots to the user, depending on the Category and Type the user requested.
 * Users are also able to filter the results, based on their Color and Retailer. The order in which the results are displayed
 * can also be changed, such as from the lowest price to the highest or the last added first.
 *
 * Each time a change is made to the filter, the PreferenceHelper object is updated, and a new request for screenshots is sent to
 * the database along with the updated PreferenceHelper.
 */

public class TypeActivity extends AppCompatActivity {

    // Initializes the global variables
    Cursor cursor;
    ProductAdapter adapter;
    DatabaseHelper db;
    String choice;
    GridView gridView;

    // Creates a new PreferenceHelper object
    PreferenceHelper preferenceHelper = new PreferenceHelper("none", "none", "none", "none");

    /** onCreate
     *
     * Sets the UI elements, and loads the screenshots into the image by calling the databaseInitializer method passing the
     * PreferenceHelper object as a parameter.
     */
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

    /** buttonColor
     *
     * A popupMenu which allows the user to filter the results on the basis of their Color. First, a database request is sent
     * requesting the colors available of one particular type of item. Subsequently removes duplicate entries using the removeDuplicates
     * method. These Colors are then displayed in the popupMenu.
     *
     * Once the user selects a Color, updates the preferenceHelper object and then refreshes the gridView by calling the
     * databaseInitializer functionality again, passing the updated PreferenceHelper object as a parameter.
     */
    public void buttonColor(View view) {

        //Creating the instance of PopupMenu
        PopupMenu popupColor = new PopupMenu(TypeActivity.this, view);

        //Inflating the Popup
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

    /** removeDuplicates
     *
     * Removes any duplicates from the ArrayList of Strings passed to it.
     */
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

    /** buttonRetailer
     *
     * A popupMenu which allows the user to filter the results on the basis of their Retailer. First, a database request is sent
     * requesting the retailers available of one particular type of item. Subsequently removes duplicate entries using the removeDuplicates
     * method. These Retailers are then displayed in the popupMenu.
     *
     * Once the user selects a Retailer, updates the preferenceHelper object and then refreshes the gridView by calling the
     * databaseInitializer functionality again, passing the updated PreferenceHelper object as a parameter.
     */
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

    /** buttonSort
     *
     * A popupMenu which allows the user to sort the results on the basis of four choices (Price high to low, low to high, data added last
     * added first and first added first).
     *
     * Once the user makes a choice, updates the preferenceHelper object and then refreshes the gridView by calling the
     * databaseInitializer functionality again, passing the updated PreferenceHelper object as a parameter.
     */
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

    /** buttonReset
     *
     * On click, resets the preferenceHelper object using preferenceHelperResetter method and then refreshes the listView by calling the
     * databaseInitializer functionality again, passing the updated PreferenceHelper object as a parameter.
     */
    public void buttonReset (View view) {

        preferenceHelper.preferenceHelperResetter();
        databaseInitializer(preferenceHelper);
    }

    /** databaseInitializer
     *
     * Method used to fill the GridView with screenshots, using the PreferenceHelper object passed to it to filter
     * which screenshots are shown.
     */
    public void databaseInitializer (PreferenceHelper preferenceHelper) {

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

    /** gridItemLongClickListener
     *
     * On long click, removes the current screenshot from the SQLite Database using the deleter method. After removing
     * the entry, calls the databaseInitializer to reset the gridView.
     */
    private class gridItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // Extracts the Id of the screenshot to be removed
            SQLiteCursor clickedObject = (SQLiteCursor) parent.getItemAtPosition(position);
            Integer Id = clickedObject.getInt(0);

            // Calls the deleter functionality using the Id extracted
            db.deleter(Id);

            // Reloads the GridView using the updated preferences
            databaseInitializer(preferenceHelper);
            return true;
        }
    }

    /** gridItemClickListener
     *
     * This method firsts extracts the necessary information from the screenshot the user selected. This information
     * is then stored in an arrayList. The user is then transferred to the DetailActivity, along with the arrayList.
     */
    private class gridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

