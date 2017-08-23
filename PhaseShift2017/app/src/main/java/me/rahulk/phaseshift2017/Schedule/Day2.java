package me.rahulk.phaseshift2017.Schedule;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.rahulk.phaseshift2017.R;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day2, container, false);

        horizontalViewHeader = (CustomScrollView) rootView.findViewById(R.id.horizontalViewHeader);
        horizontalViewBody = (CustomScrollView) rootView.findViewById(R.id.horizontalViewBody);

        horizontalViewHeader.setScrollViewListener(this);
        horizontalViewBody.setScrollViewListener(this);

        // Load JSON
        String jsonString = "{\"day2\":[{\"Venue\":\"Advanced RF lab, EC Block\",\"Events\":[{\"Title\":\"Workshop on Understanding RF Signals\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_tower\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Analog lab(EI) and RK Hall\",\"Events\":[{\"Title\":\"Digital Twin - Virtual Reality in Manufacturing\",\"Col\":1,\"Span\":8,\"Icon\":\"event_bot\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Architecture Studio 8\",\"Events\":[{\"Title\":\"Louis Kaun?\",\"Col\":6,\"Span\":3,\"Icon\":\"event_quiz_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Architecture Studio 9\",\"Events\":[{\"Title\":\"Future Cities\",\"Col\":2,\"Span\":7,\"Icon\":\"event_city_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"BSN hall\",\"Events\":[{\"Title\":\"General Quiz\",\"Col\":1,\"Span\":3,\"Icon\":\"default\",\"Color\":\"colorNone\"},{\"Title\":\"General Quiz\",\"Col\":6,\"Span\":3,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"CCP lab\",\"Events\":[{\"Title\":\"Algomaniac\",\"Col\":6,\"Span\":3,\"Icon\":\"event_code\",\"Color\":\"colorNone\"}]},{\"Venue\":\"CNC &  Robotics lab, Computer lab (IEM)\",\"Events\":[{\"Title\":\"Solid Works Workshop\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_sw\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Communication lab(TCE), EC Block\",\"Events\":[{\"Title\":\"HAM Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_radio\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Computer Lab, 3rd floor, PG block(Chem)\",\"Events\":[{\"Title\":\"Workshop on CFD\",\"Col\":1,\"Span\":8,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Computer lab(EI)\",\"Events\":[{\"Title\":\"Hackathon TCP IP Based on Real Time Data Processing\",\"Col\":2,\"Span\":3,\"Icon\":\"workshop_network\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Computer lab(MBA)\",\"Events\":[{\"Title\":\"Sensor to Cloud Analytics\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_cloud_computing\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Computer Simulation/Synthesis Lab(TCE), EC Block\",\"Events\":[{\"Title\":\"MATLAB GUI Development Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_matlab_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"CS Lab\",\"Events\":[{\"Title\":\"IoT and its Application using Arduino\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_arduino\",\"Color\":\"colorNone\"}]},{\"Venue\":\"CS Lab\",\"Events\":[{\"Title\":\"Android Sensors: A Workshop on Networking\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_android\",\"Color\":\"colorNone\"}]},{\"Venue\":\"CS-5001, CS-5002\",\"Events\":[{\"Title\":\"INFI'NIGHT' Hunt\",\"Col\":2,\"Span\":2,\"Icon\":\"event_math_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Drawing hall 1 and MESH\",\"Events\":[{\"Title\":\"Hammers And Tongs\",\"Col\":3,\"Span\":6,\"Icon\":\"event_quiz_3\",\"Color\":\"colorNone\"}]},{\"Venue\":\"DSP Lab, PG Block 2nd floor\",\"Events\":[{\"Title\":\"Image Processing Workshop\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_matlab_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"EC 201\",\"Events\":[{\"Title\":\"Unsensored\",\"Col\":1,\"Span\":7,\"Icon\":\"workshop_ic\",\"Color\":\"colorNone\"}]},{\"Venue\":\"EC 206\",\"Events\":[{\"Title\":\"Networking Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_router\",\"Color\":\"colorNone\"}]},{\"Venue\":\"EC 209\",\"Events\":[{\"Title\":\"Ideathon\",\"Col\":1,\"Span\":8,\"Icon\":\"event_idea\",\"Color\":\"colorNone\"}]},{\"Venue\":\"ERP LAB, TAYLOR HALL,  IE Lab-Mech block\",\"Events\":[{\"Title\":\"The Fourth Monkey\",\"Col\":2,\"Span\":7,\"Icon\":\"event_laser\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Gopi wall/Mechanical parking lot\",\"Events\":[{\"Title\":\"EZ Rider\",\"Col\":1,\"Span\":8,\"Icon\":\"event_bike\",\"Color\":\"colorNone\"}]},{\"Venue\":\"ICL lab PG block 1st floor\",\"Events\":[{\"Title\":\"Tech Jam\",\"Col\":6,\"Span\":2,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"IEM CR1 (Mech block 1st floor)\",\"Events\":[{\"Title\":\"Link In\",\"Col\":3,\"Span\":5,\"Icon\":\"event_money\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Internet lab\",\"Events\":[{\"Title\":\"Prototype Development and Exhibition\",\"Col\":1,\"Span\":3,\"Icon\":\"event_idea\",\"Color\":\"colorNone\"},{\"Title\":\"SnS-Hack-A-Thon\",\"Col\":5,\"Span\":4,\"Icon\":\"event_pi\",\"Color\":\"colorNone\"}]},{\"Venue\":\"ISE lab\",\"Events\":[{\"Title\":\"Sensor Learning Workshop\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_sensor\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Library Auditorium\",\"Events\":[]},{\"Venue\":\"MC lab, TCE department\",\"Events\":[{\"Title\":\"Ideathon\",\"Col\":2,\"Span\":7,\"Icon\":\"event_idea\",\"Color\":\"colorNone\"}]},{\"Venue\":\"MCA Classroom 2\",\"Events\":[{\"Title\":\"Workshop on Sensors\",\"Col\":1,\"Span\":4,\"Icon\":\"workshop_sensor\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Measurements Lab, PG block 1st floor\",\"Events\":[{\"Title\":\"Make-A-Thon\",\"Col\":1,\"Span\":3,\"Icon\":\"event_infrared_sensor\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Mechanical classroom 2 ( ML-2)\",\"Events\":[{\"Title\":\"Sensors in Automobiles\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_car\",\"Color\":\"colorNone\"}]},{\"Venue\":\"ML-1, Mech Block\",\"Events\":[{\"Title\":\"Situation\",\"Col\":3,\"Span\":3,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"MV hall\",\"Events\":[{\"Title\":\"Remote Sensing and GIS\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_satellite\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block 1st floor (opposite mirror), EC204\",\"Events\":[{\"Title\":\"Fury Road V3.0\",\"Col\":1,\"Span\":8,\"Icon\":\"event_race_car\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG Block 2nd floor IT classroom 03\",\"Events\":[{\"Title\":\"App Development (Android or iOS)\",\"Col\":3,\"Span\":4,\"Icon\":\"event_app_dev\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block 3rd floor BT classroom 3\",\"Events\":[{\"Title\":\"Sensorship\",\"Col\":2,\"Span\":3,\"Icon\":\"event_robot_2\",\"Color\":\"colorNone\"},{\"Title\":\"Battle of The Brains\",\"Col\":6,\"Span\":2,\"Icon\":\"event_micro\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block 3rd floor  BT classroom 2 and Bioinformatics Lab\",\"Events\":[{\"Title\":\"Sensors in Forensics\",\"Col\":2,\"Span\":6,\"Icon\":\"workshop_doctor\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block 3rd floor MBA classroom 3\",\"Events\":[{\"Title\":\"inQUIZitive\",\"Col\":3,\"Span\":2,\"Icon\":\"event_business\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block 3rd floor MBA classroom 4\",\"Events\":[{\"Title\":\"Block and Tackle\",\"Col\":6,\"Span\":2,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CH-3001\",\"Events\":[{\"Title\":\"Episodes\",\"Col\":6,\"Span\":3,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CH-3002\",\"Events\":[{\"Title\":\"Sensors Assemble\",\"Col\":2,\"Span\":2,\"Icon\":\"workshop_sensor\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CV-7002\",\"Events\":[{\"Title\":\"Plus+Codes Hunt\",\"Col\":2,\"Span\":7,\"Icon\":\"event_treasure_map_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block IS-4001,4002,4003\",\"Events\":[{\"Title\":\"VRtigo\",\"Col\":2,\"Span\":3,\"Icon\":\"workshop_webvr\",\"Color\":\"colorNone\"},{\"Title\":\"Logo Mania\",\"Col\":5,\"Span\":3,\"Icon\":\"event_logo\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block IS-4005\",\"Events\":[{\"Title\":\"Sensorship\",\"Col\":1,\"Span\":5,\"Icon\":\"event_robot_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block ML-5001\",\"Events\":[{\"Title\":\"Medbuzz\",\"Col\":2,\"Span\":2,\"Icon\":\"default\",\"Color\":\"colorNone\"},{\"Title\":\"Criminal Case 4.0\",\"Col\":6,\"Span\":3,\"Icon\":\"event_detective\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Project Lab (EI)\",\"Events\":[{\"Title\":\"Workshop on Recent Developments in Automation\",\"Col\":1,\"Span\":8,\"Icon\":\"default\",\"Color\":\"colorNone\"}]},{\"Venue\":\"RK hall\",\"Events\":[{\"Title\":\"Technical Discussion\",\"Col\":3,\"Span\":3,\"Icon\":\"event_plane\",\"Color\":\"colorNone\"}]}]}";

        try {
            generateUI(new JSONObject(jsonString), rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void generateUI(JSONObject jsonString, View rootView) {

        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.scheduleTable);

        try {
            JSONArray venueArray = jsonString.getJSONArray("day2");
            for (int i = 0; i < venueArray.length(); i++) {
                JSONObject venueJSONObject = (JSONObject) venueArray.get(i);

                String venueTitle = venueJSONObject.getString("Venue");

                // Cretae a venue row
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                // For the Venue Title
                View viewVenue = LayoutInflater.from(getActivity()).inflate(R.layout.item_schedule_venue, null);
                TextView txtVenueTitle = (TextView) viewVenue.findViewById(R.id.txtVenueTitle);
                txtVenueTitle.setText(venueTitle);

                tableRow.addView(viewVenue);

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
                    params.column = eventJSONObject.getInt("Col");
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
