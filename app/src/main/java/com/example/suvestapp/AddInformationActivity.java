package com.example.suvestapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/** AddInformationActivity
 *
 * A Class which allows the user to add screenshots to the application. Using the OCRHelper Class, as much information as possible
 * is extracted from the image, and filled in for the user. The user can then fill in the remaining fields, and confirm the addition of
 * the screenshot to the app.
 */

public class AddInformationActivity extends AppCompatActivity {

    // Initializes a new Product object
    Product product;

    // Initializing the global variables
    Bitmap bitmap;
    ArrayList<String> types = new ArrayList<>();
    Uri mImageUri;

    // Sets the UI elements
    EditText InputRetailer;
    EditText InputPrice;
    EditText InputURL;
    EditText InputColor;
    Spinner CategorySpinner;
    Spinner TypeSpinner;

    /** onCreate
     *
     * Used to initialize the UI elements, to get the imageUri from the previous activity, and calls the OCRHelper Class to
     * perform OCRFunctionality on the selected screenshot. Also converts the selected screenshot (represented by an URI)
     * into a bitmap, so it can be used in the OCRHelper Class in order to attempt to extract information from it.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        // Gets the Intent from the previous activity and puts the stringUri's in an ArrayList
        Intent intent = getIntent();
        ArrayList<Uri> stringUri = intent.getParcelableArrayListExtra("imageUri");

        // Extracts the String contained in StringUri
        mImageUri = stringUri.get(0);

        try {

            // Converting the Uri to a Bitmap
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
        }

        catch (Exception e) {
            // If the conversion fails
            Toast.makeText(this, "Conversion from Uri to Bitmap failed.", Toast.LENGTH_SHORT).show();
        }

        // Calls the OCRFunctionality to be performed on the screenshot
        OCRHelper ocrHelper = new OCRHelper(bitmap, this);
        product = ocrHelper.productReturner();

        // Sets the image in the layout to the Image passed by the intent
        ImageView infoImageView = findViewById(R.id.detailImage);
        infoImageView.setImageURI(mImageUri);

        // Declare and initialize a new ArrayList categoryObjects, and fill it with values from the Category Enum
        ArrayList<String> categoryObjects = new ArrayList<>();
        for (Category category: Category.values()) {
            categoryObjects.add(category.getCategory());
        }

        // Initializing and filling the ArrayAdapter categoryAdapter with values from the ArrayList CategoryObjects
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryObjects);
        CategorySpinner = (Spinner) findViewById(R.id.CategorySpinner);
        CategorySpinner.setAdapter(categoryAdapter);

        // Initializes the ArrayList for TypeObjects
        ArrayList<String> typeObjects = new ArrayList<>();
        TypeSpinner = (Spinner) findViewById(R.id.TypeSpinner);

        // If the product category has already been determined by the OCRHelper
        if (product.getCategory() != null) {
            CategorySpinner.setSelection(categoryAdapter.getPosition(product.getCategory()));
        }

        // Getting the Category the user choose from the spinner CategorySpinner
        CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
        Button infoImageRemove = (Button) findViewById(R.id.detailBuy);

        // Adding the OnClickListener to transfer the user to the next image
        View.OnClickListener listener = new MyClickListener();
        infoImageNext.setOnClickListener(listener);

        // Setting the EditTexts
        InputRetailer = findViewById(R.id.InputRetailer);
        InputPrice = findViewById(R.id.InputPrice);
        InputURL = findViewById(R.id.InputURL);
        InputColor = findViewById(R.id.InputColor);

        // Checks if any fields op the product object have been filled by the ocrHelper
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

    /** typeLoader
     *
     * A method which adds all the constants from the relevant Enum (Gifts, or Shoes for example) to an ArrayList,
     * which is then used by an adapter to show these types in a spinner.
     */

    public void typeLoader(String choice) {

        // Fills the ArrayList types with elements, depending on the choice of the user in MainActivity
        ArrayList<String> typeObjects = new ArrayList<>();
        switch(choice) {
            case "Clothing":
                for (TypeClothing typeClothing : TypeClothing.values()) {
                    typeObjects.add(typeClothing.getTypeclothing());
                }
                break;
            case "Shoes":
                for (TypeShoes typeShoes : TypeShoes.values()) {
                    typeObjects.add(typeShoes.getTypeshoes());
                }
                break;
            case "Accessories":
                for (TypeAccessories typeAccessories : TypeAccessories.values()) {
                    typeObjects.add(typeAccessories.getTypeaccessories());
                }
                break;
            case "Gifts":
                for (TypeGifts typeGifts : TypeGifts.values()) {
                    typeObjects.add(typeGifts.getTypegifts());
                }
                break;
            case "Sport":
                for (TypeSport typeSport : TypeSport.values()) {
                    typeObjects.add(typeSport.getTypesport());
                }
                break;
            case "Wellness":
                for (TypeWellness typeWellness : TypeWellness.values()) {
                    typeObjects.add(typeWellness.getTypewellness());
                }
                break;
            case "Interior":
                for (TypeInterior typeInterior : TypeInterior.values()) {
                    typeObjects.add(typeInterior.getTypeinterior());
                }
                break;
        }

        // Filling the arrayAdapter with the values of TypeObjects
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typeObjects);
        TypeSpinner.setAdapter(typeAdapter);
    }

    /** MyClickListener
     *
     * OnClick, will add the screenshot to the SQLite database, extracting all available information from
     * the user inputs.
     */
    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // Assigns values to the elements used in the Product
            String category = CategorySpinner.getSelectedItem().toString();
            String type = TypeSpinner.getSelectedItem().toString();
            String retailer = InputRetailer.getText().toString();
            String uRL = InputURL.getText().toString();
            Float price = Float.valueOf(InputPrice.getText().toString());
            String color = InputColor.getText().toString();

            // Setting all the fields in a Product object
            product.setCategory(category);
            product.setType(type);
            product.setRetailer(retailer);
            product.setURL(uRL);
            product.setPrice(price);
            product.setImageUri(mImageUri.toString());
            product.setColor(color);

            // Calls the 'addProduct' method to add the screenshot / product to the database
            addProduct();

            // Starts the next activity using an intent
            Intent intent = new Intent(AddInformationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /** addProduct
     *
     * Method which inserts the selected Product in the database.
     */
    public void addProduct () {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.insert(product);
    }

    /** buttonRemove
     *
     * Brings the user back to the main menu, if they decide not to add the screenshot to the database.
     */
    public void buttonRemove(View view) {

        // Brings the user back to the main menu
        Intent intent = new Intent(AddInformationActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
