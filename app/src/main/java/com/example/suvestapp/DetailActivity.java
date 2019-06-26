package com.example.suvestapp;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    // Initializes the global variables
    String uRL;
    DatabaseHelper db;
    Integer idNumber;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Gets the ArrayList filled with information of the screenshot selected by the user
        Intent intent = getIntent();
        ArrayList<String> clickedObjectArrayList = new ArrayList<String>();
        clickedObjectArrayList = intent.getStringArrayListExtra("clickedObject");

        idNumber = Integer.parseInt(clickedObjectArrayList.get(0));
        uRL = clickedObjectArrayList.get(5);
        type = clickedObjectArrayList.get(7);

        // Creates the Strings which are displayed in the Activity
        String typeRetailer = type + " by " +  clickedObjectArrayList.get(1);
        String price = "$ " + clickedObjectArrayList.get(2);
        String date = "Date added is: " + clickedObjectArrayList.get(4);
        String color = "A " + clickedObjectArrayList.get(6) + " " + type + " by " + clickedObjectArrayList.get(1) + ". Screenshot added on: " + clickedObjectArrayList.get(4) + ".";

        // Initializing the UI elements
        TextView detailPrice = findViewById(R.id.detailPrice);
        TextView detailDate = findViewById(R.id.detailDate);
        TextView detailColor = findViewById(R.id.detailColor);
        TextView detailURL = findViewById(R.id.detailURL);
        TextView detailType = findViewById(R.id.detailType);
        Button detailBuy = findViewById(R.id.detailBuy);
        Button detailRemove = findViewById(R.id.detailRemove);
        ImageView detailImage = findViewById(R.id.detailImage);

        // Filling the UI elements
        detailPrice.setText(price);
        detailImage.setImageURI(Uri.parse(clickedObjectArrayList.get(3)));
        //detailDate.setText(date);
        detailColor.setText(color);
        detailType.setText(typeRetailer);

        // Checks if the user provided an URL
        if (clickedObjectArrayList.get(5).equals("")) {
            detailURL.setHint("No URL provided!");
        }
        else {
            detailURL.setText(clickedObjectArrayList.get(5));
        }
    }

    public void buyClicked (View view) {

        // If the user provided an URL
        if (!uRL.equals("")) {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(uRL));
            startActivity(viewIntent);
        }

        // Notify the user that no URL was provided using a toast
        else {
            Toast.makeText(this, "No uRL provided!", Toast.LENGTH_LONG).show();
        }
    }

    public void removeClicked (View view) {

        // Declaration and initialization of a db object
        db = DatabaseHelper.getInstance(this);

        // Deletes the row which contains the screenshot the user removed
        db.onDelete(idNumber);

        // Redirects the user back to the TypeActivity
        Intent intent = new Intent(DetailActivity.this, TypeActivity.class);
        intent.putExtra("choice", type);
        startActivity(intent);
    }
}
