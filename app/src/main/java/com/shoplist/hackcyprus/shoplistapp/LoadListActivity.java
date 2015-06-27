package com.shoplist.hackcyprus.shoplistapp;

import android.app.ListActivity;
import android.app.SearchManager;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;


public class LoadListActivity extends ListActivity {

    ListView shopListItemsListView = null;
    EditText searchBoxEditText = null;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_3);

        dbHandler = new DatabaseHandler(this);
        Cursor dbCursor = dbHandler.getRawAllShoppingLists();

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("query", query);
        }

        final ShoppingListsCursorAdapter adapter = new ShoppingListsCursorAdapter(this, dbCursor);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                if(null != cursor) {
                    Intent viewListItem = new Intent( LoadListActivity.this, ViewListItemActivity.class );
                    int listId = cursor.getInt( 0 );
                    viewListItem.putExtra( "list_id", listId );
                    startActivity(viewListItem);
                }
            }
        });

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_list, menu);
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
