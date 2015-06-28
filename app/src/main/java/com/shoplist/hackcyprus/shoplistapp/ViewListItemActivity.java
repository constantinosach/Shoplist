package com.shoplist.hackcyprus.shoplistapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class ViewListItemActivity extends ListActivity {

    private DatabaseHandler dbHandler;
    private  Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_4);

        dbHandler = new DatabaseHandler(this);

        Intent intent = getIntent();
        int listId = intent.getIntExtra("list_id", 0);

        Cursor dbCursor = dbHandler.getRawShoppingListItemsForList(listId);

        ViewShoppingListAdapter listAdapter = new ViewShoppingListAdapter(this, dbCursor);
        setListAdapter(listAdapter);

        backButton = (Button) findViewById(R.id.backToLoadListButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(ViewListItemActivity.this, LoadListActivity.class);
                startActivity(backIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list_item, menu);
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
