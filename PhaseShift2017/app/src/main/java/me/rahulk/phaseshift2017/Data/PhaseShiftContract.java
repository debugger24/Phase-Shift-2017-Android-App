package me.rahulk.phaseshift2017.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by debugger24 on 12/08/17.
 */

public class PhaseShiftContract {
    public static final String CONTENT_AUTHORITY = "me.rahulk.phaseshift2017.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EVENT = "event";

    public static final class EventEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;

        public static final String TABLE_NAME = "events";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMNS_EVENT_TITLE = "title";
        public static final String COLUMNS_EVENT_ICON = "icon";
        public static final String COLUMNS_EVENT_DEPARTMENT = "department";
        public static final String COLUMNS_EVENT_TYPE = "type";
        public static final String COLUMNS_EVENT_CATEGORY = "category";
        public static final String COLUMNS_EVENT_BMSCE = "bmsce";
        public static final String COLUMNS_EVENT_FULL = "full";
        public static final String COLUMNS_EVENT_FLAGSHIP = "flagship";
        public static final String COLUMNS_EVENT_PARTICIPATION = "participation";
        public static final String COLUMNS_EVENT_PRIZE1 = "prize1";
        public static final String COLUMNS_EVENT_PRIZE2 = "prize2";
        public static final String COLUMNS_EVENT_PRIZE3 = "prize3";
        public static final String COLUMNS_EVENT_VENUE = "venue";
        public static final String COLUMNS_EVENT_PERSON = "person";
        public static final String COLUMNS_EVENT_PERSON_NUMBER = "personNumber";
        public static final String COLUMNS_EVENT_FEES = "fees";
        public static final String COLUMNS_EVENT_DATE = "date";
        public static final String COLUMNS_EVENT_TIME = "time";
        public static final String COLUMNS_EVENT_DESCRIPTION = "description";
        public static final String COLUMNS_EVENT_RULES = "rules";

        public static Uri buildEventDetailUri(long id) {
            Uri myURI = CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
            return myURI;
        }

        public static Uri buildEventUri() {
            return CONTENT_URI;
        }

    }
}
