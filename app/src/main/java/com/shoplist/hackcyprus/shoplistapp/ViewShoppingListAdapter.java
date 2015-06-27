package com.shoplist.hackcyprus.shoplistapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Abdoulaye Sow on 6/27/2015.
 */
public class ViewShoppingListAdapter extends CursorAdapter {


    public ViewShoppingListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_shopping_list_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView quantity = (TextView) view.findViewById(R.id.itemQuantityText);
        TextView name = (TextView) view.findViewById(R.id.itemNameText);
        TextView price = (TextView) view.findViewById(R.id.itemPriceText);
        CheckBox isItemInBasket = (CheckBox) view.findViewById(R.id.isItemInBasketCheckBox);


        quantity.setText( cursor.getString(cursor.getColumnIndex("quantity") )  );
        name.setText( cursor.getString(cursor.getColumnIndex("name")) );
        price.setText( cursor.getString(cursor.getColumnIndex("price") ) );

        boolean itemBasket = ( cursor.getString(cursor.getColumnIndex("isItemInBasket") ) == "1" ) ? true : false;
        isItemInBasket.setChecked( itemBasket );

    }
}
