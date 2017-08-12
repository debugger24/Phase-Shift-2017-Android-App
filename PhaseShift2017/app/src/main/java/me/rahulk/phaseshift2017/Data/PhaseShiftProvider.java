package me.rahulk.phaseshift2017.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by debugger24 on 12/08/17.
 */

public class PhaseShiftProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private PhaseShiftDbHelper mOpenHelper;

    static final int EVENT = 100;
    static final int EVENT_DETAIL = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PhaseShiftContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, PhaseShiftContract.PATH_EVENT, EVENT);
        matcher.addURI(authority, PhaseShiftContract.PATH_EVENT + "/*", EVENT_DETAIL);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PhaseShiftDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case EVENT:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PhaseShiftContract.EventEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_DETAIL:
                selection = PhaseShiftContract.EventEntry._ID + " = ?";
                String[] selectionArg = {uri.getPathSegments().get(1)};
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PhaseShiftContract.EventEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArg,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case EVENT:
                return PhaseShiftContract.EventEntry.CONTENT_TYPE;
            case EVENT_DETAIL:
                return PhaseShiftContract.EventEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case EVENT: {
                long _id = db.insert(PhaseShiftContract.EventEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    returnUri = PhaseShiftContract.EventEntry.buildEventDetailUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) selection = "1";

        switch (match) {
            case EVENT:
                rowsDeleted = db.delete(PhaseShiftContract.EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENT:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PhaseShiftContract.EventEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
