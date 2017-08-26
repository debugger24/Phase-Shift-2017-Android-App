package me.rahulk.phaseshift2017.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by debugger24 on 12/08/17.
 */

public class PhaseShiftDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PhaseShiftDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "phaseshift.db";
    private static final int DATABASE_VERSION = 7;

    public PhaseShiftDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_EVENT_TABLE = "CREATE TABLE " + PhaseShiftContract.EventEntry.TABLE_NAME + " ("
                + PhaseShiftContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + " TEXT NOT NULL UNIQUE, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_ICON + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_TYPE + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_CATEGORY + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_BMSCE + " INTEGER NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_FULL + " INTEGER NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_FLAGSHIP + " INTEGER NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_PARTICIPATION + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE1 + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE2 + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE3 + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_VENUE + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_PERSON + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_PERSON_NUMBER + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_FEES + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_DATE + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_TIME + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_DESCRIPTION + " TEXT NOT NULL, "
                + PhaseShiftContract.EventEntry.COLUMNS_EVENT_RULES + " TEXT NOT NULL, "
                + "UNIQUE (" + PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE + ") ON CONFLICT REPLACE);";

        Log.v("CREAET TABLE QUERY ", SQL_CREATE_EVENT_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PhaseShiftContract.EventEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
