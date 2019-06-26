package com.example.suvestapp;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/** MainActivity
 *
 * This Class allows the user to choose between the different Categories provided in the Category Enum. After making
 * a choice, the user is redirected to the next activity (TypeActivity). The user can also add new screenshots to the
 * application, using the floating action button.
 */
public class MainActivity extends AppCompatActivity {

    /** onCreate
     *
     * Sets the UI elements, and populates the listView using an ArrayAdapter. Also implements the floating action button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes the ArrayAdapter for the CategoriesListView and adds all the categories from the Category Enum
        ArrayList<String> CategoryObjects = new ArrayList<>();
        for (Category category: Category.values()) {
            CategoryObjects.add(category.getCategory());
        }
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CategoryObjects);

        // Initializing and assigning the FloatingActionButton
        FloatingActionButton MainActivityFab = findViewById(R.id.floatingActionButton);

        // Setting the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Setting the OnclickListener for the FloatingActionButton MainActivityFab
        MainActivityFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // On button press, allows the user to select one or more pictures from the gallery of their choice
                Intent intent;

                // Uses the Intent.Action_Open_Document functionality if the user has Android version 19 or above
                if (Build.VERSION.SDK_INT >= 19){
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }

                // Uses the deprecated Android functionality Action_Get_Content
                else {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                }
                startActivityForResult(intent, 1);
            }
        });

        // Setting the ArrayAdapter categoriesAdapter to the ListView categoriesListView
        ListView categoriesListview = findViewById(R.id.CategoriesListview);
        categoriesListview.setAdapter(categoriesAdapter);

        // Sets an OnItemClickListener to the ListView categoriesListView
        AdapterView.OnItemClickListener listViewListener = new clickViewListener();
        categoriesListview.setOnItemClickListener(listViewListener);
    }

    /** clickViewListener
     *
     * Once the user selects a Category, transfers them to the TypeActivity.
     */
    private class clickViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Saves the users choice as a String and transfers the user to the next activity using Intent
            String clickedCategory = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("choice", clickedCategory);
            startActivity(intent);
        }
    }

    /** clickViewListener
     *
     * Implements the OnResultListener for the picture selection functionality. Depending on whether or not
     * the user has selected multiple images, adds flags to these images (Uri's) and stores these in an ArrayList of Uri's.
     * The user is then transferred to the AddInformationActivity. The ArrayList is also transferred to the next Activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Checks the request to respond to
        if (requestCode == 1 && resultCode == RESULT_OK) {

            // Passes the image selected by the user to the AddInformationActivity
            // If the user selected more than one image, iterates through all images in the ClipData and adds them to an ArrayList of Uri's
            ArrayList<Uri> uris = new ArrayList<>();
            ClipData cd = data.getClipData();

            // If the user selected one image
            if ( cd == null ) {
                Uri uri = null;
                uri = data.getData();
                uris.add(uri);
            }

            // If the user selected more than one image
            else {
                for (int i = 0; i < cd.getItemCount(); i++) {
                    ClipData.Item item = cd.getItemAt(i);
                    Uri uri = item.getUri();
                    uris.add(uri);
                }
            }

            if(Build.VERSION.SDK_INT >= 19) {

                // Adds an intent flag if the Android version is 19 or higher
                final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                ContentResolver resolver = this.getContentResolver();
                for (Uri uri : uris) {
                    resolver.takePersistableUriPermission(uri, takeFlags);
                }
            }

            // Adds the ArrayList of Uri's to an intent, and transfers the user to the next activity
            Intent secondintent = new Intent(MainActivity.this, AddInformationActivity.class);
            secondintent.putExtra("imageUri", uris);
            startActivity(secondintent);
        }
    }
}
