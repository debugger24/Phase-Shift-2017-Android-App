package me.rahulk.phaseshift2017.About;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by debugger24 on 06/06/17.
 */

public class AboutPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "PhaseShift", "Core Committee", "Team"};
    private Context context;

    public AboutPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AboutPhaseShiftFragment();
        }
        else if (position == 1){
            return new AboutCoreCommitteeFragment();
        }
        else {
            return new AboutAppWebTeamFragment();
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