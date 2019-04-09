package com.mmiranda96.procastinationKiller.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.util.IntentExtras;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private Task task;
    private MapView mapView;
    private GoogleApiClient client;
    private static final int ZOOM = 18;

    public static MapFragment newInstance(Task task) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentExtras.TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            this.task = (Task) args.getSerializable(IntentExtras.TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        this.mapView = v.findViewById(R.id.mapView);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = this.task.location();
        MarkerOptions marker = new MarkerOptions()
                .position(location)
                .title("Task location");
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM));
    }
}
