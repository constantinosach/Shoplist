package com.shoplist.hackcyprus.shoplistapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Abdoulaye Sow on 6/27/2015.
 */
public class ShoppingListsCursorAdapter extends CursorAdapter {

    public ShoppingListsCursorAdapter(Context context, Cursor cursor){
        super( context, cursor, false );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.shopping_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemName = (TextView) view.findViewById(R.id.shopping_list_item_name);
        itemName.setText( cursor.getString(1) );
    }


}
