package com.example.suvestapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.lang.reflect.Type;

public class OCRHelper extends AppCompatActivity {

    // Initializes a product object
    Product product = new Product();
    SparseArray<TextBlock> textBlock;
    Context context;

    // Constructor of the Class
    public OCRHelper(Bitmap bitmap, Context context) {
        this.context = context;
        textBlock = OCRFunctionality(bitmap);
        OCRHelperFunctionality(textBlock);
    }

    private void OCRHelperFunctionality(SparseArray<TextBlock> textBlock) {

        // Loops trough all the TextBlocks in the SparseArray<TextBlock> textBlock
        for (int i = 0; i <= textBlock.size(); i++) {

            TextBlock OcrTextblock = textBlock.get(i);
            if (OcrTextblock != null) {

                String text = OcrTextblock.getValue().toString();
                Log.d("OCRHelper", "OCRHelperFunctionality: String is:" + text);
                PriceChecker(text);
                RetailerChecker(text);
                CategoryChecker(text);
                TypeChecker(text);
                ColorChecker(text);
            }
        }
    }

    private void PriceChecker (String text) {

        Log.d("PriceChecker", "RetailerChecker: Starting PriceChecker");
        // Checks the given string for a € character
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if (c == '€' || c == '$' || c == '£') {

                Log.d("PriceChecker", "RetailerChecker: euro sign found");

                // If the character at the i'th position is a '€' sign
                int index = 0;
                char[] array = new char[(text.length() - i)];

                // Adds all the characters from the j'th position and adds them to a char array 'array'
                for (int j = i + 1; j < text.length(); j++) {
                    array[index] = text.charAt(j);
                    index++;

                    // Converts the char array 'array' to a string
                    String price = new String(array);

                    // Converts the string to a float and sets the price of the product to this value
                    Float floatPrice = Float.parseFloat(price);
                    product.setPrice(floatPrice);
                    Log.d("PriceChecker", "RetailerChecker: Price found");
                }
            }
        }
    }

    private void RetailerChecker (String text) {

        Log.d("RetailChecker", "RetailerChecker: Starting retailchecker");
        // Checks if the given string contains the name of a retailer
        //First get enum constant reference from
        for (Retailer retailer: Retailer.values()) {
            if (retailer.name().equals(text.toUpperCase())) {
                product.setRetailer(retailer.getRetailer());
            }
        }
    }

    private void CategoryChecker (String text) {

        // Checks if the given string contains the name of the Category
        for (Category category: Category.values()) {
            if (category.name().equals(text.toUpperCase())) {
                product.setCategory(category.getCategory());
            }
        }
    }

    private void TypeChecker (String text) {

        // Checks if the given string contains the name of the Type
        if (product.getCategory() != null) {
            String category = product.getCategory();

            // Checks which Type Enum should be used
            switch(category) {
                case "Clothing":
                    for (TypeClothing typeClothing : TypeClothing.values()) {
                        if (typeClothing.name().equals(text.toUpperCase())) {
                            product.setType(typeClothing.getTypeclothing());
                        }
                    }
                    break;
                case "Shoes":
                    for (TypeShoes typeShoes : TypeShoes.values()) {
                        if (typeShoes.name().equals(text.toUpperCase())) {
                            product.setType(typeShoes.getTypeshoes());
                        }
                    }
                    break;
                case "Accessories":
                    for (TypeAccessories typeAccessories : TypeAccessories.values()) {
                        if (typeAccessories.name().equals(text.toUpperCase())) {
                            product.setType(typeAccessories.getTypeaccessories());
                        }
                    }
                    break;
                case "Gifts":
                    for (TypeGifts typeGifts : TypeGifts.values()) {
                        if (typeGifts.name().equals(text.toUpperCase())) {
                            product.setType(typeGifts.getTypegifts());
                        }
                    }
                    break;
                case "Sport":
                    for (TypeSport typeSport : TypeSport.values()) {
                        if (typeSport.name().equals(text.toUpperCase())) {
                            product.setType(typeSport.getTypesport());
                        }
                    }
                    break;
                case "Wellness":
                    for (TypeWellness typeWellness : TypeWellness.values()) {
                        if (typeWellness.name().equals(text.toUpperCase())) {
                            product.setType(typeWellness.getTypewellness());
                        }
                    }
                    break;
                case "Interior":
                    for (TypeInterior typeInterior : TypeInterior.values()) {
                        if (typeInterior.name().equals(text.toUpperCase())) {
                            product.setType(typeInterior.getTypeinterior());
                        }
                    }
                    break;
            }
        }
    }

    private void ColorChecker (String text) {

        // Checks if the given string contains the colour of the product
        for (Color color: Color.values()) {
                if (color.name().equals(text.toUpperCase())) {
                    product.setColor(color.getColor());
            }
        }
    }

    public SparseArray<TextBlock> OCRFunctionality(Bitmap bitmap) {

        SparseArray<TextBlock> textBlocks;
        // imageBitmap is the Bitmap image you're trying to process for text
        if(bitmap != null) {

            Log.d("context", "OCRFunctionality: " + (context == null));
            TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

            if(!textRecognizer.isOperational()) {

                Toast.makeText(context,"Detector dependencies are not yet available.", Toast.LENGTH_LONG).show();

                // Check for low storage.  If there is low storage, the native library will not be
                // downloaded, so detection will not become operational.
                IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

                if (hasLowStorage) {
                    Toast.makeText(context,"Low Storage", Toast.LENGTH_LONG).show();
                }
            }


            Frame imageFrame = new Frame.Builder()
                    .setBitmap(bitmap)
                    .build();

            textBlocks = textRecognizer.detect(imageFrame);

//            for (int i = 0; i < textBlocks.size(); i++) {
//                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
//            }
        }

        else {
            textBlocks = null;
        }

        return textBlocks;
    }

    public Product ProductReturner() {

        // Returns a product object
        return product;
    }
}
