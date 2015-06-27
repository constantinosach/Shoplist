package com.shoplist.hackcyprus.shoplistapp.data.model;

import java.util.ArrayList;

/**
 * Created by flangofas on 27/06/15.
 */
public class ShoppingList {
    private int id;
    private String name;
    private ArrayList<ShoppingListItem> items;

    public ShoppingList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShoppingListItem> items) {
        this.items = items;
    }

}
