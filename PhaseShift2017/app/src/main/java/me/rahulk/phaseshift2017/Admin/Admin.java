package me.rahulk.phaseshift2017.Admin;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.rahulk.phaseshift2017.R;

public class Admin extends AppCompatActivity {
    private View viewTshirt, viewApp, viewRegistration, viewEvents;
    private TextView tshirtLastUpdate, tshirtCounter, appLastUpdate, appCounter, registrationLastUpdate, registrationCounter, eventsLastUpdate, eventsCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        viewTshirt = findViewById(R.id.viewTshirt);
        viewApp = findViewById(R.id.viewApp);
        viewRegistration = findViewById(R.id.viewRegistration);
        viewEvents = findViewById(R.id.viewEvents);

        tshirtLastUpdate = (TextView) findViewById(R.id.tshirtLastUpdate);
        tshirtCounter = (TextView) findViewById(R.id.tshirtCounter);

        appLastUpdate = (TextView) findViewById(R.id.appLastUpdate);
        appCounter = (TextView) findViewById(R.id.appCounter);

        registrationLastUpdate = (TextView) findViewById(R.id.registrationLastUpdate);
        registrationCounter = (TextView) findViewById(R.id.registrationCounter);

        eventsLastUpdate = (TextView) findViewById(R.id.eventsLastUpdate);
        eventsCounter = (TextView) findViewById(R.id.eventsCounter);

        // Check Access Level and Load Views

    }
}
