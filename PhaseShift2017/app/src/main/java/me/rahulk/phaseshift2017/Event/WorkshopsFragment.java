package me.rahulk.phaseshift2017.Event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.LoaderManager;
import android.widget.AdapterView;
import android.widget.ListView;

import me.rahulk.phaseshift2017.Data.PhaseShiftContract;
import me.rahulk.phaseshift2017.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkshopsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkshopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkshopsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EventCursorAdapter eventCursorAdapter;
    private static final int FORECAST_LOADER = 1;

    private static final String[] EVENT_COLUMNS = {
            PhaseShiftContract.EventEntry._ID,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_BMSCE,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_FULL,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_ICON,
            PhaseShiftContract.EventEntry.COLUMNS_EVENT_FLAGSHIP
    };

    static final int COL_EVENT_ID = 0;
    static final int COL_EVENT_TITLE = 1;
    static final int COL_EVENT_DEPARTMENT = 2;
    static final int COL_EVENT_BMSCE = 3;
    static final int COL_EVENT_FULL = 4;
    static final int COL_EVENT_ICON = 5;
    static final int COL_EVENT_FLAGSHIP = 6;

    public WorkshopsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkshopsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkshopsFragment newInstance(String param1, String param2) {
        WorkshopsFragment fragment = new WorkshopsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_workshops, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lstEvent);

        eventCursorAdapter = new EventCursorAdapter(getActivity(), null, 0);

        listView.setAdapter(eventCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    Intent intent = new Intent(getActivity(), EventDetails.class).setData(PhaseShiftContract.EventEntry.buildEventDetailUri(cursor.getLong(COL_EVENT_ID)));
                    startActivity(intent);
                }
            }
        });

        return rootView;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(1, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT + " ASC";
        Uri allEvents = PhaseShiftContract.EventEntry.buildEventUri();
        String selection = PhaseShiftContract.EventEntry.COLUMNS_EVENT_TYPE + " = ?";
        String[] selectionArgs = {"Workshop"};
        return new CursorLoader(getActivity(), allEvents, EVENT_COLUMNS, selection, selectionArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        eventCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        eventCursorAdapter.swapCursor(null);
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
