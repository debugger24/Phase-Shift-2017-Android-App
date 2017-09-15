package me.rahulk.phaseshift2017.Sponsors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import me.rahulk.phaseshift2017.AppController;
import me.rahulk.phaseshift2017.Newsfeed.FeedItem;
import me.rahulk.phaseshift2017.Newsfeed.FeedListAdapter;
import me.rahulk.phaseshift2017.R;

import static me.rahulk.phaseshift2017.AppConfig.URL_NEWSFEEDS;
import static me.rahulk.phaseshift2017.AppConfig.URL_SPONSORS;

public class SponsorsActivity extends AppCompatActivity {

    private ListView listView;
    private List<SponsorRow> sponsorRows;
    private SponsorsListAdapter sponsorsListAdapter;
    public Toolbar toolbar;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Phase Shift 2017 Sponsors");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.sponsorsList);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                if (isNetworkAvailable()) {
                    refreshData();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        sponsorRows = new ArrayList<SponsorRow>();

        sponsorsListAdapter = new SponsorsListAdapter(SponsorsActivity.this, sponsorRows);
        listView.setAdapter(sponsorsListAdapter);

        updateSponsors();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateSponsors() {
        swipeContainer.setRefreshing(true);
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_SPONSORS);

        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // Make a fresh request
        if (isNetworkAvailable()) {
            refreshData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            swipeContainer.setRefreshing(false);
        }


    }

    private void refreshData() {
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_SPONSORS, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("SPONSOR_TAG", "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SPONSOR_TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to download sponsors. Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void parseJsonFeed(JSONObject response) {
        Log.v("parseJsonFeed", response.toString());
        try {
            JSONArray sponsorRowArray = response.getJSONArray("sponsors");
            sponsorRows.clear();
            for (int i = 0; i < sponsorRowArray.length(); i++) {

                JSONObject sponsorRowObject = (JSONObject) sponsorRowArray.get(i);

                SponsorRow sponsorRow = new SponsorRow(sponsorRowObject.getString("type"));

                JSONArray sponsorsArray = sponsorRowObject.getJSONArray("sponsors");
                for (int j = 0; j < sponsorsArray.length(); j++) {
                    JSONObject sponsorObject = (JSONObject) sponsorsArray.get(j);
                    Sponsor sponsor = new Sponsor(sponsorObject.getString("name"), sponsorObject.getString("logo"), sponsorObject.getString("url"));
                    sponsorRow.addSponsor(sponsor);
                }
                sponsorRows.add(sponsorRow);
            }

            Log.v("SPONSOR ROWS", sponsorRows.toString());

            // notify data changes to list adapater
            sponsorsListAdapter = new SponsorsListAdapter(SponsorsActivity.this, sponsorRows);
            listView.setAdapter(sponsorsListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            swipeContainer.setRefreshing(false);
        }
    }
}
