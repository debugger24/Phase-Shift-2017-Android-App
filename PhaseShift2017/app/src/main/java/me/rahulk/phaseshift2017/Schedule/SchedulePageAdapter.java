package me.rahulk.phaseshift2017.Schedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.rahulk.phaseshift2017.About.AboutAppWebTeamFragment;
import me.rahulk.phaseshift2017.About.AboutCoreCommitteeFragment;
import me.rahulk.phaseshift2017.About.AboutPhaseShiftFragment;

/**
 * Created by debugger24 on 19/08/17.
 */

public class SchedulePageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"15th Sep", "16th Sep"};
    private Context context;

    public SchedulePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Day1();
        } else {
            return new Day2();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
