package com.shoplist.hackcyprus.shoplistapp;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.shoplist.hackcyprus.shoplistapp.data.model.ShoppingListItem;

import java.util.ArrayList;

/**
 * Created by Abdoulaye Sow on 6/27/2015.
 */
public class NewShoppingListItemsAdapter extends ArrayAdapter<ShoppingListItem> {

    private final Activity context;

    static class ViewHolder {
        public EditText itemPrice;
        public EditText itemQuantity;
        public EditText itemName;
    }

    public NewShoppingListItemsAdapter(Activity context, ArrayList<ShoppingListItem> shoppingListItems) {
        super(context, R.layout.single_list_item_layout, shoppingListItems);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ShoppingListItem item = getItem(position);
        /// / Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_item_layout, parent, false);
            EditText itemPrice = (EditText) convertView.findViewById(R.id.priceText);
            EditText itemQuantity = (EditText) convertView.findViewById(R.id.quantityText);
            EditText itemName = (EditText) convertView.findViewById(R.id.nameText);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.itemPrice =itemPrice;
            viewHolder.itemQuantity = itemQuantity;
            viewHolder.itemName = itemName;

            itemPrice.addTextChangedListener( new CustomTextWatcher( itemPrice, item) );
            itemQuantity.addTextChangedListener( new CustomTextWatcher( itemQuantity, item) );
            itemName.addTextChangedListener( new CustomTextWatcher( itemName, item) );

            viewHolder.itemPrice.setTag(item);
            viewHolder.itemQuantity.setTag(item);
            viewHolder.itemName.setTag(item);

            convertView.setTag(viewHolder);
        } else {

            ViewHolder viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.itemPrice.setTag(item);
            viewHolder.itemQuantity.setTag(item);
            viewHolder.itemName.setTag(item);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        if( item.getPrice() != -1 ) {
            holder.itemPrice.setText( item.getPrice() + "" );
        }else {
            holder.itemPrice.setText( "" );
        }

        if( item.getQuantity() != -1 ) {
            holder.itemQuantity.setText( item.getQuantity() + "" );
        }else {
            holder.itemQuantity.setText( "" );
        }

        holder.itemName.setText( item.getName() + "" );

        // Return the completed view to render on screen
        return convertView;
    }

    private class CustomTextWatcher implements TextWatcher {

        private EditText EditText;
        private ShoppingListItem item;

        public CustomTextWatcher(EditText e, ShoppingListItem item) {
            this.EditText = e;
            this.item = item;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            return;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after) {
            return;
        }

        @Override
        public void afterTextChanged(Editable arg0) {

            String text = arg0.toString();
            int editTextId = EditText.getId();


            if (text != null && text.length() > 0) {

                if (editTextId == R.id.nameText) {
                    item.setName(text);
                } else if (editTextId == R.id.quantityText) {
                    try {
                        int quantity = Integer.parseInt(text );
                        if( quantity <= 0 )
                            quantity = 1;

                        item.setQuantity( quantity );
                    }catch ( Exception e) {

                    }
                } else if (editTextId == R.id.priceText) {
                    try {
                        double price = Double.parseDouble(text);
                        if( price <= 0 )
                            price = 1;

                        item.setPrice(price );
                    }catch ( Exception e) {

                    }
                }
            }
        }
    }
}
