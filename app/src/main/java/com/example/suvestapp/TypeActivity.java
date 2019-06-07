package com.example.suvestapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

public class TypeActivity extends AppCompatActivity {

    Cursor cursor;
    ProductAdapter adapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // Gets the users choice using an Intent
        Intent intent = getIntent();
        String choice = (String) intent.getSerializableExtra("choice");

        // Declaration and initialization of a db object
        db = DatabaseHelper.getInstance(this);
        cursor = db.selectAll(choice);

        // Declaration, initialization and assignment of a new EntryAdapter object
        GridView gridview = findViewById(R.id.gridview);
        adapter = new ProductAdapter(this, cursor);
        gridview.setAdapter(adapter);
    }


}
