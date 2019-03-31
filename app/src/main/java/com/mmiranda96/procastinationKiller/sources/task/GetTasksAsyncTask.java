package com.mmiranda96.procastinationKiller.sources.task;

import android.os.AsyncTask;

import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;

public abstract class GetTasksAsyncTask extends AsyncTask<Void, Void, ArrayList<Task>> {

    protected Listener listener;

    protected GetTasksAsyncTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(ArrayList<Task> result) {
        this.listener.getTasksAsyncTaskDone(result);
    }

    public interface Listener {
        void getTasksAsyncTaskDone(ArrayList<Task> result);
    }
}
