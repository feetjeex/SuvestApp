package com.example.suvestapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/** CategoryActivity
 *
 * Class which displays the Types of items the user can select from, dependent on the choice
 * of Category the user made in MainActivity. Once the user has chosen a Type, redirects the user
 * to the next activity.
 */
public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Gets the users choice using an Intent
        Intent intent = getIntent();
        String choice = (String) intent.getSerializableExtra("choice");

        // Decides with which elements to populate the types ArrayList.
        ArrayList<String> types = new ArrayList<>();
        types = typeLoader(choice, types);

        // Initializes the ArrayAdapter for the TypeListview
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types);

        // Setting the ArrayAdapter typesAdapter to the ListView typesListview
        ListView typesListview = findViewById(R.id.TypeListview);
        typesListview.setAdapter(typesAdapter);

        // Sets an OnItemClickListener to the ListView typesListview
        AdapterView.OnItemClickListener listViewListener = new clickViewListener();
        typesListview.setOnItemClickListener(listViewListener);
    }

    /** typeLoader
     *
     * A method which adds all the constants from the relevant Enum (Gifts, or Shoes for example) to an ArrayList,
     * which is then used by an adapter to show these types in a listView.
     */
    public ArrayList<String> typeLoader(String choice, ArrayList<String> types) {

        // Fills the ArrayList types with elements, depending on the choice of the user in MainActivity
        switch(choice) {

            case "Clothing":
                // Iterates through the clothing enum to fill the ArrayList types with the proper elements
                ArrayList<String> clothingObjects = new ArrayList<>();
                for (TypeClothing type: TypeClothing.values()) {
                    clothingObjects.add(type.getTypeclothing());
                }
                types.addAll(clothingObjects);
                break;

            case "Shoes":
                ArrayList<String> shoesObjects = new ArrayList<>();
                for (TypeShoes type: TypeShoes.values()) {
                    shoesObjects.add(type.getTypeshoes());
                }
                types.addAll(shoesObjects);
                break;

            case "Accessories":
                ArrayList<String> accessoriesObjects = new ArrayList<>();
                for (TypeAccessories type: TypeAccessories.values()) {
                    accessoriesObjects.add(type.getTypeaccessories());
                }
                types.addAll(accessoriesObjects);
                break;

            case "Gifts":
                ArrayList<String> giftsObjects = new ArrayList<>();
                for (TypeGifts type: TypeGifts.values()) {
                    giftsObjects.add(type.getTypegifts());
                }
                types.addAll(giftsObjects);
                break;

            case "Sport":
                ArrayList<String> sportsObjects = new ArrayList<>();
                for (TypeSport type: TypeSport.values()) {
                    sportsObjects.add(type.getTypesport());
                }
                types.addAll(sportsObjects);
                break;

            case "Wellness":
                ArrayList<String> wellnessObjects = new ArrayList<>();
                for (TypeWellness type: TypeWellness.values()) {
                    wellnessObjects.add(type.getTypewellness());
                }
                types.addAll(wellnessObjects);
                break;

            case "Interior":
                ArrayList<String> interiorObjects = new ArrayList<>();
                for (TypeInterior type: TypeInterior.values()) {
                    interiorObjects.add(type.getTypeinterior());
                }
                types.addAll(interiorObjects);
                break;
        }

        // Returns the ArrayList types, now filled with elements
        return types;
    }

    /** clickViewListener
     *
     * Once the user has chosen the Type of screenshot to view, transfers the user to the next activity.
     */
    private class clickViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Saves the users choice as a String and transfers the user to the next activity using Intent
            String clickedCategory = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(CategoryActivity.this, TypeActivity.class);
            intent.putExtra("choice", clickedCategory);
            startActivity(intent);
        }
    }
}
