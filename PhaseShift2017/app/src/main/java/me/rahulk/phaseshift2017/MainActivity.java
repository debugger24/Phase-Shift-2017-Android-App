package me.rahulk.phaseshift2017;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.rahulk.phaseshift2017.About.AboutAppWebTeamFragment;
import me.rahulk.phaseshift2017.About.AboutCoreCommitteeFragment;
import me.rahulk.phaseshift2017.About.AboutFragment;
import me.rahulk.phaseshift2017.About.AboutPhaseShiftFragment;
import me.rahulk.phaseshift2017.Event.EventFragment;
import me.rahulk.phaseshift2017.Event.EventsCategoryFragment;
import me.rahulk.phaseshift2017.Event.WorkshopsFragment;
import me.rahulk.phaseshift2017.Map.MapFragment;
import me.rahulk.phaseshift2017.Newsfeed.NewsfeedFragment;
import me.rahulk.phaseshift2017.Schedule.ScheduleFragment;

public class MainActivity extends AppCompatActivity implements NewsfeedFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener, AboutPhaseShiftFragment.OnFragmentInteractionListener,
        AboutCoreCommitteeFragment.OnFragmentInteractionListener, AboutAppWebTeamFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener,
        EventsCategoryFragment.OnFragmentInteractionListener, WorkshopsFragment.OnFragmentInteractionListener {

    Fragment fragment = null;
    Class fragmentClass = null;
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_schedule:
                    fragmentClass = ScheduleFragment.class;
                    break;
                case R.id.navigation_events:
                    fragmentClass = EventFragment.class;
                    break;
                case R.id.navigation_home:
                    fragmentClass = NewsfeedFragment.class;
                    break;
                case R.id.navigation_map:
                    fragmentClass = MapFragment.class;
                    break;
                case R.id.navigation_about:
                    fragmentClass = AboutFragment.class;
                    break;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        View view = navigation.findViewById(R.id.navigation_home);
        view.performClick();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
