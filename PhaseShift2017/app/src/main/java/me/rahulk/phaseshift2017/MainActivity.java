package me.rahulk.phaseshift2017;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.rahulk.phaseshift2017.About.AboutAppWebTeamFragment;
import me.rahulk.phaseshift2017.About.AboutCoreCommitteeFragment;
import me.rahulk.phaseshift2017.About.AboutFragment;
import me.rahulk.phaseshift2017.About.AboutPhaseShiftFragment;
import me.rahulk.phaseshift2017.Event.EventFragment;
import me.rahulk.phaseshift2017.Event.EventsCategoryFragment;
import me.rahulk.phaseshift2017.Event.EventsFragment;
import me.rahulk.phaseshift2017.Event.WorkshopsFragment;
import me.rahulk.phaseshift2017.Map.MapFragment;
import me.rahulk.phaseshift2017.Newsfeed.NewsfeedFragment;
import me.rahulk.phaseshift2017.Schedule.Day1;
import me.rahulk.phaseshift2017.Schedule.Day2;
import me.rahulk.phaseshift2017.Schedule.ScheduleFragment;

public class MainActivity extends AppCompatActivity implements NewsfeedFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener, AboutPhaseShiftFragment.OnFragmentInteractionListener,
        AboutCoreCommitteeFragment.OnFragmentInteractionListener, AboutAppWebTeamFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener,
        EventsCategoryFragment.OnFragmentInteractionListener, WorkshopsFragment.OnFragmentInteractionListener,
        EventsFragment.OnFragmentInteractionListener, Day1.OnFragmentInteractionListener, Day2.OnFragmentInteractionListener {

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

    public void loadEventsFragment(String category) {
        Bundle bundle = new Bundle();
        bundle.putString("category", category);

        fragmentClass = EventsFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("abc");
        fragmentTransaction.replace(R.id.mainContainer, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        View view = navigation.findViewById(R.id.navigation_home);
        view.performClick();

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
