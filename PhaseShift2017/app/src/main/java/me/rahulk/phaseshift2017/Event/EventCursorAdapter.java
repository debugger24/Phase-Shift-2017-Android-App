package me.rahulk.phaseshift2017.Event;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.rahulk.phaseshift2017.R;

/**
 * Created by debugger24 on 14/08/17.
 */

public class EventCursorAdapter extends CursorAdapter {
    private int full;
    private int flagship;

    public EventCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_event, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imageView = (ImageView) view.findViewById(R.id.event_icon);
        Context imageContext = imageView.getContext();
        int id;
        Log.v("TAG", cursor.getString(cursor.getColumnIndexOrThrow("icon")));

        if (cursor.getString(cursor.getColumnIndexOrThrow("icon")).equals("") || cursor.getString(cursor.getColumnIndexOrThrow("icon")) == null
                || cursor.getString(cursor.getColumnIndexOrThrow("icon")).equals("null")) {
            id = imageContext.getResources().getIdentifier("ps_logo_outline", "drawable", context.getPackageName());
        } else {
            id = imageContext.getResources().getIdentifier(cursor.getString(cursor.getColumnIndexOrThrow("icon")), "drawable", context.getPackageName());
        }
        imageView.setImageResource(id);


        TextView eventTitle = (TextView) view.findViewById(R.id.title);
        eventTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));

        TextView eventDepartment = (TextView) view.findViewById(R.id.department);
        eventDepartment.setText(cursor.getString(cursor.getColumnIndexOrThrow("department")));

        TextView flagshipEvent = (TextView) view.findViewById(R.id.flagship);
        TextView alertEventFull = (TextView) view.findViewById(R.id.alertEventFull);

        full = cursor.getInt(cursor.getColumnIndexOrThrow("full"));
        flagship = cursor.getInt(cursor.getColumnIndexOrThrow("flagship"));

        if (full == 1) {
            alertEventFull.setVisibility(View.VISIBLE);
        } else {
            alertEventFull.setVisibility(View.GONE);
        }

        if (flagship == 1) {
            flagshipEvent.setVisibility(View.VISIBLE);
        } else {
            flagshipEvent.setVisibility(View.GONE);
        }
    }
}
