package me.rahulk.phaseshift2017.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import me.rahulk.phaseshift2017.AppController;
import me.rahulk.phaseshift2017.Data.FetchEventTask;
import me.rahulk.phaseshift2017.R;
import me.rahulk.phaseshift2017.SplashScreen.SplashScreen;

import static me.rahulk.phaseshift2017.AppConfig.URL_ADMIN;
import static me.rahulk.phaseshift2017.AppConfig.URL_QUIZ;

public class Admin extends AppCompatActivity {
    private View viewTshirt, viewApp, viewRegistration, viewEvents;
    private TextView tshirtLastUpdate, tshirtCounter, appLastUpdate, appCounter, registrationLastUpdate, registrationCounter, eventsLastUpdate, eventsCounter;
    private SwipeRefreshLayout swipeContainer;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        this.setTitle("PhaseShift Admin Panel");

        sharedPreferences = this.getSharedPreferences("PhaseShift2017", this.MODE_PRIVATE);
        editor = sharedPreferences.edit();

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

        viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, Stats.class);
                startActivity(i);
            }
        });

        // Check Access Level and Load Views
        switch (getAccessCode()) {
            case "":
                finish();
                break;
            case "RULE_BOOK":
                viewEvents.setVisibility(View.VISIBLE);
                break;
            case "DATABASE":
                viewEvents.setVisibility(View.VISIBLE);
                viewRegistration.setVisibility(View.VISIBLE);
                break;
            case "ADMIN":
                viewTshirt.setVisibility(View.VISIBLE);
                viewApp.setVisibility(View.VISIBLE);
                viewRegistration.setVisibility(View.VISIBLE);
                viewEvents.setVisibility(View.VISIBLE);
                break;
            default:
                finish();
        }

        refreshContent();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                if (isNetworkAvailable()) {
                    refreshContent();
                } else {
                    Toast.makeText(Admin.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void refreshContent() {
        if (isNetworkAvailable()) {
            // Fetch Events
            new FetchEventTask(this).execute();

            // Fetch Counters
            fetchCounters();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCounters() {
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_ADMIN, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.v("LOG", response.toString());
                VolleyLog.d("ADMIN RESPONSE", "Response: " + response.toString());
                if (response != null) {
                    updateCounters(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ADMIN ERROR", "Error: " + error.getMessage());
                swipeContainer.setRefreshing(false);
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void updateCounters(JSONObject response) {
        try {
            tshirtCounter.setText(response.getJSONObject("tshirt").getString("count"));
            tshirtLastUpdate.setText(response.getJSONObject("tshirt").getString("last_update"));

            appCounter.setText(response.getJSONObject("app").getString("count"));
            appLastUpdate.setText(response.getJSONObject("app").getString("last_update"));

            registrationCounter.setText(response.getJSONObject("registration").getString("count"));
            registrationLastUpdate.setText(response.getJSONObject("registration").getString("last_update"));

            eventsCounter.setText(response.getJSONObject("events").getString("count"));
            eventsLastUpdate.setText(response.getJSONObject("events").getString("last_update"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            swipeContainer.setRefreshing(false);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void hideAll() {
        viewTshirt.setVisibility(View.GONE);
        viewApp.setVisibility(View.GONE);
        viewRegistration.setVisibility(View.GONE);
        viewEvents.setVisibility(View.GONE);
    }

    private String getAccessCode() {
        hideAll();
        return ("ADMIN");
        //return sharedPreferences.getString("ACCESS_CODE", "");
    }
}
