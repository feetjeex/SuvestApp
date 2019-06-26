package com.example.suvestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/** DatabaseHelper
 *
 * Helper class which is used to insert, delete and extract entries to and from a SQLite Database, dependent
 * on the choices of the user.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    Cursor cursor;
    private static DatabaseHelper instance;

    // Constructor of the class
    public DatabaseHelper(Context context) {
        super(context, "products", null, 7);
    }

    // Checks if an instance already exists, if not: Creates one
    public static DatabaseHelper getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        else {
            instance = new DatabaseHelper(context);
            return instance;
        }
    }

    /** insert
     *
     * Method which uses contentValues to insert a new entry into the database, after extracting information
     * from a Product object.
     */
    public long insert(Product product) {

        // Establishes a connection with the database
        getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Assigns values to all the fields of the table in the database using data from the Product object
        contentValues.put("Category", product.getCategory());
        contentValues.put("type", product.getType());
        contentValues.put("Retailer", product.getRetailer());
        contentValues.put("Price", product.getPrice());
        contentValues.put("ImageUri", product.getImageUri());
        contentValues.put("uRL", product.getURL());
        contentValues.put("Color", product.getColor());

        // Returns the database upon success
        return getWritableDatabase().insert("products", null, contentValues);
    }

    /** selectAll
     *
     * Used to extract and return entries from the SQLite Database, which are then stored in a Cursor object.
     * The request for entries depends on the choices of the user, such as only entries of a certain color
     * or specific retailer.
     */
    public Cursor selectAll(PreferenceHelper preferenceHelper) {

        // Method to select all rows from the database
        SQLiteDatabase db;
        db = getWritableDatabase();

        // Gets the relevant data from the preferenceHelper object as Strings
        String type = preferenceHelper.getType();
        String color = preferenceHelper.getColor();
        String format = preferenceHelper.getFormat();
        String retailer = preferenceHelper.getRetailer();

        // Executes a rawQuery based on the values of the preferenceHelper object
        if (color.equals("none") && retailer.equals("none")) {
            switch (format) {
                case "none":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"'", null);
                    break;
                case "Price ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' ORDER BY Price ASC", null);
                    break;
                case "Price descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' ORDER BY Price DESC", null);
                    break;
                case "Date added ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' ORDER BY sqltime ASC", null);
                    break;
                case "Date added descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' ORDER BY sqltime DESC", null);
                    break;
            }
        }

        // Executes a rawQuery based on the values of the preferenceHelper object
        if (!color.equals("none") && retailer.equals("none")) {
            switch (format) {
                case "none":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Color = '"+ color +"'", null);
                    break;
                case "Price ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Color = '"+ color +"'ORDER BY Price ASC", null);
                    break;
                case "Price descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Color = '"+ color +"'ORDER BY Price DESC", null);
                    break;
                case "Date added ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Color = '"+ color +"'ORDER BY sqltime ASC", null);
                    break;
                case "Date added descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Color = '"+ color +"'ORDER BY sqltime DESC", null);
                    break;
            }
        }

        // Executes a rawQuery based on the values of the preferenceHelper object
        if (color.equals("none") && !retailer.equals("none")) {
            switch (format) {
                case "none":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND Retailer = '"+ retailer +"'", null);
                    break;
                case "Price ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' ORDER BY Price ASC", null);
                    break;
                case "Price descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' ORDER BY Price DESC", null);
                    break;
                case "Date added ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' ORDER BY sqltime ASC", null);
                    break;
                case "Date added descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' ORDER BY sqltime DESC", null);
                    break;
            }
        }

        // Executes a rawQuery based on the values of the preferenceHelper object
        if (!color.equals("none") && !retailer.equals("none")) {
            switch (format) {
                case "none":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' AND  Color = '" + color + "'", null);
                    break;
                case "Price ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' AND  Color = '" + color + "' ORDER BY Price ASC", null);
                    break;
                case "Price descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' AND  Color = '" + color + "' ORDER BY Price DESC", null);
                    break;
                case "Date added ascending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' AND  Color = '" + color + "' ORDER BY sqltime ASC", null);
                    break;
                case "Date added descending":
                    cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' AND  Retailer = '" + retailer + "' AND  Color = '" + color + "' ORDER BY sqltime DESC", null);
                    break;
            }
        }

        // Returns the cursor containing the rows from the database that the user selected
        return cursor;
    }

    /** onCreate
     *
     * Creates the table products.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creates the table products
        db.execSQL("create table products (_id INTEGER PRIMARY KEY AUTOINCREMENT, Category TEXT, Type TEXT, Retailer TEXT, Price REAL, ImageUri TEXT, URL TEXT, Color TEXT, sqltime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);");
    }

    /** onUpgrade
     *
     * Method used to delete the old table entries and create a new one.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "products");
        onCreate(db);
    }

    /** deleter
     *
     * Method used to delete a certain row in the table products. Can be called by DetailActivity,
     * or by the user long-pressing an entry in TypeActivity.
     */

    public Integer deleter(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id =String.valueOf(_id);
        return db.delete("products", "_id = ?", new String[]{id});
    }

    /** stringDatabaseLookup
     *
     * Method used for requesting and filling an ArrayList with elements dependent on which button (Retailer or Color) the user pressed.
     * This ArrayList then fills a popupMenu allowing the user to filter results in the TypeActivity.
     */
    public ArrayList<String> stringDatabaseLookup (String type, String sort) {

        ArrayList<String> stringArrayList = new ArrayList<>();

        // Connects to the Database, and requests either all the Retailers or all the Colors available for a particular type of screenshot
        SQLiteDatabase db;
        db = getWritableDatabase();
        switch (sort) {
            case "Retailer":
                cursor = db.rawQuery("SELECT Retailer FROM products WHERE Type = '" + type + "'", null);
                break;
            case "Color":
                cursor = db.rawQuery("SELECT Color FROM products WHERE Type = '" + type + "'", null);
                break;
        }

        // Adds all Strings in the cursor to the ArrayList
        while(cursor.moveToNext()) {
            stringArrayList.add(cursor.getString(cursor.getColumnIndexOrThrow(sort)));
        }

        // Returns the ArrayList
        return stringArrayList;
    }
}
