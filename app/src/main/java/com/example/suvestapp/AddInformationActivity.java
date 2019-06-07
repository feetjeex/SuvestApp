package com.example.suvestapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddInformationActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Initializes a new Product object
    Product product = new Product();

    // Sets the UI elements
    EditText InputRetailer;
    EditText InputPrice;
    EditText InputURL;
    Spinner CategorySpinner;
    Spinner TypeSpinner;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        // Gets the users choice using an Intent and
        Intent intent = getIntent();
        // ArrayList<String> stringUri = intent.getStringArrayListExtra("imageUri");

        // Extracts the String passed by the intent and parses it back to a Uri
        imageUri = intent.getStringExtra("imageUri");
        Uri mImageUri = Uri.parse(imageUri);

        // Sets the image in the layout to the Image passed by the intent
        ImageView infoImageView = findViewById(R.id.infoImageView);
        infoImageView.setImageURI(mImageUri);

        // Declare and initialize the new Adapter
        String[] CategoryObjects = {"Clothing", "Shoes", "Accesoires", "Placeholder 1", "Placeholder 2"};
        ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CategoryObjects);
        CategorySpinner = (Spinner) findViewById(R.id.CategorySpinner);
        CategorySpinner.setAdapter(CategoryAdapter);

        // Getting the Category the user choose from the spinner CategorySpinner
        String CategoryChosen = CategorySpinner.getSelectedItem().toString();

        // Decides with which elements to populate the TypeObjects Arraylist.
        ArrayList<String> TypeObjects = new ArrayList<>();
        TypeObjects = typeLoader(CategoryChosen, TypeObjects);
        ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TypeObjects);
        TypeSpinner = (Spinner) findViewById(R.id.TypeSpinner);
        TypeSpinner.setAdapter(TypeAdapter);

        // Setting the Buttons Remove and Next
        Button infoImageNext = (Button) findViewById(R.id.infoImageNext);
        Button infoImageRemove = (Button) findViewById(R.id.infoImageRemove);


        View.OnClickListener listener = new MyClickListener();
        infoImageNext.setOnClickListener(listener);

        // Setting the EditTexts
        InputRetailer = findViewById(R.id.InputRetailer);
        InputPrice = findViewById(R.id.InputPrice);
        InputURL = findViewById(R.id.InputURL);
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

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // Assigns values to the elements used in the Product
            String Category = CategorySpinner.getSelectedItem().toString();
            String Type = TypeSpinner.getSelectedItem().toString();
            String Retailer = InputRetailer.getText().toString();
            String URL = InputURL.getText().toString();
            Float Price = Float.valueOf(InputPrice.getText().toString());


            // Code to get the Timestamp field
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm - dd/MM/yyyy");
            String format = simpleDateFormat.format(new Date());

            // Storing all the data in a Product object
            product.setCategory(Category);
            product.setType(Type);
            product.setRetailer(Retailer);
            product.setURL(URL);
            product.setPrice(Price);
            product.setTimestamp(format);
            product.setImageUri(imageUri);

            // Calls the 'addProduct' method
            addProduct();
            Intent intent = new Intent(AddInformationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    // Method which inserts the selected Product in the database
    public void addProduct () {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.insert(product);
    }
}
