package com.shoplist.hackcyprus.shoplistapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shoplist.hackcyprus.shoplistapp.data.model.ShoppingList;
import com.shoplist.hackcyprus.shoplistapp.data.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;


public class CreateListActivity extends ListActivity {

    private ArrayList<ShoppingListItem> items;
    private ShoppingList shoppingList;
    private DatabaseHandler dbHandler;
    private EditText shoppingListName;

    private Button saveButtton;
    private Button backButton;
    private Button addItemButton;

    private int totalItems = 0;
    private String action = "create";
    private int listId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list_layout);

        shoppingList = new ShoppingList(0, "");
        items = new ArrayList<ShoppingListItem>();

        dbHandler = new DatabaseHandler(this);

        Intent intent = getIntent();
        String intentAction = intent.getStringExtra("action");
        int intentListId = intent.getIntExtra("list_id", 0);

        shoppingList = new ShoppingList(listId, "New List");

        if( null != intentAction && intentAction.length() > 0 ) {
            action = intentAction;
        }
        if(0 != intentListId) {
            listId = intentListId;
            shoppingList = dbHandler.getShoppingList(listId);
            items = dbHandler.getShoppingListItemsForList(listId);
            totalItems = items.size();
        }



        //newItemsList = (ListView) findViewById(R.id.list);
        final NewShoppingListItemsAdapter adapter = new NewShoppingListItemsAdapter(this, items);

        setListAdapter(adapter);

        addItemButton = (Button) findViewById(R.id.addItemButton);
        saveButtton = (Button) findViewById(R.id.saveButton);
        backButton  = (Button) findViewById(R.id.backButton);
        shoppingListName = (EditText) findViewById(R.id.enterListName);

        shoppingListName.setText(shoppingList.getName());

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalItems++;
                ShoppingListItem newItem = new ShoppingListItem(0, "Item " + totalItems, 1, 0, listId);
                //items.add(newItem);
                adapter.add(newItem);
            }
        });

        saveButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( totalItems == 0 ){
                    Toast msg = Toast.makeText( CreateListActivity.this, "No item to save", Toast.LENGTH_LONG );
                    msg.show();
                    return;
                }

                if(shoppingListName.getText().toString() == "") {
                    Toast msg = Toast.makeText( CreateListActivity.this, "The shopping list name is required", Toast.LENGTH_LONG );
                    msg.show();
                    return;
                }



                if(  0 != listId) {
                    shoppingList = dbHandler.getShoppingList(listId);
                    dbHandler.updateShoppingList(shoppingList);
                    ShoppingListItem item;
                    for(int i = 0; i < adapter.getCount(); i++) {
                        item = adapter.getItem( i );
                        if( 0 == item.getId() ) {
                            dbHandler.addShoppingListItem( item,  listId);
                        } else {
                            dbHandler.updateShoppingListItem(item);
                        }
                    }
                } else {

                    int newId = dbHandler.addShoppingList(shoppingList);
                    shoppingList.setId(newId);

                    if( newId > 0 ) {
                        ShoppingListItem item;
                        for(int i = 0; i < adapter.getCount(); i++) {
                            item = adapter.getItem( i );
                            dbHandler.addShoppingListItem( item,  newId);
                        }

                        Toast msg = Toast.makeText(CreateListActivity.this, "shopping list successfully added", Toast.LENGTH_LONG);
                        msg.show();
                        Intent backIntent = new Intent(CreateListActivity.this, MainActivity.class);
                        startActivity(backIntent);
                    }

                }



            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(CreateListActivity.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
