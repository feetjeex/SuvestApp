package com.example.suvestapp;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes the ArrayAdapter for the CategoriesListView
        ArrayList<String>categories = new ArrayList<>(Arrays.asList("Clothing", "Shoes", "Accesoires", "Placeholder 1", "Placeholder 2"));
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);

        // Initializing and assigning the FloatingActionButton
        FloatingActionButton MainActivityFab = findViewById(R.id.fab);

        // Setting the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Setting the OnclickListener for the FloatingActionButton MainActivityFab
        MainActivityFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // On button press, allows the user to select one or more pictures from the gallery of their choice
                Intent intent;

                if (Build.VERSION.SDK_INT >= 19){
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }
                else {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                }

                startActivityForResult(intent, 1);
            }
        });

        // Setting the ArrayAdapter categoriesAdapter to the ListView CategoriesListView
        ListView CategoriesListview = findViewById(R.id.CategoriesListview);
        CategoriesListview.setAdapter(categoriesAdapter);

        // Sets an OnItemClickListener to the ListView categoriesListView
        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        CategoriesListview.setOnItemClickListener(listViewListener);
    }

    private class ClickViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // If the user clicks on one of the categories in the ListView
            // Saves the users choice as a String and transfers the user to the next activity using Intent
            String clickedCategory = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("choice", clickedCategory);
            startActivity(intent);
        }
    }

    // Implements the OnResultListener for the picture selection functionality
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == 1 && resultCode == RESULT_OK) {

            // Passes the image selected by the user to the AddInformationActivity
            ArrayList<Uri> uris = new ArrayList<>();
            ClipData cd = data.getClipData();
            if ( cd == null ) {
                Uri uri = null;
                uri = data.getData();
                uris.add(uri);
            }

            else {
                for (int i = 0; i < cd.getItemCount(); i++) {
                    ClipData.Item item = cd.getItemAt(i);
                    Uri uri = item.getUri();
                    uris.add(uri);
                }
            }

            if(Build.VERSION.SDK_INT >= 19) {

                final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                ContentResolver resolver = this.getContentResolver();

                for (Uri uri : uris) {
                    resolver.takePersistableUriPermission(uri, takeFlags);
                }
            }

            // Log.d(TAG, String.format("This is the size of Uri's: %s", uris.size()));
            Intent secondintent = new Intent(MainActivity.this, AddInformationActivity.class);
            secondintent.putExtra("imageUri", uris);
            startActivity(secondintent);
        }
    }
}
