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
 * {@link Day1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Day1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Day1 extends Fragment implements ScrollViewListener {
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

    public Day1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Day1.
     */
    // TODO: Rename and change types and number of parameters
    public static Day1 newInstance(String param1, String param2) {
        Day1 fragment = new Day1();
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
        View rootView = inflater.inflate(R.layout.fragment_day1, container, false);

        horizontalViewHeader = (CustomScrollView) rootView.findViewById(R.id.horizontalViewHeader);
        horizontalViewBody = (CustomScrollView) rootView.findViewById(R.id.horizontalViewBody);

        horizontalViewHeader.setScrollViewListener(this);
        horizontalViewBody.setScrollViewListener(this);

        // Load JSON
        String jsonString = "{\"day1\":[{\"Venue\":\"Indoor Stadium\",\"Events\":[{\"Title\":\"Inauguration\",\"Col\":1,\"Span\":3,\"Icon\":\"ps_logo_outline\",\"Color\":\"#f23a3a\"}]},{\"Venue\":\"Advanced RF lab, EC Block\",\"Events\":[{\"Title\":\"Arduino - Basics and Interfacing Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_arduino\",\"Color\":\"#9b5836\"}]},{\"Venue\":\"Architecture Studio 8\",\"Events\":[{\"Title\":\"Lensation\",\"Col\":3,\"Span\":6,\"Icon\":\"event_camera_2\",\"Color\":\"#447025\"}]},{\"Venue\":\"Architecture Studio 9\",\"Events\":[{\"Title\":\"Divide and Sculpt\",\"Col\":3,\"Span\":6,\"Icon\":\"ps_logo_outline\",\"Color\":\"#253570\"}]},{\"Venue\":\"Colab - Mechanical\",\"Events\":[{\"Title\":\"Botwars\",\"Col\":2,\"Span\":7,\"Icon\":\"event_bot\",\"Color\":\"#5f2570\"}]},{\"Venue\":\"Communication lab(TCE), EC Block\",\"Events\":[{\"Title\":\"Project J\",\"Col\":2,\"Span\":7,\"Icon\":\"event_processor\",\"Color\":\"#256e70\"}]},{\"Venue\":\"Computer Lab, 3rd floor, PG block(Chem)\",\"Events\":[{\"Title\":\"Workshop on CFD\",\"Col\":1,\"Span\":8,\"Icon\":\"ps_logo_outline\",\"Color\":\"#9b5836\"}]},{\"Venue\":\"Computer lab(EI)\",\"Events\":[{\"Title\":\"Hackathon TCP IP Based on Real Time Data Processing\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_network\",\"Color\":\"color5\"}]},{\"Venue\":\"Computer Simulation/Synthesis Lab(TCE), EC Block\",\"Events\":[{\"Title\":\"MATLAB GUI Development Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_matlab_2\",\"Color\":\"color1\"}]},{\"Venue\":\"Concrete Lab (Civil)\",\"Events\":[{\"Title\":\"Non - Destructive Testing\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_compass\",\"Color\":\"color1\"}]},{\"Venue\":\"CS-5002 and CS-5003\",\"Events\":[{\"Title\":\"Confoundo\",\"Col\":2,\"Span\":3,\"Icon\":\"event_math\",\"Color\":\"color9\"},{\"Title\":\"Game Of Strategies\",\"Col\":6,\"Span\":3,\"Icon\":\"event_strategy\",\"Color\":\"color10\"}]},{\"Venue\":\"CS-Lab, section 1\",\"Events\":[{\"Title\":\"Android Senses: A Workshop on Multimedia\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_android\",\"Color\":\"color1\"}]},{\"Venue\":\"CS-Lab, section 2\",\"Events\":[{\"Title\":\"Tech-Hunt 2.0\",\"Col\":6,\"Span\":3,\"Icon\":\"event_treasure_map\",\"Color\":\"color5\"}]},{\"Venue\":\"Drawing hall 1 and CADD lab\",\"Events\":[{\"Title\":\"Encase It\",\"Col\":2,\"Span\":6,\"Icon\":\"event_design\",\"Color\":\"color8\"}]},{\"Venue\":\"EC 201, VHDL lab, AME lab\",\"Events\":[{\"Title\":\"Bidding Wars-An Auction Ecstasy\",\"Col\":1,\"Span\":7,\"Icon\":\"event_auction\",\"Color\":\"color4\"}]},{\"Venue\":\"EC 201,202,203, DEC lab\",\"Events\":[{\"Title\":\"Geekatron\",\"Col\":1,\"Span\":7,\"Icon\":\"event_quiz_3\",\"Color\":\"color4\"}]},{\"Venue\":\"EC 206\",\"Events\":[{\"Title\":\"Networking Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_router\",\"Color\":\"color1\"}]},{\"Venue\":\"EC 207\",\"Events\":[{\"Title\":\"Walk-In Mock\",\"Col\":2,\"Span\":7,\"Icon\":\"event_resume\",\"Color\":\"color3\"}]},{\"Venue\":\"EC 209\",\"Events\":[{\"Title\":\"T-REX Hunt\",\"Col\":2,\"Span\":7,\"Icon\":\"event_antenna\",\"Color\":\"color6\"}]},{\"Venue\":\"ERP LAB, TAYLOR HALL, IE Lab\",\"Events\":[{\"Title\":\"The Fourth Monkey\",\"Col\":4,\"Span\":3,\"Icon\":\"event_laser\",\"Color\":\"color4\"}]},{\"Venue\":\"FDC hall and ML Lab\",\"Events\":[{\"Title\":\"Cimethon\",\"Col\":2,\"Span\":7,\"Icon\":\"event_health\",\"Color\":\"color10\"}]},{\"Venue\":\"Gopi wall/Mechanical parking lot\",\"Events\":[{\"Title\":\"EZ Rider\",\"Col\":2,\"Span\":7,\"Icon\":\"event_bike\",\"Color\":\"color8\"}]},{\"Venue\":\"IEM CR1 (Mech block 1st floor)\",\"Events\":[{\"Title\":\"Sensive\",\"Col\":2,\"Span\":7,\"Icon\":\"event_race_car_2\",\"Color\":\"colorNone\"}]},{\"Venue\":\"IEM CR2 (Mech block 1st floor)\",\"Events\":[{\"Title\":\"Survival of the Cyborgs\",\"Col\":2,\"Span\":7,\"Icon\":\"ps_logo_outline\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Internet Lab\",\"Events\":[{\"Title\":\"Code-A-Thon 2.0\",\"Col\":3,\"Span\":3,\"Icon\":\"event_code\",\"Color\":\"colorNone\"}]},{\"Venue\":\"MBA computer lab\",\"Events\":[{\"Title\":\"Sensor to Cloud Analytics\",\"Col\":1,\"Span\":8,\"Icon\":\"workshop_cloud_computing\",\"Color\":\"color\"}]},{\"Venue\":\"MC lab, TCE department\",\"Events\":[{\"Title\":\"IoT Workshop\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_iot\",\"Color\":\"color\"}]},{\"Venue\":\"MCA Classroom 1\",\"Events\":[{\"Title\":\"Technicolor\",\"Col\":6,\"Span\":3,\"Icon\":\"ps_logo_outline\",\"Color\":\"color\"}]},{\"Venue\":\"MCA Classroom 2\",\"Events\":[{\"Title\":\"Les Quizerables\",\"Col\":1,\"Span\":8,\"Icon\":\"event_quiz_4\",\"Color\":\"color\"}]},{\"Venue\":\"Measurements Lab , 1st floor\",\"Events\":[{\"Title\":\"Make-A-Thon\",\"Col\":3,\"Span\":6,\"Icon\":\"event_infrared_sensor\",\"Color\":\"color\"}]},{\"Venue\":\"MESH\",\"Events\":[{\"Title\":\"BMSCE IEEE Paper Presentation Competition\",\"Col\":2,\"Span\":3,\"Icon\":\"event_presentation\",\"Color\":\"color\"}]},{\"Venue\":\"ML-1, Mech block\",\"Events\":[{\"Title\":\"Non - Destructive Testing\",\"Col\":2,\"Span\":7,\"Icon\":\"workshop_compass\",\"Color\":\"color\"}]},{\"Venue\":\"PG block 1st floor IT Classroom 1\",\"Events\":[{\"Title\":\"Sensearch\",\"Col\":1,\"Span\":4,\"Icon\":\"ps_logo_outline\",\"Color\":\"color\"}]},{\"Venue\":\"PG block 3rd floor  BT classroom 2\",\"Events\":[{\"Title\":\"Start-Up Wars\",\"Col\":4,\"Span\":4,\"Icon\":\"event_client_company\",\"Color\":\"color\"}]},{\"Venue\":\"PG block 3rd floor  BT classroom 3\",\"Events\":[{\"Title\":\"Gen-Ethics\",\"Col\":1,\"Span\":2,\"Icon\":\"event_speak\",\"Color\":\"color\"},{\"Title\":\"Bio-Race\",\"Col\":4,\"Span\":5,\"Icon\":\"event_treasure_hunt\",\"Color\":\"color\"}]},{\"Venue\":\"PG block 3rd floor MBA classroom 3\",\"Events\":[{\"Title\":\"Business Plan Competition\",\"Col\":3,\"Span\":2,\"Icon\":\"event_business\",\"Color\":\"color\"}]},{\"Venue\":\"PG block 3rd floor MBA classroom 4\",\"Events\":[{\"Title\":\"Ad Making\",\"Col\":6,\"Span\":2,\"Icon\":\"event_camera\",\"Color\":\"color\"}]},{\"Venue\":\"PG block 3rd floor MBA classroom 5\",\"Events\":[{\"Title\":\"Workshop on Design Thinking\",\"Col\":3,\"Span\":2,\"Icon\":\"ps_logo_outline\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CH-3001\",\"Events\":[{\"Title\":\"It's Elementary, Watson\",\"Col\":2,\"Span\":2,\"Icon\":\"event_quiz_4\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CH-3002\",\"Events\":[{\"Title\":\"The Marauders' Maze\",\"Col\":4,\"Span\":2,\"Icon\":\"event_treasure_map\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CS-5001\",\"Events\":[{\"Title\":\"SnS-Techvita\",\"Col\":3,\"Span\":6,\"Icon\":\"event_quiz\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CS-5004\",\"Events\":[{\"Title\":\"SnS-Technical Writing\",\"Col\":4,\"Span\":5,\"Icon\":\"event_paper\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CV-7003\",\"Events\":[{\"Title\":\"Presensortation\",\"Col\":1,\"Span\":8,\"Icon\":\"event_presentation\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block CV-7010\",\"Events\":[{\"Title\":\"Potpourri\",\"Col\":1,\"Span\":8,\"Icon\":\"event_quiz_5\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block EC-6001\",\"Events\":[{\"Title\":\"Sensor Of Humour - The Mad-Ads\",\"Col\":2,\"Span\":7,\"Icon\":\"event_reel\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block EE-6003\",\"Events\":[{\"Title\":\"Tardis 2.0\",\"Col\":2,\"Span\":2,\"Icon\":\"ps_logo_outline\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block IS-4003\",\"Events\":[{\"Title\":\"Knock Some Sense\",\"Col\":6,\"Span\":3,\"Icon\":\"workshop_sensor\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block ML-5001\",\"Events\":[{\"Title\":\"LabVIEW & myDAQ\",\"Col\":1,\"Span\":8,\"Icon\":\"event_health\",\"Color\":\"colorNone\"}]},{\"Venue\":\"PG block ML-5002 and AIC Lab(ML)\",\"Events\":[{\"Title\":\"Triple-Decker\",\"Col\":3,\"Span\":3,\"Icon\":\"event_ear\",\"Color\":\"colorNone\"}]},{\"Venue\":\"Power Electroics Lab\",\"Events\":[{\"Title\":\"Circuit Challenge\",\"Col\":1,\"Span\":6,\"Icon\":\"workshop_circuit\",\"Color\":\"colorNone\"}]}]}";

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
            JSONArray venueArray = jsonString.getJSONArray("day1");
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
