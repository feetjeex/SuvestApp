package com.example.suvestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    Cursor cursor;
    private static DatabaseHelper instance;

    // Constructor of the class
    private DatabaseHelper(Context context) {
        super(context, "products", null, 3);
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

    // Implements the insert method
    public long insert(Product product) {

        // Establishes a connection with the database
        // Assigns values to all the fields of the table in the database using data from the JournalEntry object
        getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Category", product.getCategory());
        contentValues.put("Type", product.getType());
        contentValues.put("Retailer", product.getRetailer());
        contentValues.put("Price", product.getPrice());
        contentValues.put("ImageUri", product.getImageUri());
        contentValues.put("URL", product.getURL());

        // Returns the database upon success
        return getWritableDatabase().insert("products", null, contentValues);
    }

    // Method to select all rows from the database
    public Cursor selectAll(String type) {
        SQLiteDatabase db;
        db = getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM products WHERE Type = '"+type+"' ", null);

        // Returns the cursor containing all the rows from the database
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the table entries
        db.execSQL("create table products (_id INTEGER PRIMARY KEY AUTOINCREMENT, Category TEXT, Type TEXT, Retailer TEXT, Price REAL, ImageUri TEXT, URL TEXT, Timestamp TEXT);");
    }

    // Method used to delete the old table entries and create a new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "products");
        onCreate(db);
    }

    // Method used to delete a certain row in the table entries
    public Integer deleter(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id =String.valueOf(_id);
        return db.delete("products", "_id = ?", new String[]{id});
    }
}
