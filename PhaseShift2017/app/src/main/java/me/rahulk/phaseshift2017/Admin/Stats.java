package me.rahulk.phaseshift2017.Admin;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import me.rahulk.phaseshift2017.Data.PhaseShiftContract;
import me.rahulk.phaseshift2017.Admin.EventsCursorAdapter;
import me.rahulk.phaseshift2017.Event.EventDetails;
import me.rahulk.phaseshift2017.R;

public class Stats extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    SearchView searchView;
    String search = "";

    private EventsCursorAdapter eventsCursorAdapter;
    private static final int FORECAST_LOADER = 1;

    private static final String[] EVENT_COLUMNS = {
            PhaseShiftContract.EventEntry._ID,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_BMSCE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_FULL,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_ICON,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_FLAGSHIP,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_MAX_REG,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_CUR_REG,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_TYPE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_ACTIVE
    };

    static final int COL_EVENT_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ListView listView = (ListView) findViewById(R.id.lstEvent);
        searchView = (SearchView) findViewById(R.id.editSearch);

        eventsCursorAdapter = new EventsCursorAdapter(getApplicationContext(), null, 0);

        listView.setAdapter(eventsCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    Intent intent = new Intent(Stats.this, EventDetails.class).setData(PhaseShiftContract.EventEntry.buildEventDetailUri(cursor.getLong(COL_EVENT_ID)));
                    startActivity(intent);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search = query;
                getSupportLoaderManager().restartLoader(1, null, Stats.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                getSupportLoaderManager().restartLoader(1, null, Stats.this);
                return true;
            }
        });

        getSupportLoaderManager().initLoader(1, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PhaseShiftContract.EventEntry.COLUMNS_EVENT_ACTIVE + " DESC, " +
                PhaseShiftContract.EventEntry.COLUMNS_EVENT_FULL + " ASC, " +
                PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + " ASC";
        String selection = PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + " LIKE ?";
        String[] selectionArgs = {"%" + search + "%"};
        Uri allEvents = PhaseShiftContract.EventEntry.buildEventUri();
        return new CursorLoader(getApplicationContext(), allEvents, EVENT_COLUMNS, selection, selectionArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        eventsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        eventsCursorAdapter.swapCursor(null);
    }
}
