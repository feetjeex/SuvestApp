package com.example.suvestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Gets the users choice using an Intent
        Intent intent = getIntent();
        String choice = (String) intent.getSerializableExtra("choice");

        // Decides with which elements to populate the types Arraylist.
        ArrayList<String> types = new ArrayList<>();
        types = typeLoader(choice, types);

        // Initializes the ArrayAdapter for the TypeListview
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types);

        // Setting the ArrayAdapter typesAdapter to the ListView TypeListview
        ListView TypesListview = findViewById(R.id.TypeListview);
        TypesListview.setAdapter(typesAdapter);

        // Sets an OnItemClickListener to the ListView typeListview
        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        TypesListview.setOnItemClickListener(listViewListener);
    }

    public ArrayList<String> typeLoader(String choice, ArrayList<String> types) {

        // Fills the ArrayList types with elements, depending on the choice of the user in MainActivity
        switch(choice) {
            case "Clothing":
                types.addAll(Arrays.asList("Sweaters", "Shirts", "Jeans", "Socks", "Hoodies", "Trousers", "Jackets", "Training"));
                break;
            case "Shoes":
                types.addAll(Arrays.asList("Formal", "Sneakers", "Training", "Heels", "Placeholder 2"));
                break;
            case "Accesoires":
                types.addAll(Arrays.asList("Watches", "Bags", "Caps", "Wallets", "Placeholder 2"));
                break;
            case "Placeholder 1":
                types.addAll(Arrays.asList("Sweaters", "Shirts", "Accesoires", "Placeholder 1", "Placeholder 2"));
                break;
            case "Placeholder 2":
                types.addAll(Arrays.asList("Sweaters", "Shirts", "Accesoires", "Placeholder 1", "Placeholder 2"));
                break;
            default:
                types.addAll(Arrays.asList("Sweaters", "Shirts", "Accesoires", "Placeholder 1", "Placeholder 2"));
        }

        // Returns the ArrayList types, now filled with elements
        return types;
    }

    private class ClickViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // If the user clicks on one of the categories in the ListView
            // Saves the users choice as a String and transfers the user to the next activity using Intent
            String clickedCategory = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(CategoryActivity.this, TypeActivity.class);
            intent.putExtra("choice", clickedCategory);
            startActivity(intent);
        }
    }
}
