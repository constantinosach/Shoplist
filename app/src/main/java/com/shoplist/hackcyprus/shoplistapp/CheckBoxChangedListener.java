package com.shoplist.hackcyprus.shoplistapp;

import android.database.Cursor;
import android.view.View;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by Abdoulaye Sow on 6/28/2015.
 */
public interface CheckBoxChangedListener {
    void CheckBoxChanged(ViewShoppingListAdapter.ViewHolder viewHolder, boolean isChecked);
}
