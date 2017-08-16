package me.rahulk.phaseshift2017.Event;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

import me.rahulk.phaseshift2017.MainActivity;
import me.rahulk.phaseshift2017.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CategoryAdapter categoryAdapter;

    Category[] categories = {
            new Category("Mission Possible", "Quest Events", R.drawable.cat01),
            new Category("Across the Panorama", "General Events", R.drawable.cat02),
            new Category("Ingenuity", "Creative Events", R.drawable.cat03),
            new Category("Semicolon Redefined", "Coding Events", R.drawable.cat04),
            new Category("Maze Break", "Circuit Events", R.drawable.cat05),
            new Category("Automatons", "Robotics Events", R.drawable.cat06),
            new Category("Grease Monkey", "Mech Events", R.drawable.cat07),
            new Category("Not so FAQ", "Quizzing Events", R.drawable.cat08),
            new Category("Pioneer", "Innovation Events", R.drawable.cat09)
    };

    private OnFragmentInteractionListener mListener;

    public EventsCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsCategoryFragment newInstance(String param1, String param2) {
        EventsCategoryFragment fragment = new EventsCategoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_events_category, container, false);

        categoryAdapter = new CategoryAdapter(getActivity(), Arrays.asList(categories));

        ListView listView = (ListView) rootView.findViewById(R.id.eventCategories);
        listView.setAdapter(categoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // MainActivity will replace fragment
                ((MainActivity)getActivity()).loadEventsFragment(categories[position].categoryTitle);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
