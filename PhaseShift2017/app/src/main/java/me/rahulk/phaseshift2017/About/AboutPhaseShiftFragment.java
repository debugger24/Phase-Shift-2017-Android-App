package me.rahulk.phaseshift2017.About;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.rahulk.phaseshift2017.Admin.Admin;
import me.rahulk.phaseshift2017.Quiz.Quiz;
import me.rahulk.phaseshift2017.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutPhaseShiftFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutPhaseShiftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutPhaseShiftFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AboutPhaseShiftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutPhaseShiftFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutPhaseShiftFragment newInstance(String param1, String param2) {
        AboutPhaseShiftFragment fragment = new AboutPhaseShiftFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_about_phase_shift, container, false);

        sharedPreferences = getContext().getSharedPreferences("PhaseShift2017", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        TextView txtPhaseShiftVersion = (TextView) rootView.findViewById(R.id.txtPhaseShiftVersion);

        View emailButton = rootView.findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"phaseshift@bmsce.ac.in"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "PhaseShift 2017");
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        View fbButton = rootView.findViewById(R.id.fbButton);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.facebook.com/techfest.bmsce");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        View twitterButton = rootView.findViewById(R.id.twitterButton);
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://twitter.com/techfest_bmsce");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        View instaButton = rootView.findViewById(R.id.instaButton);
        instaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.instagram.com/techfest.bmsce");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        Button btnQuiz = (Button) rootView.findViewById(R.id.btnLaunchQuiz);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch Quiz Activity
                Intent intent = new Intent(getActivity(), Quiz.class);
                startActivity(intent);
            }
        });

        Button btnAdmin = (Button) rootView.findViewById(R.id.btnLaunchAdmin);

        // Verify Admin Activated or Not
        if (isAdmin()) {
            btnAdmin.setVisibility(View.VISIBLE);
        } else {
            btnAdmin.setVisibility(View.GONE);
        }

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch Admin Activity
                Intent intent = new Intent(getActivity(), Admin.class);
                startActivity(intent);
            }
        });

        txtPhaseShiftVersion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Admin Mode Unlocking");
                alert.setMessage("Enter Code to unlock Admin Features :");

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        if (setAdminCode(value)) {
                            Toast.makeText(getContext(), "Admin Mode Unlocked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Invalid Unlock Code", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                });

                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                return;
                            }
                        });
                alert.show();
                return true;
            }
        });

        return rootView;
    }

    private boolean setAdminCode(String code) {
        if (code.equals("ADMN_765056") ||
                code.equals("DEPT_701121_Bio-Technology") ||
                code.equals("DEPT_946402_Civil Engineering") ||
                code.equals("DEPT_557457_Chemical Engineering") ||
                code.equals("DEPT_932794_Computer Science and Engineering") ||
                code.equals("DEPT_776689_Electrical and Electronics Engineering") ||
                code.equals("DEPT_983432_Electronics and Communication Engineering") ||
                code.equals("DEPT_653552_Electronics and Instrumentation Engineering") ||
                code.equals("DEPT_111288_Industrial Engineering and Management") ||
                code.equals("DEPT_253132_Information Science and Engineering") ||
                code.equals("DEPT_567146_Master of Computer Application") ||
                code.equals("DEPT_912905_Mechanical Engineering") ||
                code.equals("DEPT_865526_Medical Electronics") ||
                code.equals("DEPT_247099_Telecommunication Engineering") ||
                code.equals("DEPT_407684_BMSCE IEEE") ||
                code.equals("DEPT_196403_Architecture") ||
                code.equals("DEPT_832570_Master of Business Administration") ||
                code.equals("DEPT_165169_Pentagram") ||
                code.equals("DEPT_964018_Alternate Universe") ||
                code.equals("DEPT_136047_Qcaine - Quiz club") ||
                code.equals("DATA_370250_Database Team")) {

            editor.putString("AdminCode", code);
            editor.putBoolean("IsAdmin", true);
            editor.commit();
            return true;
        } else {
            return false;
        }

    }

    private boolean isAdmin() {
        return sharedPreferences.getBoolean("IsAdmin", false);
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
