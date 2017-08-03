package me.rahulk.phaseshift2017.About;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import me.rahulk.phaseshift2017.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutCoreCommitteeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutCoreCommitteeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutCoreCommitteeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AboutCoreCommitteeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutCoreCommitteeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutCoreCommitteeFragment newInstance(String param1, String param2) {
        AboutCoreCommitteeFragment fragment = new AboutCoreCommitteeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_about_core_committee, container, false);

        ArrayList<Person> coreList = new ArrayList<>();

        coreList.add(new Person("Prajwal Damodar Prabhu", "+91 9538471925", null, R.drawable.prajwal));
        coreList.add(new Person("Akshay R Deogiri", "+91 9481310600", null, R.drawable.akshay));
        coreList.add(new Person("Nihal M Navada", "+91 9448905422", null, R.drawable.nihal));
        coreList.add(new Person("T Mithun", "+91 9535262771", null, R.drawable.mithun));
        coreList.add(new Person("Saagari S", "+91 9035024581", null, R.drawable.saagari));
        coreList.add(new Person("G G Nagasayee", "+91 8553402559", null, R.drawable.nagasayee));
        coreList.add(new Person("Akash Ashok Patil", "+91 8861397071", null, R.drawable.akash));

        ListView listView = (ListView) rootView.findViewById(R.id.coreCommitteeList);
        PersonAdapter contactCoreAdapter = new PersonAdapter(getContext(), coreList);
        listView.setAdapter(contactCoreAdapter);

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
