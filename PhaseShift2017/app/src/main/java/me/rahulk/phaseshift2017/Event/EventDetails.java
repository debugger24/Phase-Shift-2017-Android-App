package me.rahulk.phaseshift2017.Event;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import me.rahulk.phaseshift2017.Data.PhaseShiftContract;
import me.rahulk.phaseshift2017.R;

public class EventDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView txtTitle, txtDepartment, txtPrize1, txtPrize2, txtPrize3, txtVenue, txtSchedule, txtCoordinator, txtFees, txtDesctiption, txtRules, txtParticipation;
    private View viewPrize1, viewPrize2, viewPrize3, viewDescription, viewRules, viewForBMSCE, viewFullEvent, viewVenue, viewSchedule;
    private String shareMessage = "Shared using PhaseShift App";

    private static final int DETAIL_LOADER = 0;
    private static final String[] EVENT_COLUMNS = {
            PhaseShiftContract.EventEntry._ID,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE1,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE2,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE3,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_BMSCE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_FULL,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_VENUE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_DATE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_TIME,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_PERSON,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_PERSON_NUMBER,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_DESCRIPTION,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_RULES,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_PARTICIPATION,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_FEES
    };

    static final int COL_EVENT_ID = 0;
    static final int COL_EVENT_TITLE = 1;
    static final int COL_EVENT_DEPARTMENT = 2;
    static final int COL_EVENT_PRIZE1 = 3;
    static final int COL_EVENT_PRIZE2 = 4;
    static final int COL_EVENT_PRIZE3 = 5;
    static final int COL_EVENT_BMSCE = 6;
    static final int COL_EVENT_FULL = 7;
    static final int COL_EVENT_VENUE = 8;
    static final int COL_EVENT_DATE = 9;
    static final int COL_EVENT_TIME = 10;
    static final int COL_EVENT_PERSON = 11;
    static final int COL_EVENT_PERSON_NUMBER = 12;
    static final int COL_EVENT_DESCRIPTION = 13;
    static final int COL_EVENT_RULES = 14;
    static final int COL_EVENT_PARTICIPATION = 15;
    static final int COL_EVENT_FEES = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(DETAIL_LOADER, null, this);


        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDepartment = (TextView) findViewById(R.id.txtDepartment);
        txtPrize1 = (TextView) findViewById(R.id.txtPrize1);
        txtPrize2 = (TextView) findViewById(R.id.txtPrize2);
        txtPrize3 = (TextView) findViewById(R.id.txtPrize3);
        txtVenue = (TextView) findViewById(R.id.txtVenue);
        txtSchedule = (TextView) findViewById(R.id.txtSchedule);
        txtParticipation = (TextView) findViewById(R.id.txtParticipation);
        txtCoordinator = (TextView) findViewById(R.id.txtCoordinator);
        txtFees = (TextView) findViewById(R.id.txtFees);
        txtDesctiption = (TextView) findViewById(R.id.txtDescription);
        txtRules = (TextView) findViewById(R.id.txtRules);


        viewPrize1 = (View) findViewById(R.id.viewPrize1);
        viewPrize2 = (View) findViewById(R.id.viewPrize2);
        viewPrize3 = (View) findViewById(R.id.viewPrize3);

        viewVenue = (View) findViewById(R.id.viewVenue);
        viewSchedule = (View) findViewById(R.id.viewSchedule);

        viewDescription = (View) findViewById(R.id.viewDescription);
        viewRules = (View) findViewById(R.id.viewRules);

        viewForBMSCE = (View) findViewById(R.id.viewForBMSCE);
        viewFullEvent = (View) findViewById(R.id.viewFullEvent);

        if (ContextCompat.checkSelfPermission(EventDetails.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(EventDetails.this, android.Manifest.permission.CALL_PHONE)) {

                // Do Nothing as of now
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(EventDetails.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = this.getIntent();
        if (intent == null) {
            return null;
        }
        Uri data = intent.getData();
        return new CursorLoader(this, data, EVENT_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        txtTitle.setText(data.getString(COL_EVENT_TITLE));
        txtDepartment.setText(data.getString(COL_EVENT_DEPARTMENT));

        /* Prize 1 */
        if (data.getString(COL_EVENT_PRIZE1).equals("null") || data.getString(COL_EVENT_PRIZE1).equals("")) {
            viewPrize1.setVisibility(View.GONE);
        } else {
            viewPrize1.setVisibility(View.VISIBLE);
            txtPrize1.setText("Winner\n" + data.getString(COL_EVENT_PRIZE1));
        }

        /* Prize 2 */
        if (data.getString(COL_EVENT_PRIZE2).equals("null") || data.getString(COL_EVENT_PRIZE2).equals("")) {
            viewPrize2.setVisibility(View.GONE);
        } else {
            viewPrize2.setVisibility(View.VISIBLE);
            txtPrize2.setText("1st Runner Up\n" + data.getString(COL_EVENT_PRIZE2));
        }

        /* Prize 3 */
        if (data.getString(COL_EVENT_PRIZE3).equals("null") || data.getString(COL_EVENT_PRIZE3).equals("")) {
            viewPrize3.setVisibility(View.GONE);
        } else {
            viewPrize3.setVisibility(View.VISIBLE);
            txtPrize3.setText("2nd Runner Up\n" + data.getString(COL_EVENT_PRIZE3));
        }

        /* Venue */
        if (data.getString(COL_EVENT_VENUE).equals("null") || data.getString(COL_EVENT_VENUE).equals("")) {
            viewVenue.setVisibility(View.GONE);
        } else {
            viewVenue.setVisibility(View.VISIBLE);
            txtVenue.setText(data.getString(COL_EVENT_VENUE));
        }

        /* Schedule */
        txtSchedule.setText(data.getString(COL_EVENT_DATE) + "\n" + data.getString(COL_EVENT_TIME));

        /* Coordinators */
        txtCoordinator.setText("Coordinator" + "\n" + data.getString(COL_EVENT_PERSON) + "\n" + data.getString(COL_EVENT_PERSON_NUMBER));

        /* Event Fees */
        txtFees.setText("Registration Fees" + "\n" + data.getString(COL_EVENT_FEES));

        /* Participation Type */
        txtParticipation.setText("Participation" + "\n" + data.getString(COL_EVENT_PARTICIPATION));

        /* Description */
        if (data.getString(COL_EVENT_DESCRIPTION).equals("null") || data.getString(COL_EVENT_DESCRIPTION).equals("")) {
            viewDescription.setVisibility(View.GONE);
        } else {
            viewDescription.setVisibility(View.VISIBLE);
            txtDesctiption.setText(data.getString(COL_EVENT_DESCRIPTION));
        }

        /* Rules */
        if (data.getString(COL_EVENT_RULES).equals("null") || data.getString(COL_EVENT_RULES).equals("")) {
            viewRules.setVisibility(View.GONE);
        } else {
            viewRules.setVisibility(View.VISIBLE);
            txtRules.setText(data.getString(COL_EVENT_RULES));
        }

        /* BMSCE Exclusive Event */
        if (data.getInt(COL_EVENT_BMSCE) == 1) {
            Log.v("DATA BMSCE", String.valueOf(data.getInt(COL_EVENT_BMSCE)));
            viewForBMSCE.setVisibility(View.VISIBLE);
        } else {
            Log.v("DATA BMSCE", String.valueOf(data.getInt(COL_EVENT_BMSCE)));
            viewForBMSCE.setVisibility(View.GONE);
        }

        /* Event is Full */
        if (data.getInt(COL_EVENT_FULL) == 1) {
            viewFullEvent.setVisibility(View.VISIBLE);
            Log.v("EVENT FULL", "EVENT FULL");
        } else {
            viewFullEvent.setVisibility(View.GONE);
        }

        /* Coordinator Call */
        txtCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + data.getString(COL_EVENT_PERSON_NUMBER)));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Toast.makeText(getApplicationContext(), "Failed : Require Permission to Call", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callIntent);
            }
        });

        /* Set Activity Title */
        this.setTitle(data.getString(COL_EVENT_TITLE));

        /* Event Share Message */
        shareMessage = data.getString(COL_EVENT_TITLE) + "\n\n" +
                data.getString(COL_EVENT_DESCRIPTION) + "\n\n" +
                "Contact : " + data.getString(COL_EVENT_PERSON) + " (" + data.getString(COL_EVENT_PERSON_NUMBER) + ")\n\n" +
                "Shared using PhaseShift App\n#PhaseShift2017";
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                shareEvent();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareEvent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
