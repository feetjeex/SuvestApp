package com.example.suvestapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class TypeActivity extends AppCompatActivity {

    Cursor cursor;
    ProductAdapter adapter;
    DatabaseHelper db;
    String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // Gets the users choice using an Intent
        Intent intent = getIntent();
        GridView gridView = findViewById(R.id.gridview);
        choice = (String) intent.getSerializableExtra("choice");
        String random = "random";
        adapter = databaseInitializer(random);
        gridView.setAdapter(adapter);
    }

    public void buttonFilter(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_filter, popup.getMenu());
        popup.show();
    }

    public void buttonSort(View view) {

        //Creating the instance of PopupMenu
        PopupMenu popupSort = new PopupMenu(TypeActivity.this, view);
        //Inflating the Popup using xml file
        popupSort.getMenuInflater().inflate(R.menu.popup_sort, popupSort.getMenu());

        //registering popup with OnMenuItemClickListener
        popupSort.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(TypeActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                databaseInitializer(item.getTitle().toString());
                return true;
            }
        });

        popupSort.show();
    }

    public ProductAdapter databaseInitializer (String sort) {

        // Declaration and initialization of a db object, fetching the table dependent on the choice of the user
        db = DatabaseHelper.getInstance(this);
        cursor = db.selectAll(choice, sort);



        // Setting the number of screenshots
        TextView numberofscreenshots = findViewById(R.id.numberScreenshots);
        if (cursor != null) {
            numberofscreenshots.setText(String.valueOf(cursor.getCount()));
        }

        // Declaration, initialization and assignment of a new ProductAdapter object
        adapter = new ProductAdapter(this, cursor);
        return adapter;
    }
}

