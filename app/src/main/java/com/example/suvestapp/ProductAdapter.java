package com.example.suvestapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class ProductAdapter extends ResourceCursorAdapter {

    public ProductAdapter(Context context, Cursor cursor) {

        // Constructor of the Class
        super(context, R.layout.grid_product, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Assigns and fills the TextView gridRetailer
        TextView gridRetailer = (TextView) view.findViewById(R.id.gridRetailer);
        String retailer = cursor.getString(3);
        gridRetailer.setText(retailer);

        // Assigns and fills the ImageView gridImage, after parsing the String ImageUri
        ImageView gridImage = view.findViewById(R.id.gridImage);
        String ImageUri = cursor.getString(5);
        Uri mImageUri = Uri.parse(ImageUri);
        gridImage.setImageURI(mImageUri);
    }
}
