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

/** DetailActivity
 *
 * Allows the user to view a particular screenshot with more detail. Within this view, the user can then buy the product
 * (which will redirect them to the URL of the screenshot, if the user provided this information in the AddInformationActivity) and
 * to remove the screenshot.
 */

public class DetailActivity extends AppCompatActivity {

    // Initializes the global variables
    String uRL;
    DatabaseHelper db;
    Integer idNumber;
    String type;

    /** onCreate
     *
     * Sets the UI elements, and extracts information from the intent to fill these elements.
     */
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
        TextView detailColor = findViewById(R.id.detailColor);
        TextView detailURL = findViewById(R.id.detailURL);
        TextView detailType = findViewById(R.id.detailType);
        Button detailBuy = findViewById(R.id.detailBuy);
        Button detailRemove = findViewById(R.id.detailRemove);
        ImageView detailImage = findViewById(R.id.detailImage);

        // Filling the UI elements
        detailPrice.setText(price);
        detailImage.setImageURI(Uri.parse(clickedObjectArrayList.get(3)));
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

    /** buyClicked
     *
     * If the user presses the buy button, redirects them to the URL provided by the user. If no URL is provided,
     * makes a toast.
     */
    public void buyClicked (View view) {

        // If the user provided an URL
        if (!uRL.equals("")) {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(uRL));
            startActivity(viewIntent);
        }

        // Notify the user that no URL was provided
        else {
            Toast.makeText(this, "No URL provided!", Toast.LENGTH_LONG).show();
        }
    }

    /** removeClicked
     *
     * If the user presses the remove button, the entry is removed from the database using the deleter method in
     * the DatabaseHelper Class. The user is then redirected to the TypeActivity.
     */
    public void removeClicked (View view) {

        // Declaration and initialization of a db object
        db = DatabaseHelper.getInstance(this);

        // Deletes the row which contains the screenshot the user removed
        db.deleter(idNumber);

        // Redirects the user back to the TypeActivity
        Intent intent = new Intent(DetailActivity.this, TypeActivity.class);
        intent.putExtra("choice", type);
        startActivity(intent);
    }
}
