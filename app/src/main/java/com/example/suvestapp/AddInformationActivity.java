package com.example.suvestapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        // Gets the users choice using an Intent and
        Intent intent = getIntent();
        ArrayList<Uri> stringUri = intent.getParcelableArrayListExtra("imageUri");

        // Extracts the String passed by the intent and parses it back to a Uri
        Uri mImageUri = stringUri.get(0);
        //Uri mImageUri = Uri.parse(imageUri);

        try {

            // Converting the Uri to a Bitmap
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
        }

        catch (Exception e) {
            Toast.makeText(this, "Conversion from Uri to Bitmap failed.", Toast.LENGTH_SHORT).show();
        }

        OCRHelper ocrHelper = new OCRHelper(bitmap, this);
        product = ocrHelper.ProductReturner();

        //OCRHelper ocrHelper = OCRHelper(textBlock);
        //textBlock = textBlocks.get(textBlocks.keyAt(0));

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
            product.setImageUri(imageUri);
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
}
