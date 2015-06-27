package com.shoplist.hackcyprus.shoplistapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shoplist.hackcyprus.shoplistapp.data.model.ShoppingListItem;

import android.content.Context;

/**
 * Created by flangofas on 27/06/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shoplist";

    // Contacts table name
    private static final String TABLE_CONTACTS = "shoppinglistitem";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";


    public DatabaseHandler(Context content) {
        super(content, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_QUANTITY + " INT," + KEY_PRICE + " REAL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    public void addShoppingListItem(ShoppingListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, item.getName());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_QUANTITY, item.getQuantity());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public ShoppingListItem getShoppingListItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] fields;
        fields = new String[4];
        fields[0] = KEY_ID;
        fields[1] = KEY_NAME;
        fields[2] = KEY_PRICE;
        fields[3] = KEY_QUANTITY;
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_QUANTITY, KEY_PRICE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ShoppingListItem item = new ShoppingListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getDouble(3));

        return item;
    }


}
