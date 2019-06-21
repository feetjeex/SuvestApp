package com.example.suvestapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    String URL;
    DatabaseHelper db;
    Integer Id;
    String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        ArrayList<String> clickedObjectArrayList = new ArrayList<String>();
        clickedObjectArrayList = intent.getStringArrayListExtra("clickedObject");

        URL = clickedObjectArrayList.get(5);
        Id = Integer.parseInt(clickedObjectArrayList.get(0));
        Type = clickedObjectArrayList.get(7);

        TextView detailPrice = findViewById(R.id.detailPrice);
        TextView detailRetailer = findViewById(R.id.detailRetailer);
        TextView detailDate = findViewById(R.id.detailDate);
        TextView detailColor = findViewById(R.id.detailColor);
        TextView detailURL = findViewById(R.id.detailURL);
        TextView detailType = findViewById(R.id.detailType);
        Button detailBuy = findViewById(R.id.detailBuy);
        Button detailRemove = findViewById(R.id.detailRemove);
        ImageView detailImage = findViewById(R.id.detailImage);

        detailRetailer.setText(clickedObjectArrayList.get(1));
        detailPrice.setText(clickedObjectArrayList.get(2));
        detailImage.setImageURI(Uri.parse(clickedObjectArrayList.get(3)));
        detailDate.setText(clickedObjectArrayList.get(4));

        if (clickedObjectArrayList.get(5).equals("")) {
            detailURL.setHint("No URL provided!");
        }

        else {
            detailURL.setText(clickedObjectArrayList.get(5));
        }
        detailColor.setText(clickedObjectArrayList.get(6));
        detailType.setText(clickedObjectArrayList.get(7));
    }

    public void buyClicked (View view) {

        if (!URL.equals("")) {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(URL));
            startActivity(viewIntent);
        }

        else {
            Toast.makeText(this, "No URL provided!", Toast.LENGTH_LONG).show();
        }
    }

    public void removeClicked (View view) {
        // Declaration and initialization of a db object, fetching the table dependent on the choice of the user
        db = DatabaseHelper.getInstance(this);
        db.onDelete(Id);

        Intent intent = new Intent(DetailActivity.this, TypeActivity.class);
        intent.putExtra("choice", Type);
        startActivity(intent);
    }
}
