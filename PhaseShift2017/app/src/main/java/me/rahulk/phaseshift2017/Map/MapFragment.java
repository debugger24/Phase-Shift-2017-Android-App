package me.rahulk.phaseshift2017.Map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import me.rahulk.phaseshift2017.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                // Add a marker in Sydney, Australia, and move the camera.
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(12.9418, 77.5661))
                        .zoom(17).build();

                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.setMyLocationEnabled(true);



                /* BUILDINGS */
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.941670, 77.565803)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("PG Block")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942307, 77.565921)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("ECE Block")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942255, 77.566306)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("Classroom Block")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942337, 77.565457)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("Mechanical Block")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942637, 77.566075)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("Telecom Block")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.940679, 77.564932)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("Science Block")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.940187, 77.565375)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("Multipurpose Hall")).showInfoWindow();

                /* SPECIAL AREAS */
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942289, 77.566662)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_library))).title("Library Auditorium")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.941089, 77.566137)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_location))).title("Department & Company Stalls")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.940917, 77.565415)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_danger))).title("Danger : Construction Area")).showInfoWindow();

                /* PARKING */
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.941946, 77.566838)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_parking))).title("Parking")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942285, 77.566471)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_parking))).title("Parking")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942111, 77.566003)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_parking))).title("Parking")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942130, 77.565549)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_parking))).title("Parking")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.941246, 77.566703)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_parking))).title("Parking")).showInfoWindow();

                /* FOOD */
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.940435, 77.565830)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_food))).title("Canteen")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.942581, 77.566466)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_food))).title("Canteen")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.941485, 77.566267)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_food))).title("Canteen")).showInfoWindow();
                mMap.addMarker(new MarkerOptions().position(new LatLng(12.939931, 77.565313)).icon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.marker_food))).title("Hostel Mess")).showInfoWindow();

                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_json));

                try {
                    KmlLayer layer = new KmlLayer(mMap, R.raw.bmsce, getActivity());
                    layer.addLayerToMap();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
