package com.shoplist.hackcyprus.shoplistapp.data.model;

/**
 * Created by Abdoulaye Sow on 6/27/2015.
 */
public class ShoppingListItem {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private int listId;

    public ShoppingListItem(int id, String name, int quantity, double price, int listId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.listId = listId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
