package me.rahulk.phaseshift2017.Admin;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import me.rahulk.phaseshift2017.Data.PhaseShiftContract;
import me.rahulk.phaseshift2017.Admin.EventsCursorAdapter;
import me.rahulk.phaseshift2017.Event.EventDetails;
import me.rahulk.phaseshift2017.R;

public class Stats extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SharedPreferences sharedPreferences;

    SearchView searchView;
    String search = "";
    String department = "";
    String order = PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + " ASC ";
    private EventsCursorAdapter eventsCursorAdapter;
    private static final int FORECAST_LOADER = 1;
    private Toolbar toolbar;
    ArrayList<String> selectionArgs = new ArrayList<String>();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.event_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSortTitle:
                order = PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + " ASC ";
                break;
            case R.id.menuSortDepartment:
                order = PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT + " ASC ";
                break;
            case R.id.menuSortRegistrations:
                order = "(" + PhaseShiftContract.EventEntry.TABLE_NAME + "." + PhaseShiftContract.EventEntry.COLUMNS_EVENT_CUR_REG + " * 1.0)/(" + PhaseShiftContract.EventEntry.TABLE_NAME + "." + PhaseShiftContract.EventEntry.COLUMNS_EVENT_MAX_REG + " * 1.0) ASC";
                break;
            case R.id.menuFilterAll:
                department = "";
                break;
            case R.id.menuFilterBT:
                department = "Bio-Technology";
                break;
            case R.id.menuFilterCV:
                department = "Civil Engineering";
                break;
            case R.id.menuFilterCH:
                department = "Chemical Engineering";
                break;
            case R.id.menuFilterCS:
                department = "Computer Science and Engineering";
                break;
            case R.id.menuFilterEE:
                department = "Electrical and Electronics Engineering";
                break;
            case R.id.menuFilterEC:
                department = "Electronics and Communication Engineering";
                break;
            case R.id.menuFilterEIE:
                department = "Electronics and Instrumentation Engineering";
                break;
            case R.id.menuFilterIEM:
                department = "Industrial Engineering and Management";
                break;
            case R.id.menuFilterIS:
                department = "Information Science and Engineering";
                break;
            case R.id.menuFilterMCA:
                department = "Master of Computer Application";
                break;
            case R.id.menuFilterME:
                department = "Mechanical Engineering";
                break;
            case R.id.menuFilterML:
                department = "Medical Electronics";
                break;
            case R.id.menuFilterTC:
                department = "Telecommunication Engineering";
                break;
            case R.id.menuFilterIEEE:
                department = "BMSCE IEEE";
                break;
            case R.id.menuFilterARC:
                department = "Architecture";
                break;
            case R.id.menuFilterMBA:
                department = "Master of Business Administration";
                break;
            case R.id.menuFilterPENT:
                department = "Pentagram";
                break;
            case R.id.menuFilterAU:
                department = "Alternate Universe";
                break;
            case R.id.menuFilterQUIZ:
                department = "Qcaine - Quiz club";
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        getSupportLoaderManager().restartLoader(1, null, Stats.this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = this.getSharedPreferences("PhaseShift2017", this.MODE_PRIVATE);

        switch (getAccessCode().substring(0, 4)) {
            case "":
                finish();
                break;
            case "DEPT":
                department = getAccessCode().substring(12);
                break;
            case "DATA":
                department = "";
                break;
            case "ADMN":
                department = "";
                break;
            default:
                finish();
        }

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
        String sortOrder = order;
        String selection = PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + " LIKE ? ";
        selectionArgs.clear();
        selectionArgs.add("%" + search + "%");

        // Department
        if (!department.equals("")) {
            selection += " AND " + PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT + " LIKE ?";

            if (getAccessCode().substring(0, 4).equals("DEPT")) {
                selectionArgs.add("%" + getAccessCode().substring(12) + "%");
            } else {
                selectionArgs.add("%" + department + "%");
            }
        }

        Uri allEvents = PhaseShiftContract.EventEntry.buildEventUri();
        return new CursorLoader(getApplicationContext(), allEvents, EVENT_COLUMNS, selection, selectionArgs.toArray(new String[selectionArgs.size()]), sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        eventsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        eventsCursorAdapter.swapCursor(null);
    }

    private String getAccessCode() {
        return sharedPreferences.getString("AdminCode", "");
    }
}
