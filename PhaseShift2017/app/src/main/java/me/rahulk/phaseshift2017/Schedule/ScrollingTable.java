package me.rahulk.phaseshift2017.Schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import me.rahulk.phaseshift2017.R;

/**
 * Created by debugger24 on 19/08/17.
 */

public class ScrollingTable extends LinearLayout {
    public ScrollingTable(Context context) {
        super(context);
    }

    public ScrollingTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        TableLayout header = (TableLayout) findViewById(R.id.scheduleHeader);
        TableLayout body = (TableLayout) findViewById(R.id.scheduleTable);

        for (int i = 0; i < 9; i++) {
            TableRow bodyRow = (TableRow) body.getChildAt(0);
            TableRow headerRow = (TableRow) header.getChildAt(0);

            View cell = headerRow.getChildAt(i);
            TableRow.LayoutParams headerRowParams = (TableRow.LayoutParams) cell.getLayoutParams();
            headerRowParams.width = bodyRow.getChildAt(i).getWidth();
            cell.setMinimumWidth(bodyRow.getChildAt(i).getWidth());
        }
    }
}
