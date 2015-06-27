package com.shoplist.hackcyprus.shoplistapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shoplist.hackcyprus.shoplistapp.data.model.ShoppingList;
import com.shoplist.hackcyprus.shoplistapp.data.model.ShoppingListItem;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by flangofas on 27/06/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shoplist";

    // ShoppingListItem table name
    private static final String TABLE_CONTACTS = "shoppinglistitem";

    // ShoppingListItem Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";

    // ShopList table name
    private static final String TABLE_SHOPLIST = "list";

    // ShopList Table Columns names
    private static final String KEY_SHOPLIST_ID = "id";
    private static final String KEY_SHOPLIST_NAME = "name";

    public DatabaseHandler(Context content) {
        super(content, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOPLISTITEMS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_QUANTITY + " INT," + KEY_PRICE + " REAL" + "); ";
        String CREATE_SHOPLIST_TABLE = "CREATE TABLE " + TABLE_SHOPLIST + "("
                + KEY_SHOPLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SHOPLIST_NAME + " TEXT); ";
        //String SQL_TABLES = CREATE_SHOPLIST_TABLE + CREATE_SHOPLISTITEMS_TABLE;

        db.execSQL(CREATE_SHOPLIST_TABLE);
        db.execSQL(CREATE_SHOPLISTITEMS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPLIST);


        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    //Create ShoppingListItem
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

    //Read ShoppingListItem
    public ShoppingListItem getShoppingListItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] fields;
        fields = new String[4];
        fields[0] = KEY_ID;
        fields[1] = KEY_NAME;
        fields[2] = KEY_PRICE;
        fields[3] = KEY_QUANTITY;
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_QUANTITY, KEY_PRICE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ShoppingListItem item = new ShoppingListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getDouble(3));

        return item;
    }

    // get shoppingListItems for a shoppingList
    public Cursor getRawShoppingListItemsForList(int listId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ShoppingListItem> items = new ArrayList<ShoppingListItem>();
        String query = "Select id as _id, " + KEY_NAME +  ", " + KEY_PRICE + ", " + KEY_QUANTITY + ", " +  KEY_SHOPLIST_ID + "  from " + TABLE_CONTACTS
                      + " WHERE " + KEY_SHOPLIST_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(listId) });

        return cursor;
    }

    // Update ShoppingListItem
    public int updateShoppingListItem(ShoppingListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }

    // Delete ShoppingListItem
    public void deleteShoppingListItem(ShoppingListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }


    public List<ShoppingListItem> getAllShoppingListItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ShoppingListItem> items = new ArrayList<ShoppingListItem>();
        String query = "Select id as _id,  from " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingListItem item = new ShoppingListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getDouble(3));
                items.add(item);
            } while(cursor.moveToNext());
        }

        return items;
    }

    //Raw data
    public Cursor getRawAllShoppingListItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    //Raw data
    public Cursor getRawAllShoppingLists() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select id as _id, name from " + TABLE_SHOPLIST;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select id as _id, name from " + TABLE_SHOPLIST;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingList listItem = new ShoppingList(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
                shoppingLists.add(listItem);
            } while(cursor.moveToNext());
        }

        return shoppingLists;
    }

    //Create list
    public int addShoppingList(ShoppingList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SHOPLIST_NAME, list.getName());

        // Inserting Row
        int newId  = (int) db.insert(TABLE_SHOPLIST, null, values);
        db.close(); // Closing database connection
        return newId;
    }

    //Read list
    public ShoppingList getShoppingList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] fields;
        fields = new String[2];
        fields[0] = KEY_SHOPLIST_ID;
        fields[1] = KEY_SHOPLIST_NAME;
        Cursor cursor = db.query(TABLE_SHOPLIST, new String[]{KEY_SHOPLIST_ID,
                        KEY_SHOPLIST_NAME}, KEY_SHOPLIST_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ShoppingList item = new ShoppingList(Integer.parseInt(cursor.getString(0)), cursor.getString(1));

        return item;
    }

    // Update list
    public int updateShoppingList(ShoppingList list) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHOPLIST_NAME, list.getName());

        // updating row
        return db.update(TABLE_SHOPLIST, values, KEY_SHOPLIST_ID + " = ?",
                new String[] { String.valueOf(list.getId()) });
    }

    // Delete list
    public void deleteShoppingList(ShoppingList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPLIST, KEY_SHOPLIST_ID + " = ?",
                new String[]{String.valueOf(list.getId())});
        db.close();
    }
}
