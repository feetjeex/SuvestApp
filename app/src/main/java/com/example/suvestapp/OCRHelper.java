package com.example.suvestapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.Arrays;
import java.util.List;

public class OCRHelper extends AppCompatActivity {

    // Initializes the global variables
    Product product = new Product();
    SparseArray<TextBlock> textBlock;
    Context context;

    // Constructor of the Class
    public OCRHelper(Bitmap bitmap, Context context) {
        this.context = context;
        textBlock = OCRFunctionality(bitmap);
        ocrHelperFunctionality(textBlock);
    }

    private void ocrHelperFunctionality(SparseArray<TextBlock> textBlock) {

        // Loops trough all the TextBlocks in the SparseArray<TextBlock> textBlock
        for (int i = 0; i <= textBlock.size(); i++) {

            TextBlock ocrTextblock = textBlock.get(i);
            if (ocrTextblock != null) {

                // Extracts the content of an TextBlock ocrTextblock
                String text = ocrTextblock.getValue().toString();

                // Calls the checkerPreparer method on the String of text extracted from the ocrTextblock
                // This method splits the String into smaller substrings and stores them into a List of Strings
                List<String> textList = checkerPreparer(text);

                // Checks the List of String textList for possible matches with keywords such as "Asos" or "Sweater"
                priceChecker(textList);
                retailerChecker(textList);
                categoryChecker(textList);
                typeChecker(textList);
                colorChecker(textList);
            }
        }
    }

    private List<String> checkerPreparer(String text) {

        // This method splits the String into smaller substrings and stores them into a List of Strings
        String str[] = text.split(" ");
        List<String> al = Arrays.asList(str);
        return al;
    }

    private void priceChecker(List<String> text) {

        // Checks the given string for a '€', '$' or '£' character
        for(String s: text) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                // If the character at the i'th position is a '€' sign
                if (c == '€' || c == '$' || c == '£') {
                    int index = 0;
                    char[] array = new char[(s.length() - i)];

                    // Adds all the characters from the j'th position and adds them to a char array 'array'
                    for (int j = i + 1; j < s.length(); j++) {
                        array[index] = s.charAt(j);
                        index++;

                        // Converts the char array 'array' to a string
                        String price = new String(array);

                        try {
                            // Converts the string to a float and sets the price of the product to this value
                            Float floatPrice = Float.parseFloat(price);
                            product.setPrice(floatPrice);

                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "Error while converting price into a float.", Toast.LENGTH_SHORT).show();
                            break;

                        }
                    }
                }
            }
        }
    }

    private void retailerChecker(List<String> text) {

        // Checks if the given list of Strings contains the name of a retailer
        // Iterates through all Strings in the List and compares them to the constants in the Retailer Enum
        for(String s: text) {
            for (Retailer retailer : Retailer.values()) {

                // If a match is found, sets the the relevant field of the product object to this value
                if (retailer.name().equals(s.toUpperCase())) {
                    product.setRetailer(retailer.getRetailer());
                }
            }
        }
    }

    private void categoryChecker(List<String> text) {

        // Checks if the given list of Strings contains the name of a category of product
        for(String s: text) {

            // Iterates trough all the constants in the Category Enum
            for (Category category : Category.values()) {

                // If a match is found, sets the the relevant field of the product object to this value
                if (category.name().equals(s.toUpperCase())) {
                    product.setCategory(category.getCategory());
                }
            }
        }
    }

    private void typeChecker(List<String> text) {

        // Checks if the given list of Strings contains the name of a type of product
        for(String s: text) {

            // If there is already a specified type in the product object
            if (product.getCategory() != null) {
                String category = product.getCategory();

                // Checks which type Enum should be used
                switch (category) {
                    case "Clothing":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeClothing typeClothing : TypeClothing.values()) {
                            if (typeClothing.name().equals(s.toUpperCase())) {
                                product.setType(typeClothing.getTypeclothing());
                            }
                        }
                        break;
                    case "Shoes":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeShoes typeShoes : TypeShoes.values()) {
                            if (typeShoes.name().equals(s.toUpperCase())) {
                                product.setType(typeShoes.getTypeshoes());
                            }
                        }
                        break;
                    case "Accessories":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeAccessories typeAccessories : TypeAccessories.values()) {
                            if (typeAccessories.name().equals(s.toUpperCase())) {
                                product.setType(typeAccessories.getTypeaccessories());
                            }
                        }
                        break;
                    case "Gifts":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeGifts typeGifts : TypeGifts.values()) {
                            if (typeGifts.name().equals(s.toUpperCase())) {
                                product.setType(typeGifts.getTypegifts());
                            }
                        }
                        break;
                    case "Sport":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeSport typeSport : TypeSport.values()) {
                            if (typeSport.name().equals(s.toUpperCase())) {
                                product.setType(typeSport.getTypesport());
                            }
                        }
                        break;
                    case "Wellness":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeWellness typeWellness : TypeWellness.values()) {
                            if (typeWellness.name().equals(s.toUpperCase())) {
                                product.setType(typeWellness.getTypewellness());
                            }
                        }
                        break;
                    case "Interior":

                        // Iterates trough all the constants in the relevant Enum and if a match is found, sets the product objects type to this value
                        for (TypeInterior typeInterior : TypeInterior.values()) {
                            if (typeInterior.name().equals(s.toUpperCase())) {
                                product.setType(typeInterior.getTypeinterior());
                            }
                        }
                        break;
                }
            }
        }
    }

    private void colorChecker(List<String> text) {

        // Checks if the given list of Strings contains a color
        for(String s: text) {

            // Iterates trough all the constants in the Color Enum
            for (Color color : Color.values()) {

                // If a match is found, sets the the relevant field of the product object to this value
                if (color.name().equals(s.toUpperCase())) {
                    product.setColor(color.getColor());
                }
            }
        }
    }

    public SparseArray<TextBlock> OCRFunctionality(Bitmap bitmap) {

        // Initializes a SparseArray to store the TextBlocks
        SparseArray<TextBlock> textBlocks;

        // Check if the bitmap is not empty
        if(bitmap != null) {

            // Creates a new textRecognizer object
            TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

            // If the OCRFunctionality is not functional
            if(!textRecognizer.isOperational()) {

                // Checks for low storage
                // If there is low storage, the native library will not be downloaded, so detection will not become operational
                IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

                // In case the storage is low
                if (hasLowStorage) {
                    Toast.makeText(context,"Low Storage", Toast.LENGTH_LONG).show();
                }
            }

            // Performs the actual OCRFunctionality
            Frame imageFrame = new Frame.Builder().setBitmap(bitmap).build();

            // Fills the SparseArray with textBlocks
            textBlocks = textRecognizer.detect(imageFrame);
        }

        else {
            textBlocks = null;
        }

        return textBlocks;
    }

    public Product productReturner() {

        // Returns a product object
        return product;
    }
}
