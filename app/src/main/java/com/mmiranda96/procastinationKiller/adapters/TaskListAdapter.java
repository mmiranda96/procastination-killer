package com.mmiranda96.procastinationKiller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.InvitePeopleActivity;
import com.mmiranda96.procastinationKiller.R;
import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<Task> {
    private Activity context;
    private ArrayList<Task> tasks;

    public TaskListAdapter(Activity context, ArrayList<Task> tasks) {
        super(context, R.layout.task_list_view, tasks);
        this.context = context;
        this.tasks = new ArrayList<>(tasks);
    }

    public void updateTasks(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
        Toast.makeText(this.context, "Tasks updated", Toast.LENGTH_SHORT).show();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.task_list_view, parent,false);

        TextView title = rowView.findViewById(R.id.textViewTaskListAdapterTitle);
        TextView description = rowView.findViewById(R.id.textViewTaskListAdapterDescription);
        Button addPeople = rowView.findViewById(R.id.buttonTaskListAdapterAddPeople);

        final Task task = this.tasks.get(position);

        title.setText(task.getTitle());
        description.setText(task.getDescription());
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), InvitePeopleActivity.class);
                intent.putExtra("task", task);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}
