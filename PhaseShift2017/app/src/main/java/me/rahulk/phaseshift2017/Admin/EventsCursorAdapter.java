package me.rahulk.phaseshift2017.Admin;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.rahulk.phaseshift2017.R;

/**
 * Created by debugger24 on 03/09/17.
 */

public class EventsCursorAdapter extends CursorAdapter implements Filterable {
    private int full, cancel;
    private int flagship;
    private int maxLimitValue, currentRegValue;
    private String type;

    public EventsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_event_db, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView eventTitle = (TextView) view.findViewById(R.id.title);
        eventTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));

        TextView eventDepartment = (TextView) view.findViewById(R.id.department);
        eventDepartment.setText(cursor.getString(cursor.getColumnIndexOrThrow("department")));

        TextView flagshipEvent = (TextView) view.findViewById(R.id.flagship);
        TextView alertEventFull = (TextView) view.findViewById(R.id.alertEventFull);
        TextView maxLimit = (TextView) view.findViewById(R.id.maxLimit);
        TextView currentReg = (TextView) view.findViewById(R.id.currentReg);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView cancel = (TextView) view.findViewById(R.id.alertEventCancel);
        ProgressBar regProgress = (ProgressBar) view.findViewById(R.id.regProgress);

        flagship = cursor.getInt(cursor.getColumnIndexOrThrow("flagship"));
        full = cursor.getInt(cursor.getColumnIndexOrThrow("full"));
        maxLimitValue = cursor.getInt(cursor.getColumnIndexOrThrow("maximum"));
        currentRegValue = cursor.getInt(cursor.getColumnIndexOrThrow("current"));

        if (flagship == 1) {
            flagshipEvent.setVisibility(View.VISIBLE);
        } else {
            flagshipEvent.setVisibility(View.GONE);
        }

        if (full == 1) {
            alertEventFull.setVisibility(View.VISIBLE);
        } else {
            alertEventFull.setVisibility(View.GONE);
        }

        maxLimit.setText("Max : " + maxLimitValue);
        currentReg.setText("Current : " + currentRegValue);

        regProgress.setMax(maxLimitValue);
        regProgress.setProgress(currentRegValue);

        if (cursor.getString(cursor.getColumnIndexOrThrow("type")).equals("Event")) {
            type.setText("E");
            type.setBackgroundColor(Color.MAGENTA);
        } else {
            type.setText("W");
            type.setBackgroundColor(Color.BLUE);
        }

        if (cursor.getInt(cursor.getColumnIndexOrThrow("active")) == 1) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }
    }
}
