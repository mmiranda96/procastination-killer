package com.mmiranda96.procastinationKiller.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.fragments.MapFragment;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.util.IntentExtras;

public class MapsActivity extends AppCompatActivity {

    private static final String MAP_FRAGMENT_TAG = "mapFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra(IntentExtras.TASK);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = MapFragment.newInstance(task);
        transaction.add(R.id.constraintLayoutMapView, fragment, MAP_FRAGMENT_TAG);
        transaction.commit();
    }

    public void goBack(View view) {
        finish();
    }
}
