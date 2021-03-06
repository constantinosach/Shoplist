package com.shoplist.hackcyprus.shoplistapp;

import android.app.ListActivity;
import android.app.SearchManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;


public class LoadListActivity extends ListActivity {

    ListView shopListItemsListView = null;
    Button backButton = null;
    SearchView searchView = null;

    DatabaseHandler dbHandler;
    private Intent searchIntent;

    public static final String QUERY_KEY = "query";

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

        backButton = (Button) findViewById(R.id.button3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(LoadListActivity.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        final ShoppingListsCursorAdapter adapter = new ShoppingListsCursorAdapter(this, dbCursor);
        ListView list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                if (null != cursor) {
                    Intent viewListItem = new Intent(LoadListActivity.this, ViewListItemActivity.class);
                    int listId = cursor.getInt(0);
                    viewListItem.putExtra("list_id", listId);
                    startActivity(viewListItem);
                }
            }
        });

        this.registerForContextMenu(list);

        setListAdapter(adapter);

        searchView = ( SearchView ) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Cursor cLists = dbHandler.findList(query);
                if (cLists.moveToFirst()) {
                    adapter.swapCursor(cLists);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No results were found.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if(id == R.id.editList) {
            Cursor cursor = (Cursor) getListAdapter().getItem(position);
            Intent viewListItem = new Intent(LoadListActivity.this, CreateListActivity.class);
            int listId = cursor.getInt(0);
            viewListItem.putExtra("list_id", listId);
            viewListItem.putExtra("action", "edit");
            startActivity(viewListItem);
        }

        if(id == R.id.deleteList) {
            Toast msg = Toast.makeText(LoadListActivity.this, "Deleting List", Toast.LENGTH_LONG);
            msg.show();
        }

        return true;
    }

}
