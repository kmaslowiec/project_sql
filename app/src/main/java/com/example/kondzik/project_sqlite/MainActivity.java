package com.example.kondzik.project_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kondzik.project_sqlite.data.StoreContract.StoreEntry;
import com.example.kondzik.project_sqlite.data.StoreDbHelper;

public class MainActivity extends AppCompatActivity {

    private StoreDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            mDbHelper = new StoreDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
        insertProduct();


    }

    private void insertProduct() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(StoreEntry.COLUMN_PRODUCT_NAME, "Zywiec");
        values.put(StoreEntry.COLUMN_PRODUCT_PRICE, 25.5);
        values.put(StoreEntry.COLUMN_PRODUCT_QUANTITY, 5);
        values.put(StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Zywiec Polska");
        values.put(StoreEntry.COLUMN_SUPPLIER_PHONE, "0048221004280");


        db.insert(StoreEntry.TABLE_NAME, null, values);
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StoreEntry._ID,
                StoreEntry.COLUMN_PRODUCT_NAME,
                StoreEntry.COLUMN_PRODUCT_PRICE,
                StoreEntry.COLUMN_PRODUCT_QUANTITY,
                StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                StoreEntry.COLUMN_SUPPLIER_PHONE
                };

        // Perform a query on the pets table
        Cursor cursor = db.query(
                StoreEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(StoreEntry._ID + " - " +
                    StoreEntry.COLUMN_PRODUCT_NAME + " - " +
                    StoreEntry.COLUMN_PRODUCT_PRICE + " - " +
                    StoreEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                    StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " - " +
                    StoreEntry.COLUMN_SUPPLIER_PHONE
                    + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(StoreEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_SUPPLIER_PHONE);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                float price = cursor.getFloat(productPriceColumnIndex); // cursor.getInt(productPriceColumnIndex);
                int quan = cursor.getInt(quantityColumnIndex);
                String suppName = cursor.getString(supplierNameColumnIndex);
                String suppPhone = cursor.getString(supplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        name + " - " +
                        price + " - " +
                        quan + " - " +
                        suppName + " - " +
                        suppPhone));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

}
