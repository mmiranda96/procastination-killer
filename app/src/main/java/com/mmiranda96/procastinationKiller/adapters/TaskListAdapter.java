package com.mmiranda96.procastinationKiller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.activities.InvitePeopleActivity;
import com.mmiranda96.procastinationKiller.activities.MainActivity;
import com.mmiranda96.procastinationKiller.activities.PutTaskActivity;
import com.mmiranda96.procastinationKiller.activities.TaskDetailActivity;
import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.util.IntentExtras;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<Task> implements AdapterView.OnItemClickListener {
    private Activity activity;
    private User user;
    private ArrayList<Task> tasks;

    public TaskListAdapter(Activity activity, User user, ArrayList<Task> tasks) {
        super(activity, R.layout.task_list_view, tasks);
        this.activity = activity;
        this.user = user;
        this.tasks = new ArrayList<>(tasks);
    }

    public void update(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
        this.notifyDataSetChanged();
    }

    public void update(ArrayList<Task> tasks, User user) {
        this.user = user;
        this.tasks = new ArrayList<>(tasks);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.tasks.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        if (view == null) {
            view = inflater.inflate(R.layout.task_list_view, parent, false);
        }

        TextView title = view.findViewById(R.id.textViewTaskListAdapterTitle);
        TextView description = view.findViewById(R.id.textViewTaskListAdapterDescription);
        Button addPeople = view.findViewById(R.id.buttonTaskListAdapterAddPeople);
        Button edit = view.findViewById(R.id.buttonTaskListAdapterEdit);

        final Task task = this.tasks.get(position);

        title.setText(task.getTitle());
        description.setText(task.getDescription());
        addPeople.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), InvitePeopleActivity.class);
                intent.putExtra(IntentExtras.TASK, task);
                intent.putExtra(IntentExtras.USER, user);
                activity.startActivityForResult(intent, MainActivity.ADD_PEOPLE_ACTIVITY_CODE);
            }
        });
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), PutTaskActivity.class);
                intent.putExtra(IntentExtras.USER, user);
                intent.putExtra(IntentExtras.TASK, task);
                activity.startActivityForResult(intent, MainActivity.PUT_TASK_ACTIVITY_CODE);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, TaskDetailActivity.class);
        intent.putExtra(IntentExtras.TASK, this.tasks.get(position));
        activity.startActivity(intent);
    }
}
