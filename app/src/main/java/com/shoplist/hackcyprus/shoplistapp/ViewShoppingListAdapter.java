package com.shoplist.hackcyprus.shoplistapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Abdoulaye Sow on 6/27/2015.
 */
public class ViewShoppingListAdapter extends CursorAdapter {

    public static class ViewHolder {
        public TextView quantity;
        public TextView price;
        public TextView name;
        public CheckBox isItemInBasket;
    }


    private CheckBoxChangedListener checkChangedListener;

    public ViewShoppingListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_shopping_list_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        TextView quantity = (TextView) view.findViewById(R.id.itemQuantityText);
        TextView name = (TextView) view.findViewById(R.id.itemNameText);
        TextView price = (TextView) view.findViewById(R.id.itemPriceText);
        CheckBox isItemInBasket = (CheckBox) view.findViewById(R.id.isItemInBasketCheckBox);

        name.setText( cursor.getString( 1 )  );
        quantity.setText(cursor.getString(2));
        price.setText(cursor.getString(3));

        ViewHolder vh = new ViewHolder();
        vh.name =  name;
        vh.quantity = quantity;
        vh.price = price;
        vh.isItemInBasket = isItemInBasket;
        isItemInBasket.setTag(vh);

        isItemInBasket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ViewShoppingListAdapter.ViewHolder vh = (ViewShoppingListAdapter.ViewHolder) buttonView.getTag();
                        ViewShoppingListAdapter.this.checkChangedListener.CheckBoxChanged(vh, isChecked);
            }
        });

        boolean itemBasket = false;
        isItemInBasket.setChecked( itemBasket );

    }

    public void registerCheckedChangeLister(CheckBoxChangedListener listener) {
        this.checkChangedListener = listener;
    }

}
