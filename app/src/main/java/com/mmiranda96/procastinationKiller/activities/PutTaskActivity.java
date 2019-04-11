package com.mmiranda96.procastinationKiller.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.adapters.SubtaskListAdapter;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;
import com.mmiranda96.procastinationKiller.sources.task.TaskSourceFactory;
import com.mmiranda96.procastinationKiller.sources.task.UpdateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.util.IntentExtras;
import com.mmiranda96.procastinationKiller.util.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PutTaskActivity extends AppCompatActivity implements
        CreateTaskAsyncTask.Listener,
        UpdateTaskAsyncTask.Listener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final int RESULT_ERROR = 2;
    private static final int CALENDAR_ACTIVITY_CODE = 0;



    private TextView dateView;
    private EditText taskTitle, taskDescription, subtask;
    private ListView subtaskList;
    private Button addSubtask, create;
    private ArrayList<String> subtaskArrayList;
    private SubtaskListAdapter adapter;
    private TaskSource taskSource;

    private Task task;
    private Date due;
    private boolean isNewTask;

    private boolean useRealLocation;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_task);

        this.dateView = findViewById(R.id.textViewAddTaskActivityDate);
        this.taskTitle = findViewById(R.id.editTextAddTaskActivityTitle);
        this.taskDescription = findViewById(R.id.editTextAddTaskActivityDescription);
        this.subtask = findViewById(R.id.editTextAddTaskActivitySubtask);
        this.subtaskList = findViewById(R.id.listViewAddTaskActivitySubtasks);
        this.addSubtask = findViewById(R.id.buttonAddTaskActivityAddSubtask);
        this.create = findViewById(R.id.buttonAddTaskActivityCreateTask);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra(IntentExtras.USER);
        this.taskSource = TaskSourceFactory.newSource(TaskSourceFactory.REMOTE, user, Server.URL);
        this.task = (Task) intent.getSerializableExtra(IntentExtras.TASK);
        if (task != null) {
            this.isNewTask = false;
            this.due = task.getDue();
            this.dateView.setText(CalendarActivity.DATE_FORMAT.format(this.task.getDue()));
            this.taskTitle.setText(this.task.getTitle());
            this.taskDescription.setText(this.task.getDescription());
            this.subtaskArrayList = new ArrayList<>(task.getSubtasks());
            this.create.setText("Update");
        } else {
            this.isNewTask = true;
            this.subtaskArrayList = new ArrayList<>();
            this.create.setText("Create");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.i("location", "Permissions already granted, initing client...");
                this.useRealLocation = true;
                initClient();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }

        this.adapter = new SubtaskListAdapter(this, this.subtaskArrayList);
        this.subtaskList.setAdapter(adapter);
    }

    public void setDue(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, CALENDAR_ACTIVITY_CODE);
    }

    public void addSubtask(View view) {
        String subtask = this.subtask.getText().toString();
        if (subtask.compareTo("") != 0) {
            this.subtaskArrayList.add(subtask);
            this.adapter.update(this.subtaskArrayList);
            this.subtask.setText("");
        }
    }

    public void putTask(View view) {
        String title = this.taskTitle.getText().toString();
        String description = this.taskDescription.getText().toString();

        // Fixes strange bug in which dates are one day before
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.due.getTime());
        calendar.add(Calendar.DAY_OF_YEAR,  1);
        this.due = calendar.getTime();
        if (this.isNewTask) {
            Task task = new Task(title, description, this.due, this.subtaskArrayList, this.location);
            CreateTaskAsyncTask asyncTask = taskSource.newCreateTaskAsyncTask(this);
            asyncTask.execute(task);
        } else {
            Task task = new Task(this.task.getId(), title, description, this.due, this.subtaskArrayList);
            UpdateTaskAsyncTask asyncTask = taskSource.newUpdateTaskAsyncTask(this);
            asyncTask.execute(task);
        }
    }

    @Override
    public void createTaskAsyncTaskDone(Integer result) {
        finishActivity(result);
    }

    @Override
    public void updateTaskAsyncTaskDone(Integer result) {
        finishActivity(result);
    }

    private void finishActivity(Integer result) {
        Intent intent = getIntent();
        switch (result) {
            case CreateTaskAsyncTask.SUCCESS:
                setResult(Activity.RESULT_OK, intent);
                break;
            default:
                setResult(RESULT_ERROR);
                break;
        }
        finish();
    }

    private void initClient() {
        this.client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.client.connect();
        this.locationRequest = LocationRequest.create();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("location", "Client connected...");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            return;

        LocationServices.FusedLocationApi.requestLocationUpdates(client, this.locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("location", "Received new location: " + location.toString());
        if (this.location == null) {
            if (this.useRealLocation) {
                Log.i("location", "Set up location: " + location.toString());
                this.location = new LatLng(location.getLatitude(), location.getLongitude());
            } else {
                Log.i("location", "Set up default location: " + location.toString());
                this.location = new  LatLng(0., 0.);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.useRealLocation = requestCode == 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (this.useRealLocation) {
            Log.i("location", "Permissions granted now, initing client...");
            initClient();
        } else {
            Log.i("location", "Permissions denied, using empty coords...");
            this.location = new LatLng(0., 0.);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.useRealLocation) {
            client.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.useRealLocation) {
            client.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CALENDAR_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK){
                long epoch = data.getLongExtra(IntentExtras.DATE, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(epoch);
                this.due = calendar.getTime();
                this.dateView.setText(CalendarActivity.DATE_FORMAT.format(this.due));
            }
        }
    }
}
