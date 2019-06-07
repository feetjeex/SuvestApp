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
        super(context, R.layout.grid_product, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView gridRetailer = (TextView) view.findViewById(R.id.gridRetailer);
        String Retailer = cursor.getString(3);
        gridRetailer.setText(Retailer);

        // Extracts the String passed by the intent and parses it back to a Uri
        ImageView gridImage = view.findViewById(R.id.gridImage);
        String ImageUri = cursor.getString(5);
        Uri mImageUri = Uri.parse(ImageUri);
        gridImage.setImageURI(mImageUri);
    }
}
