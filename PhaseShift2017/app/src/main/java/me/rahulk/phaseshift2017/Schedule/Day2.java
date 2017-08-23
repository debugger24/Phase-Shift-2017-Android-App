package me.rahulk.phaseshift2017.Schedule;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import me.rahulk.phaseshift2017.AppController;
import me.rahulk.phaseshift2017.R;

import static me.rahulk.phaseshift2017.AppConfig.URL_SCHEDULE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Day2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Day2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Day2 extends Fragment implements ScrollViewListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    CustomScrollView horizontalViewHeader;
    CustomScrollView horizontalViewBody;

    View rootView;

    public Day2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Day2.
     */
    // TODO: Rename and change types and number of parameters
    public static Day2 newInstance(String param1, String param2) {
        Day2 fragment = new Day2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(getContext(), "Downloading Latest Schedule", Toast.LENGTH_SHORT).show();
                if (isNetworkAvailable()) {
                    refreshData(rootView);
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_day2, container, false);

        horizontalViewHeader = (CustomScrollView) rootView.findViewById(R.id.horizontalViewHeader);
        horizontalViewBody = (CustomScrollView) rootView.findViewById(R.id.horizontalViewBody);

        horizontalViewHeader.setScrollViewListener(this);
        horizontalViewBody.setScrollViewListener(this);

        // Load Table
        loadTable(rootView);

        return rootView;
    }

    private void loadTable(View rootView) {
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_SCHEDULE);

        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    generateUI(new JSONObject(data), rootView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // making fresh volley request and getting json
            if (isNetworkAvailable()) {
                Toast.makeText(getActivity(), "Downloading new Schedule", Toast.LENGTH_SHORT).show();
                refreshData(rootView);
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void refreshData(final View rootView) {
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_SCHEDULE, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("SCHEDULE RESPONSE", "Response: " + response.toString());
                if (response != null) {
                    generateUI(response, rootView);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SCHEDULE ERROR", "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Failed to download schedule", Toast.LENGTH_SHORT).show();
                // swipeContainer.setRefreshing(false);
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void generateUI(JSONObject jsonString, View rootView) {

        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.scheduleTable);

        while (tableLayout.getChildCount() > 1)
            tableLayout.removeView(tableLayout.getChildAt(tableLayout.getChildCount() - 1));

        try {
            JSONArray venueArray = jsonString.getJSONArray("day2");
            for (int i = 0; i < venueArray.length(); i++) {
                JSONObject venueJSONObject = (JSONObject) venueArray.get(i);

                String venueTitle = venueJSONObject.getString("Venue");

                // Cretae a venue row
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                // For the Venue Title
//                View viewVenue = LayoutInflater.from(getActivity()).inflate(R.layout.item_schedule_venue, null);
//                TextView txtVenueTitle = (TextView) viewVenue.findViewById(R.id.txtVenueTitle);
//                txtVenueTitle.setText(venueTitle);
//
//                tableRow.addView(viewVenue);

                // For each event
                JSONArray venueEvents = venueJSONObject.getJSONArray("Events");
                for (int j = 0; j < venueEvents.length(); j++) {
                    JSONObject eventJSONObject = (JSONObject) venueEvents.get(j);
                    String eventTitle = eventJSONObject.getString("Title");
                    String imageResource = eventJSONObject.getString("Icon");

                    Log.v("EVENT", eventTitle);

                    // For the Event
                    View viewEvent = LayoutInflater.from(getActivity()).inflate(R.layout.item_schedule_event, null);
                    TextView txtEventTitle = (TextView) viewEvent.findViewById(R.id.txtEventTitle);
                    TextView txtEventVenue = (TextView) viewEvent.findViewById(R.id.txtEventVenue);
                    txtEventTitle.setText(eventTitle);
                    txtEventVenue.setText(venueTitle);


                    ImageView eventIcon = (ImageView) viewEvent.findViewById(R.id.eventIcon);
                    Context imageContext = eventIcon.getContext();
                    eventIcon.setImageResource(imageContext.getResources().getIdentifier(imageResource, "drawable", getContext().getPackageName()));

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.span = eventJSONObject.getInt("Span");
                    params.column = eventJSONObject.getInt("Col") - 1;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                    tableRow.addView(viewEvent, params);
                }


                tableLayout.addView(tableRow);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView == horizontalViewBody) {
            horizontalViewHeader.scrollTo(x, y);
        } else if (scrollView == horizontalViewHeader) {
            horizontalViewBody.scrollTo(x, y);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
