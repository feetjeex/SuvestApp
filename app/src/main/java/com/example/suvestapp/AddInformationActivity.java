package com.example.suvestapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class AddInformationActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        // Gets the users choice using an Intent and
        Intent intent = getIntent();
        ArrayList<String> stringUri = intent.getStringArrayListExtra("imageUri");

        String imageUrl = intent.getStringExtra("imageUri");
        Uri mImageUri = Uri.parse(imageUrl);

        //
        ImageView infoImageView = findViewById(R.id.infoImageView);
        infoImageView.setImageURI(mImageUri);
    }
}
