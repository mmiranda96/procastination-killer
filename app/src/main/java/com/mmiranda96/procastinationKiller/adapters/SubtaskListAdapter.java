package com.mmiranda96.procastinationKiller.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mmiranda96.procastinationKiller.R;

import java.util.ArrayList;

public class SubtaskListAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private ArrayList<String> subtasks;

    public SubtaskListAdapter(Activity activity, ArrayList<String> subtasks) {
        super(activity, R.layout.task_list_view, subtasks);
        this.activity = activity;
        this.subtasks = new ArrayList<>(subtasks);
    }

    public void update(ArrayList<String> subtasks) {
        this.subtasks = new ArrayList<>(subtasks);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.subtasks.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        if (view == null) {
            view = inflater.inflate(R.layout.put_subtask_list_view, parent, false);
        }

        TextView title = view.findViewById(R.id.textViewSubtaskListAdapterTitle);
        Button deleteButton = view.findViewById(R.id.buttonSubtaskListAdapterDelete);

        final String subtask = this.subtasks.get(position);

        title.setText(subtask);
        // TODO: add listener to delete button

        return view;
    }
}
