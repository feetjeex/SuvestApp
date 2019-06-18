package com.example.suvestapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddInformationActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Initializes a new Product object
    Product product;
    Bitmap bitmap;

    // Sets the UI elements
    EditText InputRetailer;
    EditText InputPrice;
    EditText InputURL;
    EditText InputColor;
    Spinner CategorySpinner;
    Spinner TypeSpinner;
    String imageUri;
    Uri mImageUri;
    private boolean mCategoryspinnerInitialized = true;
    ArrayList<String> types = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        // Gets the users choice using an Intent and
        Intent intent = getIntent();
        ArrayList<Uri> stringUri = intent.getParcelableArrayListExtra("imageUri");

        // Extracts the String passed by the intent and parses it back to a Uri
        mImageUri = stringUri.get(0);
        Log.d(TAG, "AddInformationActivity onCreate, stringUri = : " + mImageUri.toString());

        try {

            // Converting the Uri to a Bitmap
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
        }

        catch (Exception e) {
            Toast.makeText(this, "Conversion from Uri to Bitmap failed.", Toast.LENGTH_SHORT).show();
        }

        // Calls the OCRFunctionality to be performed on the screenshot
        OCRHelper ocrHelper = new OCRHelper(bitmap, this);
        product = ocrHelper.ProductReturner();

        // Sets the image in the layout to the Image passed by the intent
        ImageView infoImageView = findViewById(R.id.infoImageView);
        infoImageView.setImageURI(mImageUri);

        // Declare and initialize a new ArrayList CategoryObjects, and fill it with values from the Category Enum
        ArrayList<String> CategoryObjects = new ArrayList<>();
        for (Category category: Category.values()) {
            CategoryObjects.add(category.getCategory());
        }

        // Initializing and filling the ArrayAdapter CategoryAdapter with values from the ArrayList CategoryObjects
        ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CategoryObjects);
        CategorySpinner = (Spinner) findViewById(R.id.CategorySpinner);
        CategorySpinner.setAdapter(CategoryAdapter);

        // Initializes the ArrayList for TypeObjects
        ArrayList<String> TypeObjects = new ArrayList<>();
        TypeSpinner = (Spinner) findViewById(R.id.TypeSpinner);

        // If the product category has already been determined by the OCRHelper
        if (product.getCategory() != null) {
            CategorySpinner.setSelection(CategoryAdapter.getPosition(product.getCategory()));
        }

        // Getting the Category the user choose from the spinner CategorySpinner
        CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mCategoryspinnerInitialized) {
                    mCategoryspinnerInitialized = true;
                    return;
                }

                // Decides with which elements to populate the TypeObjects Arraylist.
                String type = CategorySpinner.getSelectedItem().toString();
                typeLoader(type);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // Setting the Buttons Remove and Next
        Button infoImageNext = (Button) findViewById(R.id.infoImageNext);
        Button infoImageRemove = (Button) findViewById(R.id.infoImageRemove);

        // Adding the OnClickListener to transfer the user to the next image
        View.OnClickListener listener = new MyClickListener();
        infoImageNext.setOnClickListener(listener);

        // Setting the EditTexts
        InputRetailer = findViewById(R.id.InputRetailer);
        InputPrice = findViewById(R.id.InputPrice);
        InputURL = findViewById(R.id.InputURL);
        InputColor = findViewById(R.id.InputColor);

        if (product.getRetailer() != null) {
            InputRetailer.setText(product.getRetailer());
        }

        if (product.getPrice() != 0.0f) {
            InputPrice.setText(Float.toString(product.getPrice()));
        }

        if (product.getCategory() != null) {
            CategorySpinner.setPrompt(product.getCategory());
        }

        if (product.getType() != null) {
            TypeSpinner.setPrompt(product.getType());
        }

        if (product.getColor() != null) {
            InputColor.setText(product.getColor());
        }
    }

    public void typeLoader(String choice) {

        // Fills the ArrayList types with elements, depending on the choice of the user in MainActivity
        ArrayList<String> TypeObjects = new ArrayList<>();
        Log.d(TAG, "TYPELOADER: choice is:" + choice);
        switch(choice) {
            case "Clothing":
                for (TypeClothing typeClothing : TypeClothing.values()) {
                    TypeObjects.add(typeClothing.getTypeclothing());
                }
                break;
            case "Shoes":
                for (TypeShoes typeShoes : TypeShoes.values()) {
                    TypeObjects.add(typeShoes.getTypeshoes());
                }
                break;
            case "Accessories":
                for (TypeAccessories typeAccessories : TypeAccessories.values()) {
                    TypeObjects.add(typeAccessories.getTypeaccessories());
                }
                break;
            case "Gifts":
                for (TypeGifts typeGifts : TypeGifts.values()) {
                    TypeObjects.add(typeGifts.getTypegifts());
                }
                break;
            case "Sport":
                for (TypeSport typeSport : TypeSport.values()) {
                    TypeObjects.add(typeSport.getTypesport());
                }
                break;
            case "Wellness":
                for (TypeWellness typeWellness : TypeWellness.values()) {
                    TypeObjects.add(typeWellness.getTypewellness());
                }
                break;
            case "Interior":
                for (TypeInterior typeInterior : TypeInterior.values()) {
                    TypeObjects.add(typeInterior.getTypeinterior());
                }
                break;
        }

        // Filling the arrayAdapter with the values of TypeObjects
        ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TypeObjects);
        TypeSpinner.setAdapter(TypeAdapter);
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
            String Color = InputColor.getText().toString();


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
            product.setImageUri(mImageUri.toString());
            Log.d(TAG, "AddInformationActivity onClick, imageUri: " + mImageUri);
            product.setColor(Color);

            // Calls the 'addProduct' method
            addProduct();
            Intent intent = new Intent(AddInformationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void addProduct () {

        // Method which inserts the selected Product in the database
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.insert(product);
    }

    public void buttonRemove(View view) {
        Log.d(TAG, "buttonRemove: ");
    }
}
